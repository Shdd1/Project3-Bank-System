package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "phoneNumber can not be null")
    @Column(columnDefinition = "varchar(10) not null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be 10 digits long.")
    private String phoneNumber;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private Set<Account> accountSet;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

}
