package com.maveric.account.service;

import com.maveric.account.entity.Account;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.model.AccountWithoutBalance;
import com.maveric.account.model.User;
import com.maveric.account.repository.AccountRepository;
import com.maveric.account.restinterfaces.UserRestInterface;
import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private static final Logger logger = LogManager.getLogger(AccountService.class);
  private final AccountRepository accountRepository;
  private final UserRestInterface userRestInterface;

  public AccountService(AccountRepository accountRepository, UserRestInterface userRestInterface) {
    this.accountRepository = accountRepository;
    this.userRestInterface = userRestInterface;
  }

  public List<AccountWithoutBalance> getAccountsWithPagination(int pageNumber, int pageSize,
      int customerId) {
    logger.info("getting users for page number: {} and page size: {}", pageNumber, pageNumber);
    Pageable page = PageRequest.of(pageNumber, pageSize);
    return Optional.ofNullable(getCustomer(customerId)).map(
            user -> accountRepository.findAll(page).map(AccountWithoutBalance::of).stream().toList())
        .orElse(null);
  }

  public Account saveAccount(Account account) {
    logger.debug("saving the account:{}", account);
    LocalDateTime currentDateTime = LocalDateTime.now();
    if (!Objects.nonNull(account.getCreatedAt())) {
      account.setCreatedAt(currentDateTime);
    }
    account.setUpdatedAt(currentDateTime);
    return accountRepository.save(account);
  }

  public Account saveAccountRequest(AccountRequest accountRequest) {
    logger.info("Creating a new account");
    logger.info("validating if the given customer id is present in the user table");
    return Optional.ofNullable(getCustomer(accountRequest.getCustomerId()))
        .map(user -> {
          Account account = Account.of(accountRequest);
          return saveAccount(account);
        }).orElse(null);

  }

  public Account getAccountById(int customerId, int accountId) {
    logger.info("getting account by id:{}", accountId);
    return Optional.ofNullable(getCustomer(customerId))
        .flatMap(user -> accountRepository.findById(accountId))
        .orElse(null);
  }

  public Account updateAccount(int customerId, Account account) {
    logger.info("updating account:{}", account);
    Account updateAccount = getAccountById(customerId, account.getUniqueId());
    if (Objects.nonNull(updateAccount)) {
      account.setUniqueId(updateAccount.getUniqueId());
      return saveAccount(account);
    }
    return null;
  }


  public boolean deleteAccount(int customerId, int accountId) {
    logger.info("deleting account by id:{}", accountId);
    if (Objects.nonNull(getAccountById(customerId, accountId))) {
      accountRepository.deleteById(accountId);
      return true;
    }
    return false;
  }

  private User getCustomer(int customerId) {
    try {
      return userRestInterface.getUser(customerId);
    } catch (FeignException feignException) {
      logger.error("exception while getting customer", feignException.getCause());
      return null;
    }
  }
}
