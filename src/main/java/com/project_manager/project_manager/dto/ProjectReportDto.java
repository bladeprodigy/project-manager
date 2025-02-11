package com.project_manager.project_manager.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectReportDto(
    Long id,
    LocalDate reportDate,
    String title,
    String content
) {
}
