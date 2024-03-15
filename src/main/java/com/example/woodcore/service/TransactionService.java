package com.example.woodcore.service;

import com.example.woodcore.model.*;

import java.io.IOException;


public interface TransactionService {
    Transaction deposit(DepositRequest request, Account account) throws IOException;

    Transaction withdraw(WithdrawalRequest request, Account account) throws IOException;

    Transaction transfer(TransferRequest request, Account sourceAccount, Account beneficiaryAccount) throws IOException;

    Transaction getTransaction(Long transactionID) throws IOException;
}
