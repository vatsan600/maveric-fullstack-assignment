package com.maveric.balance.controller;

import com.maveric.balance.entity.Balance;
import com.maveric.balance.model.BalanceRequest;
import com.maveric.balance.service.BalanceService;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@RequestMapping("/customers/{customerId}/accounts/{accountId}")
public class BalanceController {

  private static final Logger logger = LogManager.getLogger(BalanceController.class);

  private final BalanceService balanceService;

  public BalanceController(BalanceService balanceService) {
    this.balanceService = balanceService;
  }

  @GetMapping("/balances")
  public ResponseEntity<List<Balance>> getAccountsWithPagination(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
      @PathVariable int customerId, @PathVariable int accountId) {
    logger.info("request for get accounts is received");
    logger.debug("The page is {} and the pageSize is {}", page, pageSize);
    return ResponseEntity.ok()
        .body(balanceService.getBalanceWithPagination(page - 1, pageSize, customerId, accountId));
  }

  @PostMapping("/balances")
  public ResponseEntity<Balance> createAccount(@RequestBody BalanceRequest balanceRequest,
      @PathVariable int customerId, @PathVariable int accountId) {
    logger.info("request for creating account is received");
    logger.debug("The input payload is: {}", balanceRequest);
    balanceRequest.setCustomerId(customerId);
    balanceRequest.setAccountId(accountId);
    Balance createdBalance = balanceService.saveBalanceRequest(balanceRequest);
    return ResponseEntity.created(URI.create("/balance/" + createdBalance.getUniqueId()))
        .body(createdBalance);
  }

  @GetMapping("/balances/{balanceId}")
  public ResponseEntity<BigDecimal> getAccount(@PathVariable int customerId,
      @PathVariable int accountId, @PathVariable int balanceId) {
    logger.info("request for getting balance using balanceId is received");
    logger.debug("The account id: {}, the customer id: {} & the balance id: {}", accountId,
        customerId, balanceId);
    Balance balance = balanceService.getBalanceById(customerId, accountId, balanceId);
    if (Objects.nonNull(balance)) {
      return ResponseEntity.ok()
          .body(Optional.ofNullable(balance.getAmount()).orElse(BigDecimal.ZERO));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/balances/{balanceId}")
  public ResponseEntity<Balance> updateAccount(@RequestBody BalanceRequest balanceRequest,
      @PathVariable int customerId, @PathVariable int accountId, @PathVariable int balanceId) {
    logger.info("request for updating balance is received");
    logger.debug(
        "The user data received from input is: {}, the customer id: {}, the account id: {}, Balance id: {}",
        balanceRequest, customerId, accountId, balanceId);
    balanceRequest.setCustomerId(customerId);
    balanceRequest.setAccountId(accountId);
    Balance balance = Balance.of(balanceRequest);
    balance.setUniqueId(balanceId);
    Balance updatedBalance = balanceService.updateBalance(customerId, accountId, balance);
    if (Objects.nonNull(updatedBalance)) {
      return ResponseEntity.ok().body(updatedBalance);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/balances/{balanceId}")
  public ResponseEntity<Balance> deleteBalance(@PathVariable int customerId,
      @PathVariable int accountId, @PathVariable int balanceId) {
    logger.info("request for deleting user is received");
    logger.debug("deleting Balance id:{} of account id:{} customer id: {}", balanceId, accountId,
        customerId);
    if (balanceService.deleteBalance(customerId, accountId, balanceId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}