package com.maveric.account.controller;

import com.maveric.account.entity.Account;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.model.AccountWithoutBalance;
import com.maveric.account.service.AccountService;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class AccountController {

  private static final Logger logger = LogManager.getLogger(AccountController.class);

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/{customerId}/accounts")
  public ResponseEntity<List<AccountWithoutBalance>> getAccountsWithPagination(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
      @PathVariable int customerId) {
    logger.info("request for get accounts is received");
    logger.debug("The page is {} and the pageSize is {}", page, pageSize);
    return ResponseEntity.ok()
        .body(accountService.getAccountsWithPagination(page - 1, pageSize, customerId));
  }

  @PostMapping("/{customerId}/accounts")
  public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest,
      @PathVariable int customerId) {
    logger.info("request for creating account is received");
    logger.debug("The input payload is: {}", accountRequest);
    accountRequest.setCustomerId(customerId);
    Account createdAccount = accountService.saveAccountRequest(accountRequest);
    return ResponseEntity.created(URI.create("/accounts/" + createdAccount.getUniqueId()))
        .body(createdAccount);
  }

  @GetMapping("/{customerId}/accounts/{accountId}")
  public ResponseEntity<Account> getAccount(@PathVariable int customerId,
      @PathVariable int accountId) {
    logger.info("request for getting user using accountId is received");
    logger.debug("The account id: {}, the customer id: {}", accountId, customerId);
    Account account = accountService.getAccountById(customerId, accountId);
    if (Objects.nonNull(account)) {
      return ResponseEntity.ok().body(account);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{customerId}/accounts/{accountId}")
  public ResponseEntity<Account> updateAccount(@RequestBody AccountRequest accountRequest,
      @PathVariable int customerId, @PathVariable int accountId) {
    logger.info("request for updating user is received");
    logger.debug(
        "The user data received from input is: {}, the customer id: {}, the account id: {}",
        accountRequest, customerId, accountId);
    accountRequest.setCustomerId(customerId);
    Account account = Account.of(accountRequest);
    account.setUniqueId(accountId);
    Account updatedAccount = accountService.updateAccount(customerId, account);
    if (Objects.nonNull(updatedAccount)) {
      return ResponseEntity.ok().body(updatedAccount);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{customerId}/accounts/{accountId}")
  public ResponseEntity<Account> deleteAccount(@PathVariable int customerId,
      @PathVariable int accountId) {
    logger.info("request for deleting user is received");
    logger.debug("deleting account id:{} of customer id: {}", accountId,customerId);
    if (accountService.deleteAccount(customerId,accountId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}