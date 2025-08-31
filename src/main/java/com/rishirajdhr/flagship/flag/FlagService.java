package com.rishirajdhr.flagship.flag;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Encapsulates business logic for feature flags.
 */
@Service
public class FlagService {
  private final FlagRepository repository;

  /**
   * Create a new service to access and modify feature flags.
   *
   * @param repository the repository that provides database access to {@link Flag} entities
   */
  public FlagService(FlagRepository repository) {
    this.repository = repository;
  }

  /**
   * Create a new feature flag.
   *
   * @param name the name of the feature flag
   * @param description the description of the feature flag
   * @param enabled the feature flag status - {@code} true if enabled, {@code false} otherwise
   * @return the newly created feature flag
   */
  public Flag createFlag(String name, String description, boolean enabled) {
    Flag flag = new Flag(name, description, enabled);
    return repository.save(flag);
  }

  /**
   * Get a list of all existing feature flags.
   *
   * @return feature flags list
   */
  public List<Flag> getAllFlags() {
    return repository.findAll();
  }
}
