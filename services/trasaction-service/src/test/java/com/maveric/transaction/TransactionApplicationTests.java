package com.maveric.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.maveric.transaction.controller.TransactionController;
import com.maveric.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionApplicationTests {

	@Autowired
	private TransactionController controller;

	@Autowired
	private TransactionService service;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
	}
}
