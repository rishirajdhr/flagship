package com.rishirajdhr.flagship.flag.dto;

import com.rishirajdhr.flagship.flag.Flag;

import java.time.Instant;

/**
 * Represents the data for a flag sent in a response to a flag request.
 *
 * @param id the flag ID
 * @param name the flag name
 * @param description the flag description
 * @param enabled the flag status - {@code true} if the flag is enabled, false otherwise
 * @param projectId the ID of the flag's project
 * @param owner the username of the flag's owner
 * @param createdAt the timestamp when the flag was created
 * @param updatedAt the timestamp when the flag was last modified
 */
public record FlagResponse(
    Long id,
    String name,
    String description,
    boolean enabled,
    Long projectId,
    String owner,
    Instant createdAt,
    Instant updatedAt) {
  /**
   * Create a flag response object for a flag.
   *
   * @param flag the flag to create the response for
   * @return the created flag response
   */
  public static FlagResponse fromFlag(Flag flag) {
    return new FlagResponse(
        flag.getId(),
        flag.getName(),
        flag.getDescription(),
        flag.isEnabled(),
        flag.getProject().getId(),
        flag.getProject().getOwner().getUsername(),
        flag.getCreatedAt(),
        flag.getUpdatedAt());
  }
}
