package com.example.woodcore.controller;

import com.example.woodcore.model.Account;
import com.example.woodcore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@Validated @RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), CREATED) ;
    }

    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountByNumber(@PathVariable Long accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        if (ObjectUtils.isEmpty(account))
            return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(account, FOUND);
    }

    @PutMapping("/{accountId}/update")
    public ResponseEntity<Account> updateAccountDetails(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        Account account = accountService.updateAccountDetails(accountId, accountDetails);
        if (ObjectUtils.isEmpty(account))
            return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(account, OK);
    }

    @PutMapping("/{accountId}/activate")
    public ResponseEntity<Account> activateAccount(@PathVariable Long accountId) {
//        TODO: Disable activation of closed accounts
        Account account = accountService.activateAccount(accountId);
        if (ObjectUtils.isEmpty(account))
            return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(account, OK);
    }

    @PutMapping("/{accountId}/delete")
    public ResponseEntity<Account> closeAccount(@PathVariable Long accountId) {
        Account account = accountService.closeAccount(accountId);
        if (ObjectUtils.isEmpty(account))
            return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(account, OK);

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
