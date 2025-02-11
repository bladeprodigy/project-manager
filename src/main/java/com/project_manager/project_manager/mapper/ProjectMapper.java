package com.project_manager.project_manager.mapper;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectDto;
import com.project_manager.project_manager.dto.ProjectManagerDto;
import com.project_manager.project_manager.dto.ProjectMemberDto;
import com.project_manager.project_manager.dto.ProjectReportDto;
import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.model.ProjectManager;
import com.project_manager.project_manager.model.ProjectMember;
import java.util.List;

public class ProjectMapper {

  public static ProjectDto toDto(Project project) {
    return ProjectDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .clientName(project.getClientName())
        .creationDate(project.getCreationDate())
        .status(project.getStatus())
        .build();
  }

  public static ProjectDetailsDto toDetailsDto(Project project) {
    ProjectManagerDto activeProjectManager = project.getProjectManagers().stream().filter(
        ProjectManager::getActive).map(ProjectManagerMapper::toDto).findFirst().orElse(null);

    List<ProjectManagerDto> inactiveProjectManagers = project.getProjectManagers().stream()
        .filter(pm -> !pm.getActive()).map(ProjectManagerMapper::toDto).toList();

    List<ProjectMemberDto> activeProjectMembers = project.getProjectMembers().stream()
        .filter(ProjectMember::getActive).map(ProjectMemberMapper::toDto).toList();

    List<ProjectMemberDto> inactiveProjectMembers = project.getProjectMembers().stream()
        .filter(pm -> !pm.getActive()).map(ProjectMemberMapper::toDto).toList();

    List<ProjectReportDto> projectReports = project.getProjectReports().stream().map(
        ProjectReportMapper::toDto).toList();

    return ProjectDetailsDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .clientName(project.getClientName())
        .creationDate(project.getCreationDate())
        .status(project.getStatus())
        .activeProjectManager(activeProjectManager)
        .inactiveProjectManagers(inactiveProjectManagers)
        .activeProjectMembers(activeProjectMembers)
        .inactiveProjectMembers(inactiveProjectMembers)
        .projectReports(projectReports)
        .build();
  }
}
