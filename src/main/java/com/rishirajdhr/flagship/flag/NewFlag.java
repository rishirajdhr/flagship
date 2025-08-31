package com.rishirajdhr.flagship.flag;

/**
 * Represents the payload for creating a new flag.
 *
 * @param name the name of the flag
 * @param description the description of the flag
 * @param enabled the status of the flag - {@code true} if enabled, {@code false} otherwise
 */
public record NewFlag(String name, String description, boolean enabled) {}
