package com.maveric.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  private int uniqueId;
  @NotNull
  private String firstName;
  private String middleName;
  @NotNull
  private String lastName;
  @NotNull
  private String email;
  @NotNull
  private String phoneNumber;
  @NotNull
  private String address;
  private Date dateOfBirth;
  private String gender;
  private Date createdAt;
  private Date updatedAt;
  private String role;
  @NotNull
  private String password;
}
