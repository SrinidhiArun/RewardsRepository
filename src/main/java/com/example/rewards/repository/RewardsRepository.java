package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardsRepository extends JpaRepository<Transaction,Integer> {

    List<Transaction> findTransactionByCustomerId(Integer customerId);
}
