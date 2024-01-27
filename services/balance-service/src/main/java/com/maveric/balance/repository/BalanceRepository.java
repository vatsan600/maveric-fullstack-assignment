package com.maveric.balance.repository;

import com.maveric.balance.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceRepository extends JpaRepository<Balance, Integer> {


}
