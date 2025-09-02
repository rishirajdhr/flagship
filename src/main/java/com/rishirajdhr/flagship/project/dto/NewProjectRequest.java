package com.rishirajdhr.flagship.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload for creating a new project.
 *
 * @param name the name of the new project
 * @param description the description of the new project
 */
public record NewProjectRequest(
    @NotNull
    @NotBlank
    String name,

    @NotNull
    String description
) {}
