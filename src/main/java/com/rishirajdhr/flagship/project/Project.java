package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.auth.AppUser;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a project for which feature flags can be configured.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {

  @Id @GeneratedValue
  private Long id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  @NotNull
  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotNull
  @Column(nullable = false)
  private String description;

  @ManyToOne
  @NotNull
  private AppUser owner;

  /**
   * No-arg constructor used by JPA to instantiate a {@link Project} object.
   */
  protected Project() {}

  /**
   * Create a new project.
   *
   * @param name the name of the project
   * @param description the description of the project
   * @param owner the owner of the project
   */
  public Project(String name, String description, AppUser owner) {
    this.name = name;
    this.description = description;
    this.owner = owner;
  }

  /**
   * Get the project ID.
   *
   * @return the project ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the timestamp when the project was created.
   *
   * @return the {@link Instant} representing the creation time
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Get the timestamp when the project was last modified.
   *
   * @return the {@link Instant} representing the last modified time.
   */
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Get the owner of the project.
   *
   * @return the project owner
   */
  public AppUser getOwner() {
    return owner;
  }

  /**
   * Get the name of the project.
   *
   * @return the project name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the description of the project.
   *
   * @return the project description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set the name of the project.
   *
   * @param name the new name of the project
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the description of the project.
   *
   * @param description the new description of the project
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
