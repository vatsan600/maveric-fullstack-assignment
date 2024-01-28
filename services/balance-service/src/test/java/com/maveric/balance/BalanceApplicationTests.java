package com.maveric.balance;

import static org.assertj.core.api.Assertions.assertThat;

import com.maveric.balance.controller.BalanceController;
import com.maveric.balance.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BalanceApplicationTests {

  @Autowired
  private BalanceController controller;

  @Autowired
  private BalanceService service;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
    assertThat(service).isNotNull();
  }
}
