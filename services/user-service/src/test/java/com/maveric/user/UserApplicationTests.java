package com.maveric.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.maveric.user.controller.UserController;
import com.maveric.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {

  @Autowired
  private UserController controller;

  @Autowired
  private UserService service;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
    assertThat(service).isNotNull();
  }
}
