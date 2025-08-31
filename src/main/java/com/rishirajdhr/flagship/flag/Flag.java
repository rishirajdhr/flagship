package com.rishirajdhr.flagship.flag;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
* Represents a feature flag with a boolean status and additional metadata (e.g. name, description).
*/
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Flag {

  @Id @GeneratedValue
  private Long id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  private String name;
  private String description;
  private boolean enabled;

  /**
   * No-arg constructor used by JPA to instantiate a {@link Flag} object.
   */
  protected Flag() {}

  /**
   * Create a new feature flag.
   *
   * @param name the name of the feature flag
   * @param description a plain-text description of the feature flag
   * @param enabled {@code true} if the flag should be enabled, {@code false} otherwise
   * @throws IllegalArgumentException if the flag name or description is invalid
   */
  public Flag(String name, String description, boolean enabled) throws IllegalArgumentException {
    this.name = validateName(name);
    this.description = validateDescription(description);
    this.enabled = enabled;
  }

  /**
   * Get the ID of the feature flag.
   *
   * @return the feature flag's ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the timestamp when this feature flag was created.
   *
   * @return the {@link Instant} representing the creation time
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Get the timestamp when this feature flag was last updated.
   *
   * @return the {@link Instant} representing the latest modification time
   */
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Get the name of the feature flag.
   *
   * @return the feature flag's name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the description of the feature flag.
   *
   * @return the feature flag's description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Check whether the feature flag is enabled.
   *
   * @return {@code true} if the feature flag is enabled, {@code false} otherwise
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Set the name of the feature flag.
   *
   * @param name the name to be set
   * @throws IllegalArgumentException if the name is invalid
   */
  public void setName(String name) throws IllegalArgumentException {
    this.name = validateName(name);
  }

  /**
   * Set the description of the feature flag.
   *
   * @param description the description to be set
   * @throws IllegalArgumentException if the description is invalid
   */
  public void setDescription(String description) throws IllegalArgumentException {
    this.description = validateDescription(description);
  }

  /**
   * Set the status of the feature flag.
   *
   * @param enabled {@code true} if the flag should be enabled, {@code false} otherwise
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return String.format("Flag(id=%s, name='%s', enabled=%b)", id, name, enabled);
  }

  /**
   * Validate a feature flag name.
   *
   * @param name the name to validate
   * @return the validated name
   * @throws IllegalArgumentException if the name is {@code null} or empty
   */
  private String validateName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Flag name cannot be null");
    }

    if (name.isBlank()) {
      throw new IllegalArgumentException("Flag name must be a non-empty string");
    }

    return name.strip();
  }

  /**
   * Validate a feature flag description.
   *
   * @param description the description to validate
   * @return the validated description
   * @throws IllegalArgumentException if the description is {@code null}
   */
  private String validateDescription(String description) throws IllegalArgumentException {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }

    return description.strip();
  }
}
