package com.project_manager.project_manager.mapper;

import com.project_manager.project_manager.dto.ProjectReportDto;
import com.project_manager.project_manager.model.ProjectReport;

public class ProjectReportMapper {

  public static ProjectReportDto toDto(ProjectReport report) {
    return ProjectReportDto.builder()
        .id(report.getId())
        .reportDate(report.getReportDate())
        .title(report.getTitle())
        .content(report.getContent())
        .build();
  }
}
