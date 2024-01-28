package com.maveric.balance.restinterfaces;

import com.maveric.balance.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account-service", url = "localhost:8000/account-service")
public interface AccountRestInterface {

  @GetMapping("/customers/{customerId}/accounts/{accountId}")
  Account getAccount(@PathVariable int customerId,
      @PathVariable int accountId);

}
