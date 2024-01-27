package com.maveric.account.restinterfaces;

import com.maveric.account.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service",url = "localhost:8000/user-service")
public interface UserRestInterface {

  @GetMapping("/users/{userId}")
  User getUser(@PathVariable int userId);
}
