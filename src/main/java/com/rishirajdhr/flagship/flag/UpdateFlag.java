package com.rishirajdhr.flagship.flag;

import org.springframework.lang.Nullable;

/**
 * Represents the payload for updating an existing feature flag. The payload may omit fields that
 * do not need to be updated.
 *
 * @param description the updated description of the flag
 * @param enabled the updated status of the flag - {@code true} if enabled, {@code false} otherwise
 */
public record UpdateFlag(@Nullable String description, @Nullable Boolean enabled) {}
