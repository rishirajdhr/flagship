package com.rishirajdhr.flagship.flag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Represents a repository to access and modify {@link Flag} entities.
 */
public interface FlagRepository extends JpaRepository<Flag, Long> {
  /**
   * Get a feature flag by its name.
   *
   * @param name the name of the flag
   * @return an {@link Optional} containing the flag if found
   */
  Optional<Flag> findFlagByName(String name);
}
