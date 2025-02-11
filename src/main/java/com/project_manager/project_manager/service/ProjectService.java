package com.project_manager.project_manager.service;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectDto;
import com.project_manager.project_manager.dto.request.CreateProjectRequest;
import com.project_manager.project_manager.dto.request.ProjectUpdateRequest;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.model.repository.ProjectRepository;
import com.project_manager.project_manager.shared.ProjectStatus;
import com.project_manager.project_manager.utils.ProjectSpecification;
import com.project_manager.project_manager.utils.SecurityUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository repository;
  private final SecurityUtils securityUtils;

  public ProjectDto createProject(CreateProjectRequest request) {
    securityUtils.checkIfCurrentUserIsAdmin();
    validateProjectName(request.name());

    Project project = Project.builder()
        .name(request.name())
        .description(request.description())
        .clientName(request.clientName())
        .creationDate(LocalDate.now())
        .status(ProjectStatus.NEW)
        .build();

    repository.save(project);
    return ProjectMapper.toDto(findProjectById(project.getId()));
  }

  public Page<Project> findAllProjects(String name, String clientName, ProjectStatus status,
      String sortBy, String sortDir, int page, int size) {
    Specification<Project> spec = Specification.where(null);

    if (name != null && !name.isEmpty()) {
      spec = spec.and(ProjectSpecification.hasName(name));
    }
    if (clientName != null && !clientName.isEmpty()) {
      spec = spec.and(ProjectSpecification.hasClientName(clientName));
    }
    if (status != null) {
      spec = spec.and(ProjectSpecification.hasStatus(status));
    }

    Sort sort = sortDir.equalsIgnoreCase("asc")
        ? Sort.by(sortBy).ascending()
        : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return repository.findAll(spec, pageable);
  }

  public ProjectDetailsDto findProject(Long id) {
    return ProjectMapper.toDetailsDto(findProjectById(id));
  }

  public ProjectDetailsDto changeProjectStatus(ProjectStatus status, Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    Project project = findProjectById(id);

    if (project.getStatus() == status) {
      throw new IllegalArgumentException("Cannot change status to the one it already has");
    }

    project.setStatus(status);
    repository.save(project);
    return ProjectMapper.toDetailsDto(project);
  }

  public ProjectDetailsDto updateProject(ProjectUpdateRequest request, Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    Project project = findProjectById(id);

    if (!request.name().isBlank()) {
      project.setName(request.name());
    }

    if (request.description() != null) {
      project.setDescription(request.description());
    }

    if (!request.clientName().isBlank()) {
      project.setClientName(request.clientName());
    }

    return ProjectMapper.toDetailsDto(project);
  }

  public String deleteProject(Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    Project project = findProjectById(id);
    repository.delete(project);
    return "Project with id " + id + " has been deleted";
  }

  public Project findProjectById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("project with id: " + id + " not found"));
  }

  private void validateProjectName(String name) {
    if (repository.existsByName(name)) {
      throw new EntityExistsException("Project with name: " + name + " already exists");
    }
  }
}
