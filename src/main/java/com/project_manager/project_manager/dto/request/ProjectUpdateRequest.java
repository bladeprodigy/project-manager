package com.project_manager.project_manager.dto.request;

import lombok.Builder;

@Builder
public record ProjectUpdateRequest(
    String name,
    String description,
    String clientName
) {

}
