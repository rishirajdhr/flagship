package com.rishirajdhr.flagship.auth;

import com.rishirajdhr.flagship.auth.dto.LoginRequest;
import com.rishirajdhr.flagship.auth.dto.AuthResponse;
import com.rishirajdhr.flagship.auth.dto.SignupRequest;
import com.rishirajdhr.flagship.auth.jwt.JWTService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Exposes REST endpoints for authentication and authorization flows.
 */
@RestController
public class AuthController {
  private final JWTService jwtService;
  private final AppUserService appUserService;

  /**
   * Create a new controller to intercept auth requests.
   *
   * @param jwtService the service with utilities to work with JWTs
   * @param appUserService the service with methods to work with application users
   */
  public AuthController(JWTService jwtService, AppUserService appUserService) {
    this.jwtService = jwtService;
    this.appUserService = appUserService;
  }

  /**
   * Sign up a new user into the application.
   *
   * @param signupRequest the payload with the user's sign up information
   * @return the authenticated access information which can be used to authorize subsequent requests
   */
  @PostMapping("/signup")
  public AuthResponse signupUser(@RequestBody @Valid SignupRequest signupRequest) {
    String username = signupRequest.username();
    String rawPassword = signupRequest.password();
    UserDetails userDetails = appUserService.createUser(username, rawPassword);
    String token = jwtService.generateToken(userDetails);
    return new AuthResponse(token);
  }

  /**
   * Log in an existing user into the application.
   *
   * @param loginRequest the payload with the user's login credentials
   * @return the authenticated access information which can be used to authorize subsequent requests
   */
  @PostMapping("/login")
  public AuthResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
    String username = loginRequest.username();
    String rawPassword = loginRequest.password();
    UserDetails userDetails = appUserService.loginUser(username, rawPassword);
    String token = jwtService.generateToken(userDetails);
    return new AuthResponse(token);
  }
}
