package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employee {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Position cannot be null.")
    @Column(columnDefinition = "varchar(20) not null")
    private String position;

    @NotNull(message = "Salary cannot be null.")
    @Positive(message = "Must be a non-negative decimal number.")
    @Column(columnDefinition = "DOUBLE not null")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
}
