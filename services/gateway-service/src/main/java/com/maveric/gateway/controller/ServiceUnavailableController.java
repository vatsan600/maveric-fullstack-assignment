package com.maveric.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ServiceUnavailableController {

  @RequestMapping("/serviceUnavailable")
  public Mono<ResponseEntity<String>> serviceUnavailable() {
    return  Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
  }
}
