package com.rishirajdhr.flagship.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures Spring Security options for Authentication.
 */
@Configuration
public class AuthSecurityConfig {
  /**
   * Configure the password encoder to be used for storing application user passwords.
   *
   * @return the encoder to be used to encode passwords
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
