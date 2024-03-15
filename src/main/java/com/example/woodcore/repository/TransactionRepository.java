package com.example.woodcore.repository;

import com.example.woodcore.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO: Split into request and response
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
