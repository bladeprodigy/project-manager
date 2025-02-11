package com.project_manager.project_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectReportRequest(
    @NotBlank
    @Size(min = 3, max = 50)
    String title,
    @NotBlank
    @Size(min = 3, max = 1000)
    String content
) {

}
