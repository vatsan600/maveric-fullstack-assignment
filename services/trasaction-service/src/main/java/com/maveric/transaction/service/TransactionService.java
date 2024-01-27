package com.maveric.transaction.service;

import com.maveric.transaction.entity.Transaction;
import com.maveric.transaction.model.Account;
import com.maveric.transaction.model.TransactionRequest;
import com.maveric.transaction.repository.TransactionRepository;
import com.maveric.transaction.restinterfaces.AccountRestInterface;
import feign.FeignException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private static final Logger logger = LogManager.getLogger(TransactionService.class);

  private final TransactionRepository transactionRepository;

  private final AccountRestInterface accountRestInterface;

  public TransactionService(TransactionRepository transactionRepository,
      AccountRestInterface accountRestInterface) {
    this.transactionRepository = transactionRepository;
    this.accountRestInterface = accountRestInterface;
  }

  public List<Transaction> getTransactionWithPagination(int pageNumber, int pageSize,
      int customerId, int accountId) {
    logger.info("getting transactions for page number: {} and page size: {}", pageNumber, pageNumber);
    Pageable page = PageRequest.of(pageNumber, pageSize);
    return Optional.ofNullable(getAccount(customerId, accountId)).map(
            user -> transactionRepository.findAll(page).stream().toList())
        .orElse(null);
  }

  public Transaction saveTransaction(Transaction transaction) {
    logger.debug("saving the transaction:{}", transaction);
    LocalDateTime currentDateTime = LocalDateTime.now();
    if (!Objects.nonNull(transaction.getCreatedAt())) {
      transaction.setCreatedAt(currentDateTime);
    }
    return transactionRepository.save(transaction);
  }

  public Transaction saveTransactionRequest(TransactionRequest transactionRequest) {
    logger.info("Creating a new Transaction");
    logger.info("validating if the given account is present in the account table");
    return Optional.ofNullable(
            getAccount(transactionRequest.getCustomerId(), transactionRequest.getAccountId()))
        .map(user -> {
          Transaction transaction = Transaction.of(transactionRequest);
          return saveTransaction(transaction);
        }).orElse(null);
  }

  public Transaction getTransactionById(int customerId, int accountId, int transactionId) {
    logger.info("getting transaction by id:{}", transactionId);
    return Optional.ofNullable(getAccount(customerId, accountId))
        .flatMap(user -> transactionRepository.findById(transactionId))
        .orElse(null);
  }

  public Transaction updateTransaction(int customerId, int accountId, Transaction transaction) {
    logger.info("updating transaction:{}", transaction);
    Transaction updateTransaction = getTransactionById(customerId, accountId, transaction.getUniqueId());
    if (Objects.nonNull(updateTransaction)) {
      transaction.setUniqueId(updateTransaction.getUniqueId());
      return saveTransaction(transaction);
    }
    return null;
  }

  public boolean deletetransaction(int customerId, int accountId,int transactionId) {
    logger.info("deleting transaction by id:{}", transactionId);
    if (Objects.nonNull(getTransactionById(customerId, accountId,transactionId))) {
      transactionRepository.deleteById(transactionId);
      return true;
    }
    return false;
  }

  private Account getAccount(int customerId, int accountId) {
    try {
      return accountRestInterface.getAccount(customerId, accountId);
    } catch (FeignException feignException) {
      logger.error("exception while getting account", feignException.getCause());
      return null;
    }
  }
}
