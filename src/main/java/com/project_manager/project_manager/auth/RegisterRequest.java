package com.project_manager.project_manager.auth;

import com.project_manager.project_manager.validation.ValidEmail;
import com.project_manager.project_manager.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
    @ValidEmail
    String email,
    @ValidPassword
    String password,
    @NotBlank
    String name,
    @NotBlank
    String surname
) {

}
