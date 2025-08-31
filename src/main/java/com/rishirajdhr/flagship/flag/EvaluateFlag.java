package com.rishirajdhr.flagship.flag;

import jakarta.validation.constraints.NotNull;

/**
 * Represents the payload for retrieving the state of a feature flag.
 *
 * @param flag the name of the flag
 */
public record EvaluateFlag(@NotNull String flag) {}
