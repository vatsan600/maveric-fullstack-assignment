package com.maveric.transaction.model;

import lombok.Data;

@Data
public class Account {
  private int uniqueId;
  private String type;
  private int customerId;

}
