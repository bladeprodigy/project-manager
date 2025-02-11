package com.project_manager.project_manager.controller;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectDto;
import com.project_manager.project_manager.dto.request.CreateProjectRequest;
import com.project_manager.project_manager.dto.request.ProjectUpdateRequest;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.service.ProjectService;
import com.project_manager.project_manager.shared.ProjectStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
class ProjectController {

  private final ProjectService service;

  @PostMapping
  ResponseEntity<ProjectDto> create(@Valid @RequestBody CreateProjectRequest request) {
    return ResponseEntity.ok(service.createProject(request));
  }

  @GetMapping
  ResponseEntity<Page<ProjectDto>> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String clientName,
      @RequestParam(required = false) ProjectStatus status,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<ProjectDto> dtoPage = service.findAllProjects(name, clientName, status, sortBy, sortDir,
        page, size).map(ProjectMapper::toDto);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/{id}")
  ResponseEntity<ProjectDetailsDto> getProject(@PathVariable Long id) {
    return ResponseEntity.ok(service.findProject(id));
  }

  @PatchMapping("{id}/status")
  ResponseEntity<ProjectDetailsDto> changeProjectStatus(@PathVariable Long id, @RequestParam
  ProjectStatus status) {
    return ResponseEntity.ok(service.changeProjectStatus(status, id));
  }

  @PatchMapping("/{id}")
  ResponseEntity<ProjectDetailsDto> updateProject(@PathVariable Long id,
      @RequestBody ProjectUpdateRequest request) {
    return ResponseEntity.ok(service.updateProject(request, id));
  }

  @DeleteMapping("/{id}")
  ResponseEntity<String> deleteProject(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteProject(id));
  }
}
