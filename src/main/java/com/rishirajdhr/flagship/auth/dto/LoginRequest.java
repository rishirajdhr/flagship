package com.rishirajdhr.flagship.auth.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Payload for a login request.
 *
 * @param username the username of the user attempting to log in
 * @param password the raw, non-encoded password of the user attempting to log in
 */
public record LoginRequest(@NotNull String username, @NotNull String password) {}
