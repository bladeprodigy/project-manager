package com.project_manager.project_manager.model.repository;

import com.project_manager.project_manager.model.User;
import com.project_manager.project_manager.shared.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByRole(Role role);
}

