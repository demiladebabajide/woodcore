package com.example.woodcore.model;
// Account.java

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Address cannot be blank")
    private String address;
    private String accountType = "SAVINGS";
    private AccountStatus accountStatus = AccountStatus.INACTIVE;
    private double balance;

    public Account(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    // Other attributes and getters/setters

//    @JsonIgnore
//    public boolean isValid(){
//        return !(ObjectUtils.isEmpty(firstName) || ObjectUtils.isEmpty(lastName) || ObjectUtils.isEmpty(address));
//    }
}
