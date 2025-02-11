package com.project_manager.project_manager.service;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectManagerDto;
import com.project_manager.project_manager.dto.request.CreateProjectParticipantRequest;
import com.project_manager.project_manager.mapper.ProjectManagerMapper;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.model.ProjectManager;
import com.project_manager.project_manager.model.repository.ProjectManagerRepository;
import com.project_manager.project_manager.utils.SecurityUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectManagerService {

  private final ProjectManagerRepository repository;
  private final UserService userService;
  private final ProjectService projectService;
  private final SecurityUtils securityUtils;

  public ProjectDetailsDto createProjectManager(CreateProjectParticipantRequest request) {
    securityUtils.checkIfCurrentUserIsAdmin();
    validateProjectManagerCreation(request);

    ProjectManager manager = ProjectManager.builder()
        .user(userService.findUserById(request.userId()))
        .project(projectService.findProjectById(request.projectId()))
        .active(true)
        .projectJoinDate(LocalDate.now())
        .build();

    repository.save(manager);

    return ProjectMapper.toDetailsDto(projectService.findProjectById(request.projectId()));
  }

  public ProjectManagerDto changeProjectManagerActiveStatus(boolean active, Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    ProjectManager manager = findProjectManagerById(id);

    validateProjectManagerActiveStatusChange(manager, active);

    manager.setActive(active);
    repository.save(manager);

    return ProjectManagerMapper.toDto(findProjectManagerById(id));
  }

  public ProjectManagerDto updateProjectManagerWorkStatus(Long projectId, Long userId,
      String status) {
    ProjectManager manager = findByProjectIdAndUserId(projectId, userId);

    if (securityUtils.getCurrentUser() != manager.getUser()) {
      throw new IllegalCallerException("You cannot edit other users work status");
    }

    manager.setStatus(status);
    repository.save(manager);

    return ProjectManagerMapper.toDto(findProjectManagerById(manager.getId()));
  }

  public ProjectManager findProjectManagerById(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Project Manager with id: " + id + " not found"));
  }

  private ProjectManager findByProjectIdAndUserId(Long projectId, Long userId) {
    return repository.findByProjectIdAndUserId(projectId, userId).orElseThrow(
        () -> new EntityNotFoundException(
            "Project Manager for project with id " + projectId + " and user with id " + userId
                + " not found"));
  }

  private void validateProjectManagerCreation(CreateProjectParticipantRequest request) {
    if (repository.existsByProjectIdAndUserId(request.projectId(), request.userId())) {
      throw new EntityExistsException(
          "Project Manager for project with id " + request.projectId() + " and user with id "
              + request.userId() + " already exists");
    }

    Project project = projectService.findProjectById(request.projectId());

    if (!project.getProjectManagers().stream().filter(ProjectManager::getActive).toList()
        .isEmpty()) {
      throw new EntityExistsException(
          "Cannot add manager for a project with already existing active manager");
    }
  }

  private void validateProjectManagerActiveStatusChange(ProjectManager manager, boolean active) {
    if (manager.getActive() == active) {
      throw new IllegalArgumentException("Cannot change active from " + active + " to " + active);
    }

    Project project = manager.getProject();

    if (active && !project.getProjectManagers().stream().filter(ProjectManager::getActive).toList()
        .isEmpty()) {
      throw new EntityExistsException("There can be maximum one active project manager");
    }
  }
}
