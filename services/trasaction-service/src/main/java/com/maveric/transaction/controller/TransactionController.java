package com.maveric.transaction.controller;

import com.maveric.transaction.entity.Transaction;
import com.maveric.transaction.model.TransactionRequest;
import com.maveric.transaction.service.TransactionService;
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
public class TransactionController {

  private static final Logger logger = LogManager.getLogger(TransactionController.class);

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<Transaction>> getTransactionsWithPagination(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
      @PathVariable int customerId, @PathVariable int accountId) {
    logger.info("request for get transactions is received");
    logger.debug("The page is {} and the pageSize is {}", page, pageSize);
    return ResponseEntity.ok()
        .body(transactionService.getTransactionWithPagination(page - 1, pageSize, customerId,
            accountId));
  }

  @PostMapping("/transactions")
  public ResponseEntity<Transaction> createAccount(
      @RequestBody TransactionRequest transactionRequest,
      @PathVariable int customerId, @PathVariable int accountId) {
    logger.info("request for creating transaction is received");
    logger.debug("The input payload is: {}", transactionRequest);
    transactionRequest.setCustomerId(customerId);
    transactionRequest.setAccountId(accountId);
    Transaction createdTransaction = transactionService.saveTransactionRequest(transactionRequest);
    return ResponseEntity.created(URI.create("/balance/" + createdTransaction.getUniqueId()))
        .body(createdTransaction);
  }

  @GetMapping("/transactions/{transactionId}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable int customerId,
      @PathVariable int accountId, @PathVariable int transactionId) {
    logger.info("request for getting balance using balanceId is received");
    logger.debug("The account id: {}, the customer id: {} & the transaction id: {}", accountId,
        customerId, transactionId);
    Transaction transaction = transactionService.getTransactionById(customerId, accountId, transactionId);
    if (Objects.nonNull(transaction)) {
      return ResponseEntity.ok()
          .body(transaction);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/transactions/{transactionId}")
  public ResponseEntity<Transaction> updateTransaction(@RequestBody TransactionRequest transactionRequest,
      @PathVariable int customerId, @PathVariable int accountId, @PathVariable int transactionId) {
    logger.info("request for updating balance is received");
    logger.debug(
        "The user data received from input is: {}, the customer id: {}, the account id: {}, Balance id: {}",
        transactionRequest, customerId, accountId, transactionId);
    transactionRequest.setCustomerId(customerId);
    transactionRequest.setAccountId(accountId);
    Transaction transaction = Transaction.of(transactionRequest);
    transaction.setUniqueId(transactionId);
    Transaction updatedBalance = transactionService.updateTransaction(customerId, accountId, transaction);
    if (Objects.nonNull(updatedBalance)) {
      return ResponseEntity.ok().body(updatedBalance);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/transactions/{transactionId}")
  public ResponseEntity<Transaction> deleteBalance(@PathVariable int customerId,
      @PathVariable int accountId, @PathVariable int transactionId) {
    logger.info("request for deleting user is received");
    logger.debug("deleting transaction id:{} of account id:{} customer id: {}", transactionId, accountId,
        customerId);
    if (transactionService.deletetransaction(customerId, accountId, transactionId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}