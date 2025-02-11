package com.project_manager.project_manager.utils;

import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.shared.ProjectStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {

  public static Specification<Project> hasName(String name) {
    return (root, query, builder) ->
        builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  public static Specification<Project> hasClientName(String clientName) {
    return (root, query, builder) ->
        builder.like(builder.lower(root.get("clientName")), "%" + clientName.toLowerCase() + "%");
  }

  public static Specification<Project> hasStatus(ProjectStatus status) {
    return (root, query, builder) ->
        builder.equal(root.get("status"), status);
  }
}

