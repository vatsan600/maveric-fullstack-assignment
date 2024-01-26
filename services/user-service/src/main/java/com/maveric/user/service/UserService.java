package com.maveric.user.service;

import com.maveric.user.entity.User;
import com.maveric.user.repository.UserRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LogManager.getLogger(UserService.class);

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    logger.info("getting users");
    return userRepository.findAll();
  }

  public Page<User> getUsersWithPagination(int pageNumber, int pageSize) {
    logger.info("getting users for page number: {} and page size: {}", pageNumber, pageNumber);
    Pageable page = PageRequest.of(pageNumber, pageSize);
    return userRepository.findAll(page);
  }

  public User saveUser(User user) {
    logger.info("saving the users");
    Date currentDate = Date.valueOf(LocalDate.now());
    if (!Objects.nonNull(user.getCreatedAt())) {
      user.setCreatedAt(currentDate);
    }
    user.setUpdatedAt(currentDate);
    return userRepository.save(user);
  }

  public User getUserById(int userId) {
    logger.info("getting user by id:{}", userId);
    return userRepository.findById(userId).orElse(null);
  }

  public User updateUser(User user) {
    logger.info("getting user by id:{}", user);
    User updatedUser = getUserById(user.getUniqueId());
    if (Objects.nonNull(updatedUser)) {
      user.setUniqueId(updatedUser.getUniqueId());
      return saveUser(user);
    }
    return null;
  }

  public boolean deleteUser(int userId) {
    logger.info("deleting user by id:{}", userId);
    if (Objects.nonNull(getUserById(userId))) {
      userRepository.deleteById(userId);
      return true;
    }
    return false;
  }

  public User getUserByEmailId(String emailId) {
    logger.info("getting user by email id:{}", emailId);
    return userRepository.findByEmail(emailId);
  }

}
