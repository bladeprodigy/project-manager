package com.project_manager.project_manager.mapper;

import com.project_manager.project_manager.dto.ProjectManagerDto;
import com.project_manager.project_manager.model.ProjectManager;

public class ProjectManagerMapper {

  public static ProjectManagerDto toDto(ProjectManager projectManager) {
    return ProjectManagerDto.builder()
        .id(projectManager.getId())
        .user(UserMapper.toDto(projectManager.getUser()))
        .active(projectManager.getActive())
        .status(projectManager.getStatus())
        .projectJoinDate(projectManager.getProjectJoinDate())
        .projectLeaveDate(projectManager.getProjectLeaveDate())
        .build();
  }
}
