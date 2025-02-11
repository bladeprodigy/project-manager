package com.project_manager.project_manager.auth;

public record LoginRequest(
    String email,
    String password
) {

}
