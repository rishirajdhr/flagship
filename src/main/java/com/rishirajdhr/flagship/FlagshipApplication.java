package com.rishirajdhr.flagship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlagshipApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlagshipApplication.class, args);
  }
}
