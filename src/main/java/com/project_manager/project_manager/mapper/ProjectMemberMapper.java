package com.project_manager.project_manager.mapper;

import com.project_manager.project_manager.dto.ProjectMemberDto;
import com.project_manager.project_manager.model.ProjectMember;

public class ProjectMemberMapper {

  public static ProjectMemberDto toDto(ProjectMember projectMember) {
    return ProjectMemberDto.builder()
        .id(projectMember.getId())
        .user(UserMapper.toDto(projectMember.getUser()))
        .projectRole(projectMember.getProjectRole())
        .active(projectMember.getActive())
        .status(projectMember.getStatus())
        .projectJoinDate(projectMember.getProjectJoinDate())
        .projectLeaveDate(projectMember.getProjectLeaveDate())
        .build();
  }
}
