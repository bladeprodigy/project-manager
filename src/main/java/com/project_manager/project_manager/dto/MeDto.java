package com.project_manager.project_manager.dto;

import com.project_manager.project_manager.shared.Role;
import lombok.Builder;

@Builder
public record MeDto(
    Long id,
    Role role
) {

}
