package com.example.woodcore.service;

import com.example.woodcore.model.*;
import com.example.woodcore.repository.AccountRepository;
import com.example.woodcore.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void activeAccountReturnsSuccessfulDeposit() throws IOException {
        DepositRequest request = new DepositRequest();
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(200.0);

        DepositResponse savedResponse = new DepositResponse();
        savedResponse.setType(TransactionType.DEPOSIT);
        savedResponse.setAmount(100.0);
        savedResponse.setSourceAccount(account);
        savedResponse.setStatus(TransactionStatus.SUCCESSFUL);
        savedResponse.setComments(TransactionStatus.SUCCESSFUL.toString());

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionRepository.save(any(DepositResponse.class))).thenReturn(savedResponse);

        Transaction depositResponse = transactionService.deposit(request, account);

        assertEquals(savedResponse.getAmount(), depositResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), depositResponse.getSourceAccount());
        assertEquals(savedResponse.getStatus(), depositResponse.getStatus());
        assertEquals(savedResponse.getComments(), depositResponse.getComments());
        assertEquals(savedResponse.getType(), depositResponse.getType());

        assertEquals(300.0, depositResponse.getSourceAccount().getBalance());
    }

    @Test
    void inactiveAccountReturnsFailedDeposit() throws IOException {
        DepositRequest request = new DepositRequest();
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountStatus(AccountStatus.INACTIVE);
        account.setBalance(200.0);

        DepositResponse savedResponse = new DepositResponse();
        savedResponse.setType(TransactionType.DEPOSIT);
        savedResponse.setAmount(100.0);
        savedResponse.setSourceAccount(account);
        savedResponse.setStatus(TransactionStatus.FAILED);
        savedResponse.setComments("Unable to perform transactions on this account. Kindly contact your bank");

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionRepository.save(any(DepositResponse.class))).thenReturn(savedResponse);

        Transaction depositResponse = transactionService.deposit(request, account);

        assertEquals(savedResponse.getAmount(), depositResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), depositResponse.getSourceAccount());
        assertEquals(savedResponse.getStatus(), depositResponse.getStatus());
        assertEquals(savedResponse.getComments(), depositResponse.getComments());
        assertEquals(savedResponse.getType(), depositResponse.getType());
    }

    @Test
    void fundedAccountReturnsSuccessfulWithdrawal() throws IOException {
        WithdrawalRequest request = new WithdrawalRequest();
        request.setAmount(10.0);

        Account account = new Account();
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(100.0);

        WithdrawalResponse savedResponse = new WithdrawalResponse();
        savedResponse.setType(TransactionType.WITHDRAWAL);
        savedResponse.setAmount(10.0);
        savedResponse.setSourceAccount(account);
        savedResponse.setStatus(TransactionStatus.SUCCESSFUL);
        savedResponse.setComments(TransactionStatus.SUCCESSFUL.toString());

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionRepository.save(any(WithdrawalResponse.class))).thenReturn(savedResponse);

        Transaction withdrawalResponse = transactionService.withdraw(request, account);

        assertEquals(savedResponse.getAmount(), withdrawalResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), withdrawalResponse.getSourceAccount());
        assertEquals(savedResponse.getStatus(), withdrawalResponse.getStatus());
        assertEquals(savedResponse.getComments(), withdrawalResponse.getComments());
        assertEquals(savedResponse.getType(), withdrawalResponse.getType());

        assertEquals(90.0, withdrawalResponse.getSourceAccount().getBalance());

    }

    @Test
    void insufficientAccountBalanceReturnsFailedWithdrawal() throws IOException {
        WithdrawalRequest request = new WithdrawalRequest();
        request.setAmount(10.0);

        Account account = new Account();
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(5.0);

        WithdrawalResponse savedResponse = new WithdrawalResponse();
        savedResponse.setType(TransactionType.WITHDRAWAL);
        savedResponse.setAmount(10.0);
        savedResponse.setSourceAccount(account);
        savedResponse.setStatus(TransactionStatus.FAILED);
        savedResponse.setComments("Insufficient balance");

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionRepository.save(any(WithdrawalResponse.class))).thenReturn(savedResponse);

        Transaction withdrawalResponse = transactionService.withdraw(request, account);

        assertEquals(savedResponse.getAmount(), withdrawalResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), withdrawalResponse.getSourceAccount());
        assertEquals(savedResponse.getStatus(), withdrawalResponse.getStatus());
        assertEquals(savedResponse.getComments(), withdrawalResponse.getComments());
        assertEquals(savedResponse.getType(), withdrawalResponse.getType());
    }

    @Test
    void fundedAndActiveAccountsReturnSuccessfulTransfer() throws IOException {
        TransferRequest request = new TransferRequest();
        request.setAmount(15.0);

        Account sourceAccount = new Account();
        sourceAccount.setAccountStatus(AccountStatus.ACTIVE);
        sourceAccount.setBalance(100.0);

        Account beneficiaryAccount = new Account();
        beneficiaryAccount.setAccountStatus(AccountStatus.ACTIVE);
        beneficiaryAccount.setBalance(0.0);

        TransferResponse savedResponse = new TransferResponse();
        savedResponse.setType(TransactionType.TRANSFER);
        savedResponse.setAmount(15.0);
        savedResponse.setSourceAccount(sourceAccount);
        savedResponse.setBeneficiaryAccount(beneficiaryAccount);
        savedResponse.setStatus(TransactionStatus.SUCCESSFUL);
        savedResponse.setComments(TransactionStatus.SUCCESSFUL.toString());

        when(accountRepository.save(any(Account.class))).thenReturn(sourceAccount);
        when(transactionRepository.save(any(TransferResponse.class))).thenReturn(savedResponse);

        TransferResponse transferResponse = (TransferResponse) transactionService.transfer(request, sourceAccount, beneficiaryAccount);

        assertEquals(savedResponse.getAmount(), transferResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), transferResponse.getSourceAccount());
        assertEquals(savedResponse.getBeneficiaryAccount(), transferResponse.getBeneficiaryAccount());
        assertEquals(savedResponse.getStatus(), transferResponse.getStatus());
        assertEquals(savedResponse.getComments(), transferResponse.getComments());
        assertEquals(savedResponse.getType(), transferResponse.getType());

        assertEquals(85.0, transferResponse.getSourceAccount().getBalance());
        assertEquals(15.0, transferResponse.getBeneficiaryAccount().getBalance());

    }

    @Test
    void nonExistentBeneficiaryReturnsFailedTransfer() throws IOException {
        TransferRequest request = new TransferRequest();
        request.setAmount(15.0);

        Account sourceAccount = new Account();
        sourceAccount.setAccountStatus(AccountStatus.ACTIVE);
        sourceAccount.setBalance(100.0);

        Account beneficiaryAccount = null;

        TransferResponse savedResponse = new TransferResponse();
        savedResponse.setType(TransactionType.TRANSFER);
        savedResponse.setAmount(15.0);
        savedResponse.setSourceAccount(sourceAccount);
        savedResponse.setBeneficiaryAccount(beneficiaryAccount);
        savedResponse.setStatus(TransactionStatus.FAILED);
        savedResponse.setComments("Beneficiary account not found");

        when(accountRepository.save(any(Account.class))).thenReturn(sourceAccount);
        when(transactionRepository.save(any(TransferResponse.class))).thenReturn(savedResponse);

        TransferResponse transferResponse = (TransferResponse) transactionService.transfer(request, sourceAccount, beneficiaryAccount);

        assertEquals(savedResponse.getAmount(), transferResponse.getAmount());
        assertEquals(savedResponse.getSourceAccount(), transferResponse.getSourceAccount());
        assertEquals(savedResponse.getBeneficiaryAccount(), transferResponse.getBeneficiaryAccount());
        assertEquals(savedResponse.getStatus(), transferResponse.getStatus());
        assertEquals(savedResponse.getComments(), transferResponse.getComments());
        assertEquals(savedResponse.getType(), transferResponse.getType());

        assertEquals(100.0, transferResponse.getSourceAccount().getBalance());
    }

}