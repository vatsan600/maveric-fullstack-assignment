package com.maveric.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import com.maveric.account.entity.Account;
import com.maveric.account.enums.AccountType;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.model.AccountWithoutBalance;
import com.maveric.account.model.User;
import com.maveric.account.repository.AccountRepository;
import com.maveric.account.restinterfaces.UserRestInterface;
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
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private UserRestInterface userRestInterface;

  @InjectMocks
  @Spy
  private AccountService service;
  Account account;
  AccountRequest accountRequest;
  AccountWithoutBalance accountWithoutBalance;
  User user;

  @BeforeEach
  void setup() {
    setupAccountRequest();
    setupAccount();
    setupAccountWithoutBalance();
    setupUser();
  }

  void setupAccount() {
    account = Account.of(accountRequest);
    account.setUniqueId(1);
  }

  void setupAccountRequest() {
    accountRequest = new AccountRequest();
    accountRequest.setType(AccountType.SAVINGS);
    accountRequest.setCustomerId(10);
  }

  void setupAccountWithoutBalance() {
    accountWithoutBalance = AccountWithoutBalance.of(account);
  }

  void setupUser() {
    user = new User();
    user.setUniqueId(1);
  }

  @Test
  void getAccountsWithPagination() {
    List<Account> testData = new ArrayList<>();
    testData.add(account);
    Page<Account> page = new PageImpl<>(testData);
    given(accountRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(page);
    doReturn(user).when(userRestInterface).getUser(ArgumentMatchers.anyInt());
    List<AccountWithoutBalance> result = service.getAccountsWithPagination(1, 10, 10);
    assertThat(result).isNotNull().hasSize(1);
  }


  @Test
  void saveAccount() {
    Account testOutput = account;
    testOutput.setUniqueId(0);
    given(accountRepository.save(account)).willReturn(testOutput);
    Account result = service.saveAccount(account);
    assertThat(result).isNotNull();
    assertThat(result.getUniqueId()).isZero();
  }

  @Test
  void saveAccountRequest() {
    doReturn(user).when(userRestInterface).getUser(ArgumentMatchers.anyInt());
    doReturn(account).when(service).saveAccount(ArgumentMatchers.any(Account.class));
    assertThat(service.saveAccountRequest(accountRequest)).isNotNull();
  }

  @Test
  void getAccountById() {
    given(accountRepository.findById(ArgumentMatchers.anyInt())).willReturn(Optional.of(account));
    doReturn(user).when(userRestInterface).getUser(ArgumentMatchers.anyInt());
    assertThat(service.getAccountById(Mockito.anyInt(),Mockito.anyInt())).isEqualTo(account);
  }

  @Test
  void updateAccount() {
    Mockito.doReturn(account).when(service).getAccountById(ArgumentMatchers.anyInt(),ArgumentMatchers.anyInt());
    Mockito.doReturn(account).when(service).saveAccount(account);
    assertThat(service.updateAccount(0,account)).isEqualTo(account);
  }

  @Test
  void deleteAccount() {
    Mockito.doReturn(account).when(service).getAccountById(ArgumentMatchers.anyInt(),ArgumentMatchers.anyInt());
    assertThat(service.deleteAccount(ArgumentMatchers.anyInt(),ArgumentMatchers.anyInt())).isTrue();
  }
}