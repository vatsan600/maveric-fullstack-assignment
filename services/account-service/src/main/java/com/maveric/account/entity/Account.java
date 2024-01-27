package com.maveric.account.entity;

import com.maveric.account.enums.AccountType;
import com.maveric.account.model.AccountRequest;
import com.maveric.account.repository.AccountRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.Data;

@Data
@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int uniqueId;
  @NotNull
  private String type;
  @NotNull
  private int customerId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Account of(AccountRequest request) {
    Account account = new Account();
    Optional.of(request.getType()).map(AccountType::name).ifPresent(account::setType);
    Optional.of(request.getCustomerId()).ifPresent(account::setCustomerId);
    return account;
  }
}
