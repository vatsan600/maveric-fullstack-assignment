package com.maveric.user.service;

import com.maveric.user.entity.User;
import com.maveric.user.repository.UserRepository;
import java.util.List;
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
    logger.info("getting users for page number: {} and page size: {}",pageNumber,pageNumber);
    Pageable page = PageRequest.of(pageNumber, pageSize);
    return userRepository.findAll(page);
  }

  public User createUser(User user) {
    logger.info("creating new users");
    return userRepository.save(user);
  }


}
