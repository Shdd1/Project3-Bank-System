package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

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

    @NotEmpty(message = "Position cannot be null.")
    private String position;

    @NotNull(message = "Salary cannot be null.")
    @Positive(message = "Must be a non-negative decimal number.")
    private Double salary;
}
