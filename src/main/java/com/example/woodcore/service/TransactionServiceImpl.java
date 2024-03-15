package com.example.woodcore.service;

import com.example.woodcore.model.*;
import com.example.woodcore.repository.AccountRepository;
import com.example.woodcore.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private ObjectMapper mapper;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(ObjectMapper mapper, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction deposit(DepositRequest request, Account account) throws IOException {

        DepositResponse response = new DepositResponse();
        response.setReference(UUID.randomUUID().toString());
        response.setAmount(request.getAmount());
        response.setType(TransactionType.DEPOSIT);
        response.setSourceAccount(account);
        if (account != null) {

            if (account.getAccountStatus() == AccountStatus.ACTIVE) {
                try {
                    Double balance = account.getBalance() + request.getAmount();
                    account.setBalance(balance);
                    response.setStatus(TransactionStatus.SUCCESSFUL);
                    response.setComments(TransactionStatus.SUCCESSFUL.toString());
                    accountRepository.save(account);
                } catch (Exception e) {
                    response.setStatus(TransactionStatus.PENDING);
                    response.setComments("Something went wrong");
                }
            } else {
                response.setStatus(TransactionStatus.FAILED);
                response.setComments("Unable to perform transactions on this account. Kindly contact your bank");

            }
        } else {
            response.setStatus(TransactionStatus.FAILED);
            response.setComments("Account not found");

        }
        LOGGER.info("Deposit response: {}", mapper.writeValueAsString(response));
        transactionRepository.save(response);

        return response;
    }

    @Override
    public Transaction withdraw(WithdrawalRequest request, Account account) throws IOException {

        WithdrawalResponse response = new WithdrawalResponse();
        response.setReference(UUID.randomUUID().toString());
        response.setAmount(request.getAmount());
        response.setType(TransactionType.WITHDRAWAL);
        response.setSourceAccount(account);
        if (account != null) {

            if (account.getAccountStatus() == AccountStatus.ACTIVE) {
                try {
                    Double balance = account.getBalance();
                    if (balance < request.getAmount()) {
                        response.setStatus(TransactionStatus.FAILED);
                        response.setComments("Insufficient balance");
                    } else {
                        balance -= request.getAmount();
                        account.setBalance(balance);
                        response.setStatus(TransactionStatus.SUCCESSFUL);
                        response.setComments(TransactionStatus.SUCCESSFUL.toString());
                        accountRepository.save(account);
                    }
                } catch (Exception e) {
                    response.setStatus(TransactionStatus.PENDING);
                    response.setComments("Something went wrong");
                }
            } else {
                response.setStatus(TransactionStatus.FAILED);
                response.setComments("Unable to perform transactions on this account. Kindly contact your bank");

            }
        } else {
            response.setStatus(TransactionStatus.FAILED);
            response.setComments("Account not found");

        }
        transactionRepository.save(response);

        LOGGER.info("Withdrawal response: {}", mapper.writeValueAsString(response));
        return response;

    }

    @Override
    public Transaction transfer(TransferRequest request, Account sourceAccount, Account beneficiaryAccount) throws IOException {
        TransferResponse response = new TransferResponse();
        response.setReference(UUID.randomUUID().toString());
        response.setAmount(request.getAmount());
        response.setType(TransactionType.TRANSFER);
        response.setSourceAccount(sourceAccount);
        response.setBeneficiaryAccount(beneficiaryAccount);

        if (sourceAccount != null){
            if (beneficiaryAccount != null) {
                if (sourceAccount.getAccountStatus() == AccountStatus.ACTIVE) {
                    if (beneficiaryAccount.getAccountStatus() == AccountStatus.ACTIVE) {
                        try {
                            Double sourceAccountBalance = sourceAccount.getBalance();
                            Double beneficiaryAccountBalance = beneficiaryAccount.getBalance();
                            if (sourceAccountBalance < request.getAmount()) {
                                response.setStatus(TransactionStatus.FAILED);
                                response.setComments("Insufficient balance");
                            } else {
                                sourceAccountBalance -= request.getAmount();
                                sourceAccount.setBalance(sourceAccountBalance);
                                beneficiaryAccountBalance += request.getAmount();
                                beneficiaryAccount.setBalance(beneficiaryAccountBalance);
                                accountRepository.save(beneficiaryAccount);
                                accountRepository.save(sourceAccount);

                                transactionRepository.save(response);
                                response.setStatus(TransactionStatus.SUCCESSFUL);
                                response.setComments(TransactionStatus.SUCCESSFUL.toString());
                            }
                        } catch (Exception e) {
                            response.setStatus(TransactionStatus.PENDING);
                            response.setComments("Something went wrong");
                        }
                    } else {
                        response.setStatus(TransactionStatus.FAILED);
                        response.setComments("Unable to perform transactions on beneficiary account. Kindly contact your bank");
                    }
                } else {
                    response.setStatus(TransactionStatus.FAILED);
                    response.setComments("Unable to perform transactions on source account. Kindly contact your bank");
                }
            } else {
                response.setStatus(TransactionStatus.FAILED);
                response.setComments("Beneficiary account not found");
            }
        } else {
            response.setStatus(TransactionStatus.FAILED);
            response.setComments("Source account not found");
        }
        transactionRepository.save(response);

        LOGGER.info("Transfer response: {}", mapper.writeValueAsString(response));
        return response;
    }

    @Override
    public Transaction getTransaction(Long transactionID) throws IOException {
        Optional<Transaction> transaction = transactionRepository.findById(transactionID);
        if (!transaction.isPresent()) {
            LOGGER.info("Transaction {} not found!", transactionID);
            return null;
        }
        LOGGER.info("Fetched transaction details: {}", mapper.writeValueAsString(transaction.get()));

        return transaction.get();

    }
}
