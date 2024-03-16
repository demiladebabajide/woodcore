package com.example.woodcore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransactionRequest {

    Long sourceAccountNumber;

    @Min(value = 0, message = "Amount must be positive")
    Double amount;



}
