package com.example.woodcore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
