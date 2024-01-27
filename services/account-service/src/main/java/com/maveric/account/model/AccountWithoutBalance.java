package com.maveric.account.model;

import com.maveric.account.entity.Account;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AccountWithoutBalance {

  private AccountRequest accountRequest;
  private int id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static AccountWithoutBalance of(Account account) {
    AccountWithoutBalance accountWithoutBalance = new AccountWithoutBalance();
    accountWithoutBalance.setAccountRequest(AccountRequest.of(account));
    accountWithoutBalance.setId(account.getUniqueId());
    accountWithoutBalance.setCreatedAt(account.getCreatedAt());
    accountWithoutBalance.setUpdatedAt(account.getUpdatedAt());
    return accountWithoutBalance;
  }
}
