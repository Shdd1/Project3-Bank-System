package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(19) not null")
    @NotEmpty(message = "account Number can not be null")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", message = "Account number must follow the format 'XXXX-XXXX-XXXX-XXXX'.")
    private String accountNumber;

    @Column(columnDefinition = "DOUBLE not null")
    @NotNull(message = "Balance cannot be null.")
    @Positive(message = " Must be a non-negative decimal number.")
    private Double balance;

    private Boolean isActive = false; // Default value is false

    @ManyToOne
    @JsonIgnore
   // @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;
}
