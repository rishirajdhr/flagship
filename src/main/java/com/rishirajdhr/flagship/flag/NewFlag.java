package com.rishirajdhr.flagship.flag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents the payload for creating a new flag.
 *
 * @param key the key of the flag
 * @param name the name of the flag
 * @param description the description of the flag
 * @param enabled the status of the flag - {@code true} if enabled, {@code false} otherwise
 */
public record NewFlag(
    @NotNull
    @NotBlank
    String key,

    @NotNull
    @NotBlank
    String name,

    @NotNull
    String description,

    boolean enabled
) {}
