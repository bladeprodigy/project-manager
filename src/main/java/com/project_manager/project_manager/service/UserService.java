package com.project_manager.project_manager.service;

import com.project_manager.project_manager.dto.MeDto;
import com.project_manager.project_manager.dto.UserDetailsDto;
import com.project_manager.project_manager.dto.UserDto;
import com.project_manager.project_manager.dto.request.EditUserRequest;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.mapper.UserMapper;
import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.model.User;
import com.project_manager.project_manager.model.repository.UserRepository;
import com.project_manager.project_manager.shared.Role;
import com.project_manager.project_manager.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final SecurityUtils securityUtils;

  public List<UserDto> findAllUsers() {
    return repository.findAll().stream().map(UserMapper::toDto).toList();
  }

  public UserDetailsDto findUserWithProjects(Long id) {
    User user = findUserById(id);
    List<Project> projects = new ArrayList<>();
    List<Project> projectsManaged = new ArrayList<>();

    user.getProjectMembers().forEach(pm -> projects.add(pm.getProject()));
    user.getProjectManagers().forEach(pm -> projectsManaged.add(pm.getProject()));

    return UserDetailsDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .name(user.getName())
        .surname(user.getSurname())
        .projects(projects.stream().map(ProjectMapper::toDto).toList())
        .projectsManaged(projectsManaged.stream().map(ProjectMapper::toDto).toList())
        .build();
  }

  public MeDto me() {
    User me = securityUtils.getCurrentUser();

    return MeDto.builder()
        .id(me.getId())
        .role(me.getRole())
        .build();
  }

  public UserDto editUser(EditUserRequest request, Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    User user = findUserById(id);

    if (user.getRole() == Role.ADMIN) {
      throw new IllegalArgumentException("Cannot edit another admin users data");
    }

    if (!request.email().isBlank()) {
      user.setEmail(request.email());
    }

    if (request.role() != null) {
      user.setRole(request.role());
    }

    if (!request.name().isBlank()) {
      user.setName(request.name());
    }

    if (!request.surname().isBlank()) {
      user.setSurname(request.surname());
    }

    repository.save(user);
    return UserMapper.toDto(user);
  }

  public String deleteUser(Long id) {
    securityUtils.checkIfCurrentUserIsAdmin();
    User user = findUserById(id);
    repository.delete(user);
    return "User with id " + id + " has been deleted";
  }

  public User findUserById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
  }
}
