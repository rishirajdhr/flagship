package com.rishirajdhr.flagship.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Provides access to the application user who is currently logged in.
 */
@Component
public class AppUserProvider {
  private final AppUserRepository appUserRepository;

  /**
   * Create an app user provider that provides access to the currently logged-in user.
   *
   * @param appUserRepository the repository that provides access to application user entities
   */
  public AppUserProvider(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  /**
   * Get the username of the logged-in user.
   *
   * @return the username if a user is logged-in, {@code null} otherwise
   */
  public String getUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) return null;

    Object principal = authentication.getPrincipal();
    return (principal instanceof UserDetails ud) ? ud.getUsername() : principal.toString();
  }

  /**
   * Get the currently logged-in application user.
   *
   * @return the currently logged-in user if exists, {@code null} otherwise
   */
  public AppUser getLoggedInAppUser() {
    String username = getUsername();
    if (username == null) return null;

    return appUserRepository.findUserByUsername(username).orElse(null);
  }
}
