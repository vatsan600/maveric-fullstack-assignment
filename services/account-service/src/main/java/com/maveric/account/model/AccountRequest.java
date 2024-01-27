package com.maveric.account.model;

import com.maveric.account.entity.Account;
import com.maveric.account.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequest {

  private AccountType type;
  private int customerId;

  public static AccountRequest of(Account account) {
    AccountRequest accountRequest = new AccountRequest();
    accountRequest.setCustomerId(account.getCustomerId());
    accountRequest.setType(AccountType.valueOf(account.getType()));
    return accountRequest;
  }
}
