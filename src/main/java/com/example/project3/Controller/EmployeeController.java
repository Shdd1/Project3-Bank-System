package com.example.project3.Controller;

import com.example.project3.Api.ApiException;
import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/get")
    public ResponseEntity getEmp(){
        return ResponseEntity.status(200).body(employeeService.getEmployee());
    }
    @PostMapping("/register")
    public ResponseEntity registerEmp(@Valid @RequestBody EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));
    }
    @PutMapping("/update")
    public ResponseEntity updateEmp(@AuthenticationPrincipal User user, @Valid@RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmployee(user.getId(),employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee update"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteEmp(@AuthenticationPrincipal User user){
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
    }
}
