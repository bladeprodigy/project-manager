package com.project_manager.project_manager.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateProjectParticipantRequest(
    @NotNull
    Long userId,
    @NotNull
    Long projectId
) {

}
