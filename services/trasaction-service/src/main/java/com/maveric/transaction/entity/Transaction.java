package com.maveric.transaction.entity;

import com.maveric.transaction.model.TransactionRequest;
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
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int uniqueId;
  private BigDecimal amount;
  private String type;
  @NotNull
  private int accountId;
  private LocalDateTime createdAt;

  public static Transaction of(TransactionRequest transactionRequest) {
    Transaction transaction = new Transaction();
    transaction.setAmount(transactionRequest.getAmount());
    transaction.setType(transactionRequest.getType().name());
    transaction.setAccountId(transactionRequest.getAccountId());
    return transaction;
  }
}
