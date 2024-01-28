package com.maveric.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.maveric.account.controller.AccountController;
import com.maveric.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountApplicationTests {

	@Autowired
	private AccountController controller;

	@Autowired
	private AccountService service;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
	}

}
