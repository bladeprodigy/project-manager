package com.project_manager.project_manager.dto.request;

import com.project_manager.project_manager.shared.Role;

public record EditUserRequest(
    String email,
    Role role,
    String name,
    String surname
) {

}
