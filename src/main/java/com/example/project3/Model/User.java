package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 10, message = "Length must be between 4 and 10 characters.")
    @NotEmpty(message = "username can not be empty")
    @Column(columnDefinition = "varchar(9) not null unique")
    private String username;

    @Size(min = 6, message = "Length must be at least 6 characters. ")
    @NotEmpty(message = "can not be null")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters.")
    @NotEmpty(message = "name can not be null")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Must be a valid email format.")
    @Column(unique = true)
    private String email;

   // @NotNull(message = "Role cannot be null.")
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN", message = "Role must be either 'CUSTOMER', 'EMPLOYEE', or 'ADMIN' only.")
    private String role;

    //one to one
    @OneToOne(cascade =CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;
    //one to one
    @OneToOne(cascade =CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
