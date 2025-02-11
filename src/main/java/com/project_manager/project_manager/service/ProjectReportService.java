package com.project_manager.project_manager.service;


import com.project_manager.project_manager.dto.ProjectDetailsDto;
import com.project_manager.project_manager.dto.request.CreateProjectReportRequest;
import com.project_manager.project_manager.mapper.ProjectMapper;
import com.project_manager.project_manager.model.Project;
import com.project_manager.project_manager.model.ProjectManager;
import com.project_manager.project_manager.model.ProjectReport;
import com.project_manager.project_manager.model.repository.ProjectReportRepository;
import com.project_manager.project_manager.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectReportService {

  private final ProjectReportRepository repository;
  private final ProjectService projectService;
  private final SecurityUtils securityUtils;

  public ProjectDetailsDto createProjectReport(CreateProjectReportRequest request, Long id) {
    Project project = projectService.findProjectById(id);

    if (securityUtils.getCurrentUser() != project.getProjectManagers().stream()
        .filter(ProjectManager::getActive).map(ProjectManager::getUser).toList().getFirst()) {
      throw new EntityNotFoundException("Only project manager can create reports");
    }

    ProjectReport report = ProjectReport.builder()
        .project(project)
        .title(request.title())
        .content(request.content())
        .reportDate(LocalDate.now())
        .build();

    repository.save(report);
    return ProjectMapper.toDetailsDto(projectService.findProjectById(id));
  }
}
