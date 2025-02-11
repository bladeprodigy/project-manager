package com.project_manager.project_manager.model.repository;

import com.project_manager.project_manager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project, Long>,
    JpaSpecificationExecutor<Project> {

  boolean existsByName(String name);
}
