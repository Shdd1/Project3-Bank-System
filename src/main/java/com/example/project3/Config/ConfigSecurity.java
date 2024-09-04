package com.example.project3.Config;

import com.example.project3.Service.MyUserDetalisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetalisService myUserDetalisService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetalisService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/customer/register","/api/v1/employee/register").permitAll()
                .requestMatchers("/api/v1/customer/update","/api/v1/customer/delete","/api/v1/account/add","/api/v1/account/update/{account_id}","/api/v1/customer/delete/{account_id}","/api/v1/account/activ/{account_id}","/api/v1/account/details/{account_id}","/api/v1/account/deposit/{account_id}/{amount}","/api/v1/account/withdraw/{account_id}/{amount}","/api/v1/account/transferFunds/{fromAccount_id}/{toAccount_id}/{amount}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/employee/delete","/api/v1/employee/update").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/auth/get","/api/v1/auth/delete","/api/v1/customer/get","/api/v1/employee/get","/api/v1/account/get-all","/api/v1/account/block-account/{account_id}").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}