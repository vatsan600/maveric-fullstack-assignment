package com.maveric.balance.service;

import com.maveric.balance.entity.Balance;
import com.maveric.balance.model.Account;
import com.maveric.balance.model.BalanceRequest;
import com.maveric.balance.repository.BalanceRepository;
import com.maveric.balance.restinterfaces.AccountRestInterface;
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
public class BalanceService {

  private static final Logger logger = LogManager.getLogger(BalanceService.class);

  private final BalanceRepository balanceRepository;

  private final AccountRestInterface accountRestInterface;

  public BalanceService(BalanceRepository balanceRepository,
      AccountRestInterface accountRestInterface) {
    this.balanceRepository = balanceRepository;
    this.accountRestInterface = accountRestInterface;
  }

  public List<Balance> getBalanceWithPagination(int pageNumber, int pageSize,
      int customerId, int accountId) {
    logger.info("getting accounts for page number: {} and page size: {}", pageNumber, pageNumber);
    Pageable page = PageRequest.of(pageNumber, pageSize);
    return Optional.ofNullable(getAccount(customerId, accountId)).map(
            user -> balanceRepository.findAll(page).stream().toList())
        .orElse(null);
  }

  public Balance saveBalance(Balance balance) {
    logger.debug("saving the balance:{}", balance);
    LocalDateTime currentDateTime = LocalDateTime.now();
    if (!Objects.nonNull(balance.getCreatedAt())) {
      balance.setCreatedAt(currentDateTime);
    }
    balance.setUpdatedAt(currentDateTime);
    return balanceRepository.save(balance);
  }

  public Balance saveBalanceRequest(BalanceRequest balanceRequest) {
    logger.info("Creating a new balance");
    logger.info("validating if the given account is present in the account table");
    return Optional.ofNullable(
            getAccount(balanceRequest.getCustomerId(), balanceRequest.getAccountId()))
        .map(user -> {
          Balance balance = Balance.of(balanceRequest);
          return saveBalance(balance);
        }).orElse(null);
  }

  public Balance getBalanceById(int customerId, int accountId, int balanceId) {
    logger.info("getting balance by id:{}", balanceId);
    return Optional.ofNullable(getAccount(customerId, accountId))
        .flatMap(user -> balanceRepository.findById(balanceId))
        .orElse(null);
  }

  public Balance updateBalance(int customerId, int accountId, Balance balance) {
    logger.info("updating balance:{}", balance);
    Balance updateBalance = getBalanceById(customerId, accountId, balance.getUniqueId());
    if (Objects.nonNull(updateBalance)) {
      balance.setUniqueId(updateBalance.getUniqueId());
      return saveBalance(balance);
    }
    return null;
  }

  public boolean deleteBalance(int customerId, int accountId,int balanceId) {
    logger.info("deleting balance by id:{}", balanceId);
    if (Objects.nonNull(getBalanceById(customerId, accountId,balanceId))) {
      balanceRepository.deleteById(balanceId);
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
