package com.maveric.transaction.repository;

import com.maveric.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


}
