package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.project.Project;

import java.time.Instant;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
* Represents a feature flag with a boolean status and additional metadata (e.g. name, description).
*/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "key", "project_id" }))
public class Flag {
  private static final Pattern FLAG_KEY_PATTERN = Pattern.compile("^[a-z]+(?:[-_][a-z0-9]+)*$");

  @Id @GeneratedValue
  private Long id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  private String key;

  private String name;
  private String description;
  private boolean enabled;

  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
  private Project project;

  /**
   * No-arg constructor used by JPA to instantiate a {@link Flag} object.
   */
  protected Flag() {}

  /**
   * Create a new feature flag.
   *
   * @param key the key for the feature flag
   * @param name the name of the feature flag
   * @param description a plain-text description of the feature flag
   * @param enabled {@code true} if the flag should be enabled, {@code false} otherwise
   * @param project the project of the feature flag
   * @throws IllegalArgumentException if the flag name or description is invalid
   */
  public Flag(String key, String name, String description, boolean enabled, Project project) throws IllegalArgumentException {
    this.key = validateKey(key);
    this.name = validateName(name);
    this.description = validateDescription(description);
    this.enabled = enabled;
    this.project = project;
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
   * Get the project that this feature flag belongs to.
   *
   * @return the feature flag's parent project
   */
  public Project getProject() {
    return project;
  }

  /**
   * Get the key of the feature flag.
   *
   * @return the feature flag's key
   */
  public String getKey() {
    return key;
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
   * Set the key of the feature flag.
   *
   * @param key the key to be set
   * @throws IllegalArgumentException if the key is invalid
   */
  public void setKey(String key) throws IllegalArgumentException {
    this.key = validateKey(key);
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

  /**
   * Validate a feature flag key according to the following rules:
   *
   * <ul>
   *   <li>The key must only contain lowercase alphabets (a-z), digits (0-9), hyphens (-), or underscores (_).</li>
   *   <li>The key must start with a lowercase alphabet.</li>
   *   <li>The key must not end with a hyphen (-) or underscore (_).</li>
   * </ul>
   *
   * @param key the key to validate
   * @return the validated key
   * @throws IllegalArgumentException if the key is {@code null} or invalid
   */
  private String validateKey(String key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }

    if (!FLAG_KEY_PATTERN.matcher(key).matches()) {
      throw new IllegalArgumentException("Key is not valid");
    }

    return key;
  }
}
