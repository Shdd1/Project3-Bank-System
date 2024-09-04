package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO customerDTO){

        User user=new User();
        user.setUsername(customerDTO.getUsername());
        String hash= new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole("CUSTOMER");


        Customer customer=new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setUser(user);
        user.setCustomer(customer);
        authRepository.save(user);
        customerRepository.save(customer);
    }
    public void updateCustomer(Integer customerId,CustomerDTO customerDTO) {
        Customer oldCustomer = customerRepository.findCustomerById(customerId);
        User oldUser = authRepository.findUsersById(customerId);
        if (oldCustomer == null) {
            throw new ApiException("Customer not found");
        }
        oldUser.setEmail(customerDTO.getEmail());
        String hash= new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        oldUser.setPassword(hash);
        oldUser.setName(customerDTO.getName());
        oldUser.setUsername(customerDTO.getUsername());
        oldUser.setCustomer(oldCustomer);
        authRepository.save(oldUser);

        oldCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        oldCustomer.setUser(oldUser);
        customerRepository.save(oldCustomer);

    }
    public void deleteCustomer(Integer id){
        Customer customer=customerRepository.findCustomerById(id);
        User user=authRepository.findUsersById(id);
        if(user==null){
            throw new ApiException("customer not found");
        }

        authRepository.delete(user);
        customerRepository.delete(customer);


    }
}
