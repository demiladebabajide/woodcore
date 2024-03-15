package com.example.woodcore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
public class TransferResponse extends Transaction {
    @ManyToOne
    @JoinColumn(name = "beneficiary_account_account_number")
    Account beneficiaryAccount;
}
