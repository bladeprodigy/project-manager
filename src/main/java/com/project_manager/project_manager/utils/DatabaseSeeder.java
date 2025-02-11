package com.project_manager.project_manager.utils;

import com.project_manager.project_manager.model.User;
import com.project_manager.project_manager.model.repository.UserRepository;
import com.project_manager.project_manager.shared.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

  private final UserRepository userRepository;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    if (!userRepository.existsByRole(Role.ADMIN)) {
      User admin = new User();
      admin.setEmail("admin@admin.admin");
      admin.setPassword(new BCryptPasswordEncoder().encode("Admin123!"));
      admin.setName("admin");
      admin.setSurname("admin");
      admin.setRole(Role.ADMIN);
      userRepository.save(admin);
    }
  }

}

