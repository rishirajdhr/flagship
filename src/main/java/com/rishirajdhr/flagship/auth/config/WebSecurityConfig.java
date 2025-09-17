package com.rishirajdhr.flagship.auth.config;

import com.rishirajdhr.flagship.auth.jwt.JWTFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures Spring Security options for Web security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  private final JWTFilter jwtFilter;

  /**
   * Create a web security configuration for the application.
   *
   * @param jwtFilter the JWT-based filter to use for authenticating incoming HTTP requests
   */
  public WebSecurityConfig(JWTFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/api/login", "/api/signup").permitAll()
                .anyRequest().authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
