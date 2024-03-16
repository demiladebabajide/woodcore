package com.example.woodcore.controller;

import com.example.woodcore.model.*;
import com.example.woodcore.service.AccountService;
import com.example.woodcore.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;
    private ObjectMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService, AccountService accountService, ObjectMapper mapper) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.mapper = mapper;
    }


    @GetMapping("/{transactionId}")
    ResponseEntity<Transaction> getTransaction(@PathVariable("transactionId") Long transactionID) throws IOException {
        Transaction transaction = transactionService.getTransaction(transactionID);
        if (!ObjectUtils.isEmpty(transaction))
            return new ResponseEntity<>(transaction, FOUND);
        return new ResponseEntity<>(null, NOT_FOUND);
    }

    @PostMapping("/deposit")
    ResponseEntity<Transaction> deposit(@Validated @RequestBody DepositRequest request) throws IOException {
        Account account = accountService.getAccountByNumber(request.getSourceAccountNumber());
        ResponseEntity<Transaction> transactionResponseEntity;
        LOGGER.info("Deposit request: {}", mapper.writeValueAsString(request));
        Transaction response = transactionService.deposit(request, account);
            transactionResponseEntity = new ResponseEntity<>(response, OK);
        return transactionResponseEntity;
    }

    @PostMapping("/withdraw")
    ResponseEntity<Transaction> withdraw(@Validated @RequestBody WithdrawalRequest request) throws IOException {
        Account account = accountService.getAccountByNumber(request.getSourceAccountNumber());
        ResponseEntity<Transaction> transactionResponseEntity;
        LOGGER.info("Withdrawal request: {}", mapper.writeValueAsString(request));
        Transaction response = transactionService.withdraw(request, account);
            transactionResponseEntity = new ResponseEntity<>(response, OK);
        return transactionResponseEntity;
    }

    @PostMapping("/transfer")
    ResponseEntity<Transaction> transfer(@RequestBody TransferRequest request) throws IOException {
        Account sourceAccount = accountService.getAccountByNumber(request.getSourceAccountNumber());
        ResponseEntity<Transaction> transactionResponseEntity;
            Account beneficiaryAccount = accountService.getAccountByNumber(request.getBeneficiaryAccountNumber());
        Transaction response = transactionService.transfer(request, sourceAccount, beneficiaryAccount);
                transactionResponseEntity = new ResponseEntity<>(response, OK);
        return transactionResponseEntity;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
