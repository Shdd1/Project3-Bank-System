package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getCustomer(){
        return ResponseEntity.status(200).body(customerService.getCustomer());
    }
    @PostMapping("/register")
    public ResponseEntity registerCustomer(@Valid@RequestBody CustomerDTO customerDTO){
       customerService.registerCustomer(customerDTO);
       return ResponseEntity.status(200).body(new ApiResponse("Customer added"));
    }
    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @Valid@RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(user.getId(),customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Customer update"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user){
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
    }
}
