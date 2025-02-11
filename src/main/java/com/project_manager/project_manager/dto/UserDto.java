package com.project_manager.project_manager.dto;

import com.project_manager.project_manager.shared.Role;
import lombok.Builder;

@Builder
public record UserDto(
    Long id,
    String email,
    Role role,
    String name,
    String surname
) {

}
