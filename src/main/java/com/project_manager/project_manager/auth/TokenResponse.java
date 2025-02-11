package com.project_manager.project_manager.auth;

import lombok.Builder;

@Builder
public record TokenResponse(
    String token
) {

}
