package com.project_manager.project_manager.dto;

import com.project_manager.project_manager.shared.ProjectStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectDto(
    Long id,
    String name,
    String description,
    String clientName,
    LocalDate creationDate,
    ProjectStatus status
) {

}
