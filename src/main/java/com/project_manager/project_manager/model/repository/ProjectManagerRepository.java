package com.project_manager.project_manager.model.repository;

import com.project_manager.project_manager.model.ProjectManager;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {

  Optional<ProjectManager> findByProjectIdAndUserId(Long projectId, Long userId);

  boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}
