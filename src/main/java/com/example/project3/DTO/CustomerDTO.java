package com.example.project3.DTO;

import com.example.project3.Model.Account;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CustomerDTO {


    @Size(min = 4, max = 10, message = "Length must be between 4 and 10 characters.")
    @NotEmpty(message = "username can not be empty")
    private String username;

    @Size(min = 6, message = "Length must be at least 6 characters. ")
    @NotEmpty(message = "can not be null")
    private String password;

    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters.")
    @NotEmpty(message = "name can not be null")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Must be a valid email format.")
    private String email;


    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN", message = "Role must be either 'CUSTOMER', 'EMPLOYEE', or 'ADMIN' only.")
    private String role;

    @NotNull(message = "phoneNumber can not be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be 10 digits long.")
    private String phoneNumber;


}
