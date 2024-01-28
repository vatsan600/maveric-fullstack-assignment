package com.maveric.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.maveric.user.entity.User;
import com.maveric.user.repository.UserRepository;
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
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  @Spy
  private UserService service;
  User user;

  @BeforeEach
  void setupDummyUser() {
    user = new User();
    user.setUniqueId(1);
    user.setFirstName("ABC");
    user.setLastName("DEF");
    user.setEmail("mail@mails.com");
    user.setAddress("xyz");
    user.setPassword("tuv");
  }

  @Test
  void test_GetUsersWithPagination_Success() {
    List<User> testData = new ArrayList<>();
    testData.add(user);
    Page<User> page = new PageImpl<>(testData);
//    when(userRepository.findAll(mock(Pageable.class))).thenReturn(page);
    given(userRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(page);

    Page<User> result = service.getUsersWithPagination(1, 10);
    assertThat(result).isNotNull();
    assertThat(result.getSize()).isEqualTo(1);
  }

  @Test
  void test_GetUsersWithPagination_Failure() {
    Page<User> page = new PageImpl<>(new ArrayList<>());
    given(userRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(page);

    Page<User> result = service.getUsersWithPagination(0, 10);
    assertThat(result).isNotNull();
    assertThat(result.getSize()).isZero();
  }

  @Test
  void test_saveUser_Success() {
    User testOutput = user;
    testOutput.setUniqueId(0);
    given(userRepository.save(user)).willReturn(testOutput);

    User result = service.saveUser(user);
    assertThat(result).isNotNull();
    assertThat(result.getUniqueId()).isZero();
  }

  @Test
  void test_saveUser_Failure() {
    User result = service.saveUser(user);
    assertThat(result).isNull();
  }

  @Test
  void test_getUserById_Success() {
    given(userRepository.findById(ArgumentMatchers.anyInt())).willReturn(Optional.of(user));
    assertThat(service.getUserById(Mockito.anyInt())).isEqualTo(user);
  }

  @Test
  void test_getUserById_Failure() {
    given(userRepository.findById(ArgumentMatchers.anyInt())).willReturn(Optional.empty());
    assertThat(service.getUserById(Mockito.anyInt())).isNull();
  }

  @Test
  void test_updateUser_Success() {
    Mockito.doReturn(user).when(service).getUserById(ArgumentMatchers.anyInt());
    Mockito.doReturn(user).when(service).saveUser(user);
    assertThat(service.updateUser(user)).isEqualTo(user);
  }

  @Test
  void test_updateUser_Failure() {
    Mockito.doReturn(null).when(service).getUserById(ArgumentMatchers.anyInt());
    assertThat(service.updateUser(user)).isNull();
  }

  @Test
  void test_deleteUser_Success() {
    Mockito.doReturn(user).when(service).getUserById(ArgumentMatchers.anyInt());
    assertThat(service.deleteUser(ArgumentMatchers.anyInt())).isTrue();
  }

  @Test
  void test_deleteUser_Failure() {
    assertThat(service.deleteUser(ArgumentMatchers.anyInt())).isFalse();
  }

}
