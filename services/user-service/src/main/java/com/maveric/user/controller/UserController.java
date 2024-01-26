package com.maveric.user.controller;

import com.maveric.user.entity.User;
import com.maveric.user.service.UserService;
import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<List<User>> getUsersWithPagination(@RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
    logger.info("request for get Users is received");
    logger.debug("The page is {} and the pageSize is {}",page,pageSize);
    return ResponseEntity.ok().body(userService.getUsersWithPagination(page-1,pageSize).stream().toList());
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    logger.info("request for post users is received");
    logger.debug("The input payload is: {}",user);
    User createdUser = userService.createUser(user);
    return ResponseEntity.created(URI.create("/users/" + createdUser.getUniqueId())).body(createdUser);
  }

}