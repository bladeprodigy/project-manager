package com.project_manager.project_manager.controller;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.request.CreateProjectReportRequest;
import com.project_manager.project_manager.service.ProjectReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects/project-reports")
@RequiredArgsConstructor
class ProjectReportController {

  private final ProjectReportService service;

  @PostMapping("/{id}")
  ResponseEntity<ProjectDetailsDto> createProjectReport(@Valid @RequestBody
      CreateProjectReportRequest request, @PathVariable Long id) {
    return ResponseEntity.ok(service.createProjectReport(request, id));
  }
}
