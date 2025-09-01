package com.rishirajdhr.flagship.auth;

import com.rishirajdhr.flagship.auth.exceptions.InvalidCredentialsException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Encapsulates business logic for application users.
 */
@Service
public class AppUserService implements UserDetailsService {
  private final AppUserRepository repository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Create a new service to access and modify application users.
   *
   * @param repository the repository that provides database access to {@link AppUser} entities.
   * @param passwordEncoder the password encoder configured for the application.
   */
  public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = repository
        .findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

    return buildUserDetailsFromUser(appUser);
  }

  /**
   * Create a new application user.
   *
   * @param username the username of the new user
   * @param rawPassword the raw, non-encoded password of the new user
   * @return the {@link UserDetails} object representing the newly created user
   */
  public UserDetails createUser(String username, String rawPassword) {
    String encodedPassword = passwordEncoder.encode(rawPassword);
    AppUser appUser = new AppUser(username, encodedPassword);
    return buildUserDetailsFromUser(repository.save(appUser));
  }

  /**
   * Log in an existing application user.
   *
   * @param username the username of the existing user
   * @param rawPassword the raw, non-encoded password of the existing user
   * @return the {@link UserDetails} object representing the logged-in user
   * @throws InvalidCredentialsException if the credentials are invalid
   */
  public UserDetails loginUser(String username, String rawPassword) throws InvalidCredentialsException {
    Optional<AppUser> result = repository.findUserByUsername(username);
    if (result.isEmpty()) {
      throw new InvalidCredentialsException();
    }

    AppUser appUser = result.get();
    if (!passwordEncoder.matches(rawPassword, appUser.getPassword())) {
      throw new InvalidCredentialsException();
    }

    return buildUserDetailsFromUser(appUser);
  }

  /**
   * Build a {@link UserDetails} object representing an application user.
   *
   * @param appUser the user to build the object for
   * @return the newly built {@link UserDetails} object for the user
   */
  private UserDetails buildUserDetailsFromUser(AppUser appUser) {
    return org.springframework.security.core.userdetails.User
        .withUsername(appUser.getUsername())
        .password(appUser.getPassword())
        .roles("USER")
        .build();
  }
}
