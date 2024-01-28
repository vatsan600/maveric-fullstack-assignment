package com.maveric.transaction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import com.maveric.transaction.entity.Transaction;
import com.maveric.transaction.enums.Type;
import com.maveric.transaction.model.Account;
import com.maveric.transaction.model.TransactionRequest;
import com.maveric.transaction.repository.TransactionRepository;
import com.maveric.transaction.restinterfaces.AccountRestInterface;
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
class TransactionServiceTest {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private AccountRestInterface accountRestInterface;

  @InjectMocks
  @Spy
  private TransactionService service;

  Transaction transaction;

  TransactionRequest transactionRequest;

  Account account;

  @BeforeEach
  void setup() {
    setupTransactionRequest();
    setupBalance();
    setupAccount();
  }

  void setupAccount() {
    account = new Account();
    account.setUniqueId(1);
    account.setCustomerId(1);
  }

  void setupBalance() {
    transaction = Transaction.of(transactionRequest);
    transaction.setUniqueId(1);
  }

  void setupTransactionRequest() {
    transactionRequest = new TransactionRequest();
    transactionRequest.setAccountId(1);
    transactionRequest.setCustomerId(1);
    transactionRequest.setAmount(BigDecimal.valueOf(2345));
    transactionRequest.setType(Type.CREDIT);
  }

  @Test
  void getTransactionWithPagination() {
    List<Transaction> testData = new ArrayList<>();
    testData.add(transaction);
    Page<Transaction> page = new PageImpl<>(testData);
    given(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(page);
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    List<Transaction> result = service.getTransactionWithPagination(1, 10, 1, 1);
    assertThat(result).isNotNull().hasSize(1);
  }

  @Test
  void saveTransaction() {
    Transaction testOutput = transaction;
    testOutput.setUniqueId(0);
    given(transactionRepository.save(transaction)).willReturn(testOutput);
    Transaction result = service.saveTransaction(transaction);
    assertThat(result).isNotNull();
    assertThat(result.getUniqueId()).isZero();
  }

  @Test
  void saveTransactionRequest() {
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    doReturn(transaction).when(service).saveTransaction(ArgumentMatchers.any(Transaction.class));
    assertThat(service.saveTransactionRequest(transactionRequest)).isNotNull();
  }

  @Test
  void getTransactionById() {
    given(transactionRepository.findById(ArgumentMatchers.anyInt())).willReturn(
        Optional.of(transaction));
    doReturn(account).when(accountRestInterface)
        .getAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    assertThat(
        service.getTransactionById(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).isEqualTo(
        transaction);
  }

  @Test
  void updateTransaction() {
    Mockito.doReturn(transaction).when(service)
        .getTransactionById(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt());
    Mockito.doReturn(transaction).when(service).saveTransaction(transaction);
    assertThat(service.updateTransaction(0, 0, transaction)).isEqualTo(transaction);
  }

  @Test
  void deletetransaction() {
    Mockito.doReturn(transaction).when(service)
        .getTransactionById(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt());
    assertThat(
        service.deletetransaction(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt())).isTrue();
  }
}