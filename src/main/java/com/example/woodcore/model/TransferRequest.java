package com.example.woodcore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferRequest extends TransactionRequest {
    private Long beneficiaryAccountNumber;
}
