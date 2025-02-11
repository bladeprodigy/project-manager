package com.project_manager.project_manager.model;

import com.project_manager.project_manager.shared.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private String clientName;
  private LocalDate creationDate;
  @Enumerated(EnumType.STRING)
  private ProjectStatus status;
  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
  private List<ProjectManager> projectManagers;
  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
  private List<ProjectMember> projectMembers;
  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
  private List<ProjectReport> projectReports;
}
