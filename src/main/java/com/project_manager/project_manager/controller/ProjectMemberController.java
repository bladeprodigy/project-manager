package com.project_manager.project_manager.controller;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectMemberDto;
import com.project_manager.project_manager.dto.request.CreateProjectParticipantRequest;
import com.project_manager.project_manager.service.ProjectMemberService;
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
@RequestMapping("projects/project-members")
@RequiredArgsConstructor
class ProjectMemberController {

  private final ProjectMemberService service;

  @PostMapping
  ResponseEntity<ProjectDetailsDto> createProjectMemberForProject(@Valid @RequestBody
  CreateProjectParticipantRequest request) {
    return ResponseEntity.ok(service.createProjectMember(request));
  }

  @PatchMapping("/{id}/active")
  ResponseEntity<ProjectMemberDto> changeProjectMemberActiveStatus(@RequestParam boolean active,
      @PathVariable Long id) {
    return ResponseEntity.ok(service.changeProjectMemberActiveStatus(active, id));
  }

  @PatchMapping("/{projectId}/{userId}")
  ResponseEntity<ProjectMemberDto> changeProjectMemberWorkStatus(@PathVariable Long projectId,
      @PathVariable Long userId, @RequestParam String status) {
    return ResponseEntity.ok(service.updateProjectMemberWorkStatus(projectId, userId, status));
  }
}
