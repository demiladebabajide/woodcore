package com.example.woodcore.service;

import com.example.woodcore.model.Account;
import com.example.woodcore.model.AccountStatus;
import com.example.woodcore.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void createAccount() {
        Account account = new Account("Demi", "Babs", "Lagos, NG");
        Account savedAccount = accountService.createAccount(account);

        assertEquals(AccountStatus.INACTIVE, savedAccount.getAccountStatus());
        assertEquals("SAVINGS", savedAccount.getAccountType());
        assertEquals(0.0, savedAccount.getBalance());

    }

    @Test
    void getAccountByNumber() {
        Long accountNumber = 1L;
        Account account = new Account("Demi", "Babs", "Lagos, NG");
        account.setAccountNumber(accountNumber);
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        Account savedAccount = accountService.getAccountByNumber(accountNumber);

        assertEquals(accountNumber, savedAccount.getAccountNumber());
        assertEquals("Demi", savedAccount.getFirstName());
        assertEquals("Babs", savedAccount.getLastName());
    }

    @Test
    void updateAccountDetails() {
        Long accountId = 1L;
        Account accountDetails = new Account("Demi", "Babs", "Lagos, NG");
        accountDetails.setAccountType("CURRENT");
        accountDetails.setAddress("Warri, NG");

        Account savedAccount = new Account();
        savedAccount.setAccountNumber(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(savedAccount));
        Account updatedAccount = accountService.updateAccountDetails(accountId, accountDetails);
        assertEquals("CURRENT", updatedAccount.getAccountType());
        assertEquals("Warri, NG", updatedAccount.getAddress());

    }

    @Test
    void activateAccount() {
        Long accountId = 1L;
        Account account = new Account("Demi", "Babs", "Lagos, NG");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Account activatedAccount = accountService.activateAccount(accountId);
        assertEquals(AccountStatus.ACTIVE, activatedAccount.getAccountStatus());

    }

    @Test
    void closeAccount() {
        Long accountId = 12L;
        Account account = new Account("Demi", "Babs", "Lagos, NG");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Account closedAccount = accountService.closeAccount(accountId);
        assertEquals(AccountStatus.CLOSED, closedAccount.getAccountStatus());

    }
}