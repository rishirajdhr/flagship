package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.flag.exceptions.FlagNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  /**
   * Get a feature flag by its name.
   *
   * @param name the name of the flag
   * @return an {@link Optional} containing the flag if it exists
   */
  public Optional<Flag> getFlagByName(String name) {
    return repository.findFlagByName(name);
  }

  /**
   * Update an existing feature flag by its name.
   *
   * @param name the name of the flag
   * @param updateFlag the {@link UpdateFlag} payload with the updated data
   * @return the updated feature flag
   * @throws FlagNotFoundException if no flag exists with the given name
   */
  public Flag updateFlagByName(String name, UpdateFlag updateFlag) throws FlagNotFoundException {
    Flag flag = getFlagByName(name).orElseThrow(() -> new FlagNotFoundException(name));

    if (updateFlag.description() != null) {
      flag.setDescription(updateFlag.description());
    }

    if (updateFlag.enabled() != null) {
      flag.setEnabled(updateFlag.enabled());
    }

    return repository.save(flag);
  }
}
