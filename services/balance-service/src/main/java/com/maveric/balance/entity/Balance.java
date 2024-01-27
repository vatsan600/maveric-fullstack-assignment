package com.maveric.balance.entity;

import com.maveric.balance.model.BalanceRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Balance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int uniqueId;
  private BigDecimal amount;
  private String currency;
  @NotNull
  private int accountId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Balance of(BalanceRequest balanceRequest) {
    Balance balance = new Balance();
    balance.setAmount(balanceRequest.getAmount());
    balance.setCurrency(balanceRequest.getCurrency().name());
    balance.setAccountId(balanceRequest.getAccountId());
    return balance;
  }
}
