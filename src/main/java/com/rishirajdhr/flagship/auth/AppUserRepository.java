package com.rishirajdhr.flagship.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents a repository to access and interact with {@link AppUser} entities.
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  /**
   * Find an application user by their username.
   *
   * @param username the username of the user
   * @return an {@link Optional} containing the user if found
   */
  Optional<AppUser> findUserByUsername(String username);
}
