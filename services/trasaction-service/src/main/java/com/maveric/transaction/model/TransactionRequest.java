package com.maveric.transaction.model;

import com.maveric.transaction.enums.Type;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequest {

  @NotNull
  private BigDecimal amount;
  @NotNull
  private Type type;
  @NotNull
  private int accountId;
  @NotNull
  private int customerId;

}
