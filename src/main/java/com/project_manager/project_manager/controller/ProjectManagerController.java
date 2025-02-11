package com.project_manager.project_manager.controller;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectManagerDto;
import com.project_manager.project_manager.dto.request.CreateProjectParticipantRequest;
import com.project_manager.project_manager.service.ProjectManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects/project-managers")
@RequiredArgsConstructor
class ProjectManagerController {

  private final ProjectManagerService service;

  @PostMapping
  ResponseEntity<ProjectDetailsDto> createProjectManagerForProject(@Valid @RequestBody
  CreateProjectParticipantRequest request) {
    return ResponseEntity.ok(service.createProjectManager(request));
  }

  @PatchMapping("/{id}/active")
  ResponseEntity<ProjectManagerDto> changeProjectManagerActiveStatus(@RequestParam boolean active,
      @PathVariable Long id) {
    return ResponseEntity.ok(service.changeProjectManagerActiveStatus(active, id));
  }

  @PatchMapping("/{projectId}/{userId}")
  ResponseEntity<ProjectManagerDto> changeProjectMemberWorkStatus(@PathVariable Long projectId,
      @PathVariable Long userId, @RequestParam String status) {
    return ResponseEntity.ok(service.updateProjectManagerWorkStatus(projectId, userId, status));
  }
}
