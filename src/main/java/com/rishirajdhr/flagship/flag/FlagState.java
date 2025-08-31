package com.rishirajdhr.flagship.flag;

/**
 * Represents the state of a feature flag.
 *
 * @param flag the name of the flag
 * @param enabled the state of the flag - {@code true} if enabled, {@code false} otherwise
 */
public record FlagState(String flag, boolean enabled) {}
