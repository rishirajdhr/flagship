package com.rishirajdhr.flagship.auth;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a user of the application.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AppUser {

  @Id @GeneratedValue
  private Long id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  /**
   * No-arg constructor used by JPA to instantiate a {@link AppUser} object.
   */
  protected AppUser() {}

  /**
   * Create a new application user.
   *
   * @param username the username of the user
   * @param password the password of the user; must already be encoded
   */
  public AppUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Get the ID of the application user.
   *
   * @return the user's ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the timestamp when this user was created.
   *
   * @return the {@link Instant} representing the creation time
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Get the timestamp when this user was last modified.
   *
   * @return tbe {@link Instant} representing the last modification time
   */
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Get the username of the user.
   *
   * @return the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Get the encoded password of the user.
   *
   * @return the user's encoded password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the username of the user.
   *
   * @param username the new username of the user
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Set the password of the user.
   *
   * @param password the new password of the user; must already be encoded
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
