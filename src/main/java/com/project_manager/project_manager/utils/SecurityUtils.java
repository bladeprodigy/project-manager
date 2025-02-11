package com.project_manager.project_manager.utils;

import com.project_manager.project_manager.model.User;
import com.project_manager.project_manager.model.repository.UserRepository;
import com.project_manager.project_manager.shared.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

  private final UserRepository userRepository;

  public void checkIfCurrentUserIsAdmin() {
    if (getCurrentUser().getRole() != Role.ADMIN) {
      throw new IllegalArgumentException("Only admin can do this action");
    }
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      String username = ((UserDetails) authentication.getPrincipal()).getUsername();
      return userRepository.findByEmail(username)
          .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    return null;
  }
}
