package com.example.woodcore.config;

import com.example.woodcore.model.Account;
import com.example.woodcore.service.AccountService;
import com.example.woodcore.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TransactionService transactionService, AccountService accountService) {
        return args -> {
            log.info("Preloading account " + accountService.createAccount(new Account("Demi", "Babs","Lagos, NG")));
            log.info("Preloading account " + accountService.createAccount(new Account("Alec", "Baldwin","Warsaw, PL")));
            log.info("Activating account 1 " + accountService.activateAccount(1L));
        };
    }
}
