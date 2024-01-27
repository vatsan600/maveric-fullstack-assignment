package com.maveric.account.controller;

import com.maveric.account.entity.Account;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.model.AccountWithoutBalance;
import com.maveric.account.service.AccountService;
import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

//  @GetMapping("/{userId}")
//  public ResponseEntity<User> getUser(@PathVariable int userId) {
//    logger.info("request for getting user using userId is received");
//    logger.debug("The user id received from input is: {}", userId);
//    User user = userService.getUserById(userId);
//    if (Objects.nonNull(user)) {
//      return ResponseEntity.ok().body(user);
//    } else {
//      return ResponseEntity.notFound().build();
//    }
//  }
//
//  @PutMapping("/{userId}")
//  public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int userId) {
//    logger.info("request for updating user is received");
//    logger.debug("The user data received from input is: {}", user);
//    user.setUniqueId(userId);
//    User updatedUser = userService.updateUser(user);
//    if (Objects.nonNull(updatedUser)) {
//      return ResponseEntity.ok().body(updatedUser);
//    } else {
//      return ResponseEntity.notFound().build();
//    }
//  }
//
//  @DeleteMapping("/{userId}")
//  public ResponseEntity<User> deleteUser(@PathVariable int userId) {
//    logger.info("request for deleting user is received");
//    logger.debug("The unique Id user to be deleted is : {}", userId);
//    if (userService.deleteUser(userId)) {
//      return ResponseEntity.ok().build();
//    } else {
//      return ResponseEntity.notFound().build();
//    }
//  }
//
//  @GetMapping("/emails/{emailId}")
//  public ResponseEntity<User> getUserByEmailId(@PathVariable String emailId) {
//    logger.info("request for getting user by emailId is received");
//    logger.debug("The emailId is: {}", emailId);
//    User user = userService.getUserByEmailId(emailId);
//    if (Objects.nonNull(user)) {
//      return ResponseEntity.ok().body(user);
//    } else {
//      return ResponseEntity.notFound().build();
//    }
//  }

}