package com.example.woodcore.service;

import com.example.woodcore.model.Account;
import com.example.woodcore.model.AccountStatus;
import com.example.woodcore.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final ObjectMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, ObjectMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public Account createAccount(Account newAccount) {
            newAccount.setAccountStatus(AccountStatus.INACTIVE);
            newAccount.setAccountType("SAVINGS");
            newAccount.setBalance(0.0);

            LOGGER.info("Account created successfully: {}", accountRepository.save(newAccount));
            return newAccount;
    }

    @Override
    public Account getAccountByNumber(Long accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (!account.isPresent()) {
            LOGGER.info("Account {} not found!", accountNumber);
            return null;
        }
        LOGGER.info("Fetched account details: {}", account.get());

        return account.get();
    }

    @Override
    public Account updateAccountDetails(Long accountId, Account accountDetails) {
        Optional<Account> savedAccount = accountRepository.findById(accountId);
        if (!savedAccount.isPresent()) {
            LOGGER.info("Account {} not found!", accountId);
            return null;
        }
        Account account = savedAccount.get();
        if (!accountDetails.getAccountType().isEmpty()) account.setAccountType(accountDetails.getAccountType());
        if (!accountDetails.getAddress().isEmpty()) account.setAddress(accountDetails.getAddress());
        if (!accountDetails.getFirstName().isEmpty()) account.setFirstName(accountDetails.getFirstName());
        if (!accountDetails.getLastName().isEmpty()) account.setLastName(accountDetails.getLastName());
        LOGGER.info("Updated account details: {}", accountRepository.save(account));
        return account;
    }

    @Override
    public Account activateAccount(Long accountId) {
        Optional<Account> savedAccount = accountRepository.findById(accountId);
        if (!savedAccount.isPresent()) {
            LOGGER.info("Account {} not found!", accountId);
            return null;
        }
        Account account = savedAccount.get();
        account.setAccountStatus(AccountStatus.ACTIVE);
        LOGGER.info("Account {} activated successfully!", accountId);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account closeAccount(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            return null;
        }
        account.get().setAccountStatus(AccountStatus.CLOSED);
        LOGGER.info("Account {} closed successfully!", accountId);
        accountRepository.save(account.get());
        return account.get();

    }
}
