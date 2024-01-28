package com.maveric.balance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import com.maveric.balance.entity.Balance;
import com.maveric.balance.enums.Currency;
import com.maveric.balance.model.Account;
import com.maveric.balance.model.BalanceRequest;
import com.maveric.balance.repository.BalanceRepository;
import com.maveric.balance.restinterfaces.AccountRestInterface;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

  @Mock
  private BalanceRepository balanceRepository;
  @Mock
  private AccountRestInterface accountRestInterface;

  @InjectMocks
  @Spy
  private BalanceService service;

  Balance balance;
  BalanceRequest balanceRequest;

  Account account;

  @BeforeEach
  void setup() {
    setupBalanceRequest();
    setupBalance();
    setupAccount();
  }

  void setupAccount() {
    account = new Account();
    account.setUniqueId(1);
    account.setCustomerId(1);
  }

  void setupBalance() {
    balance = Balance.of(balanceRequest);
    balance.setUniqueId(1);
  }

  void setupBalanceRequest() {
    balanceRequest = new BalanceRequest();
    balanceRequest.setAccountId(1);
    balanceRequest.setCustomerId(1);
    balanceRequest.setAmount(BigDecimal.valueOf(2345));
    balanceRequest.setCurrency(Currency.DOLLAR);
  }

  @Test
  void getBalanceWithPagination() {
    List<Balance> testData = new ArrayList<>();
    testData.add(balance);
    Page<Balance> page = new PageImpl<>(testData);
    given(balanceRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(page);
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    List<Balance> result = service.getBalanceWithPagination(1, 10, 1, 1);
    assertThat(result).isNotNull().hasSize(1);
  }

  @Test
  void saveBalance() {
    Balance testOutput = balance;
    testOutput.setUniqueId(0);
    given(balanceRepository.save(balance)).willReturn(testOutput);
    Balance result = service.saveBalance(balance);
    assertThat(result).isNotNull();
    assertThat(result.getUniqueId()).isZero();
  }

  @Test
  void saveBalanceRequest() {
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    doReturn(balance).when(service).saveBalance(ArgumentMatchers.any(Balance.class));
    assertThat(service.saveBalanceRequest(balanceRequest)).isNotNull();
  }

  @Test
  void getBalanceById() {
    given(balanceRepository.findById(ArgumentMatchers.anyInt())).willReturn(Optional.of(balance));
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    assertThat(
        service.getBalanceById(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).isEqualTo(
        balance);
  }

  @Test
  void updateBalance() {
    Mockito.doReturn(balance).when(service)
        .getBalanceById(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt());
    Mockito.doReturn(balance).when(service).saveBalance(balance);
    assertThat(service.updateBalance(0, 0, balance)).isEqualTo(balance);
  }

  @Test
  void deleteBalance() {
    Mockito.doReturn(balance).when(service)
        .getBalanceById(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt());
    assertThat(
        service.deleteBalance(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt())).isTrue();
  }

}