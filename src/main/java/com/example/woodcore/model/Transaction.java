package com.example.woodcore.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Min(value = 0, message = "Amount must be positive")
    Double amount;

    String reference;

    String comments;


    TransactionStatus status;

    TransactionType type;

    @ManyToOne
    @JoinColumn(name = "account_account_number")
    Account sourceAccount;
}