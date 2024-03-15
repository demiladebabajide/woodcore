package com.example.woodcore.service;

import com.example.woodcore.model.Account;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountByNumber(Long accountNumber);

    Account updateAccountDetails(Long accountId, Account accountDetails);

    Account activateAccount(Long accountId);

    Account closeAccount(Long accountId);
}
