package com.project_manager.project_manager.service;

import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.ProjectMemberDto;
import com.project_manager.project_manager.dto.request.CreateProjectParticipantRequest;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.mapper.ProjectMemberMapper;
import com.project_manager.project_manager.model.ProjectMember;
import com.project_manager.project_manager.model.repository.ProjectMemberRepository;
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
public class ProjectMemberService {

  private final ProjectMemberRepository repository;
  private final UserService userService;
  private final ProjectService projectService;
  private final SecurityUtils securityUtils;

  public ProjectDetailsDto createProjectMember(CreateProjectParticipantRequest request) {
    securityUtils.checkIfCurrentUserIsAdmin();
    validateProjectMemberCreation(request);

    ProjectMember member = ProjectMember.builder()
        .user(userService.findUserById(request.userId()))
        .project(projectService.findProjectById(request.projectId()))
        .active(true)
        .projectJoinDate(LocalDate.now())
        .build();

    repository.save(member);

    return ProjectMapper.toDetailsDto(projectService.findProjectById(request.projectId()));
  }

  public ProjectMemberDto changeProjectMemberActiveStatus(boolean active, Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    ProjectMember member = findProjectMemberById(id);

    if (member.getActive() == active) {
      throw new IllegalArgumentException("Cannot change active from " + active + " to " + active);
    }

    member.setActive(active);
    repository.save(member);

    return ProjectMemberMapper.toDto(findProjectMemberById(id));
  }

  public ProjectMemberDto updateProjectMemberWorkStatus(Long projectId, Long userId,
      String status) {
    ProjectMember projectMember = findByProjectIdAndUserId(projectId, userId);

    if (securityUtils.getCurrentUser() != projectMember.getUser()) {
      throw new IllegalCallerException("You cannot edit other users work status");
    }

    projectMember.setStatus(status);
    repository.save(projectMember);

    return ProjectMemberMapper.toDto(findProjectMemberById(projectMember.getId()));
  }

  public ProjectMember findProjectMemberById(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Project Member with id: " + id + " not found"));
  }

  private ProjectMember findByProjectIdAndUserId(Long projectId, Long userId) {
    return repository.findByProjectIdAndUserId(projectId, userId).orElseThrow(
        () -> new EntityNotFoundException(
            "Project Member for project with id " + projectId + " and user with id " + userId
                + " not found"));
  }

  private void validateProjectMemberCreation(CreateProjectParticipantRequest request) {
    if (repository.existsByProjectIdAndUserId(request.projectId(), request.userId())) {
      throw new EntityExistsException(
          "Project Member for project with id " + request.projectId() + " and user with id "
              + request.userId() + " already exists");
    }
  }
}
