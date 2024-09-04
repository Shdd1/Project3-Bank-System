package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }
    public void registerEmployee(EmployeeDTO employeeDTO){
        User user=new User();
        user.setUsername(employeeDTO.getUsername());
        String hash= new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hash);
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole("EMPLOYEE");


        Employee employee=new Employee();
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setUser(user);
        user.setEmployee(employee);
        authRepository.save(user);
        employeeRepository.save(employee);
    }
    public void updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {
        Employee oldEmployee = employeeRepository.findEmployeeById(employeeId);
        User oldUser = authRepository.findUsersById(employeeId);
        if (oldEmployee == null) {
            throw new ApiException("Employee not found");
        }
        oldUser.setEmail(employeeDTO.getEmail());
        String hash= new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        oldUser.setPassword(hash);
        oldUser.setName(employeeDTO.getName());
        oldUser.setUsername(employeeDTO.getUsername());
        oldUser.setEmployee(oldEmployee);
        authRepository.save(oldUser);

        oldEmployee.setSalary(employeeDTO.getSalary());
        oldEmployee.setPosition(employeeDTO.getPosition());
        oldEmployee.setUser(oldUser);
        employeeRepository.save(oldEmployee);

    }

    public void deleteEmployee(Integer id){
        Employee employee=employeeRepository.findEmployeeById(id);
        User user=authRepository.findUsersById(id);
        if(user==null){
            throw new ApiException("Employee not found");
        }

        authRepository.delete(user);
        employeeRepository.delete(employee);


    }

}
