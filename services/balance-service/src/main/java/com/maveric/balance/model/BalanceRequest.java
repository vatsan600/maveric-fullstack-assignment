package com.maveric.balance.model;

import com.maveric.balance.enums.Currency;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BalanceRequest {

  @NotNull
  private BigDecimal amount;
  @NotNull
  private Currency currency;
  @NotNull
  private int accountId;
  @NotNull
  private int customerId;

}
