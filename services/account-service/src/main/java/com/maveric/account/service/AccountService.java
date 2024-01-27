package com.maveric.account.service;

import com.maveric.account.entity.Account;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.model.AccountWithoutBalance;
import com.maveric.account.repository.AccountRepository;
import com.maveric.account.restinterfaces.UserRestInterface;
import jakarta.ws.rs.NotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    return Optional.ofNullable(userRestInterface.getUser(customerId)).map(
            user -> accountRepository.findAll(page).map(AccountWithoutBalance::of).stream().toList())
        .orElseThrow(NotFoundException::new);
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
    return Optional.ofNullable(userRestInterface.getUser(accountRequest.getCustomerId()))
        .map(user -> {
          Account account = Account.of(accountRequest);
          return saveAccount(account);
        }).orElseThrow(NotFoundException::new);

  }

//  public User getUserById(int userId) {
//    logger.info("getting user by id:{}", userId);
//    return userRepository.findById(userId).orElse(null);
//  }
//
//  public User updateUser(User user) {
//    logger.info("getting user by id:{}", user);
//    User updatedUser = getUserById(user.getUniqueId());
//    if (Objects.nonNull(updatedUser)) {
//      user.setUniqueId(updatedUser.getUniqueId());
//      return saveUser(user);
//    }
//    return null;
//  }
//
//  public boolean deleteUser(int userId) {
//    logger.info("deleting user by id:{}", userId);
//    if (Objects.nonNull(getUserById(userId))) {
//      userRepository.deleteById(userId);
//      return true;
//    }
//    return false;
//  }
//
//  public User getUserByEmailId(String emailId) {
//    logger.info("getting user by email id:{}", emailId);
//    return userRepository.findByEmail(emailId);
//  }

}
