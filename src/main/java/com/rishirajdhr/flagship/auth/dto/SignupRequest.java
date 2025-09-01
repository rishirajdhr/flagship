package com.rishirajdhr.flagship.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload for a sign-up request.
 *
 * @param username the username of the new user
 * @param password the raw, non-encoded password of the new user
 */
public record SignupRequest(
    @NotNull
    @NotBlank
    String username,

    @NotNull
    @NotBlank
    String password
) {}
