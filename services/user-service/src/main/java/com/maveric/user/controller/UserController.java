package com.maveric.user.controller;

import com.maveric.user.entity.User;
import com.maveric.user.service.UserService;
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
@RequestMapping("/users")
public class UserController {

  private static final Logger logger = LogManager.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsersWithPagination(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
    logger.info("request for get Users is received");
    logger.debug("The page is {} and the pageSize is {}", page, pageSize);
    return ResponseEntity.ok()
        .body(userService.getUsersWithPagination(page - 1, pageSize).stream().toList());
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    logger.info("request for post users is received");
    logger.debug("The input payload is: {}", user);
    User createdUser = userService.saveUser(user);
    return ResponseEntity.created(URI.create("/users/" + createdUser.getUniqueId()))
        .body(createdUser);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(@PathVariable int userId) {
    logger.info("request for getting user using userId is received");
    logger.debug("The user id received from input is: {}", userId);
    User user = userService.getUserById(userId);
    if (Objects.nonNull(user)) {
      return ResponseEntity.ok().body(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{userId}")
  public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int userId) {
    logger.info("request for updating user is received");
    logger.debug("The user data received from input is: {}", user);
    user.setUniqueId(userId);
    User updatedUser = userService.updateUser(user);
    if (Objects.nonNull(updatedUser)) {
      return ResponseEntity.ok().body(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<User> deleteUser(@PathVariable int userId) {
    logger.info("request for deleting user is received");
    logger.debug("The unique Id user to be deleted is : {}", userId);
    if (userService.deleteUser(userId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/emails/{emailId}")
  public ResponseEntity<User> getUserByEmailId(@PathVariable String emailId) {
    logger.info("request for getting user by emailId is received");
    logger.debug("The emailId is: {}", emailId);
    User user = userService.getUserByEmailId(emailId);
    if (Objects.nonNull(user)) {
      return ResponseEntity.ok().body(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}