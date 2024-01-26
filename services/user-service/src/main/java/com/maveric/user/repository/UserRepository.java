package com.maveric.user.repository;

import com.maveric.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

}
