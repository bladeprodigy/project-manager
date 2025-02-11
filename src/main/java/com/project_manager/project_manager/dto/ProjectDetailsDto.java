package com.project_manager.project_manager.dto;

import com.project_manager.project_manager.shared.ProjectStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record ProjectDetailsDto(
    Long id,
    String name,
    String description,
    String clientName,
    LocalDate creationDate,
    ProjectStatus status,
    ProjectManagerDto activeProjectManager,
    List<ProjectMemberDto> activeProjectMembers,
    List<ProjectManagerDto> inactiveProjectManagers,
    List<ProjectMemberDto> inactiveProjectMembers,
    List<ProjectReportDto> projectReports
) {

}
