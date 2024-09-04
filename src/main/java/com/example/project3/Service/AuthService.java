package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    public List<User> getAllUser(){
        return authRepository.findAll();
    }

    public void deleteUser(Integer id){
        User user1=authRepository.findUsersById(id);
        if(user1==null){
            throw new ApiException("user not found");
        }
        authRepository.delete(user1);
    }



}
