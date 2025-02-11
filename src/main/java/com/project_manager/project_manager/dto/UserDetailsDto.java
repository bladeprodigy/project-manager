package com.project_manager.project_manager.dto;

import com.project_manager.project_manager.shared.Role;
import java.util.List;
import lombok.Builder;

@Builder
public record UserDetailsDto(
    Long id,
    String email,
    Role role,
    String name,
    String surname,
    List<ProjectDto> projects,
    List<ProjectDto> projectsManaged
) {

}
