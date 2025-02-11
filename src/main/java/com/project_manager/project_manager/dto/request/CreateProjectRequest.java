package com.project_manager.project_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
    @NotBlank
    @Size(min = 3, max = 50)
    String name,
    @Size(max = 500)
    String description,
    @NotBlank
    @Size(min = 3, max = 50)
    String clientName
    ) {

}
