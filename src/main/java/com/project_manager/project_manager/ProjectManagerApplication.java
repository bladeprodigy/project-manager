package com.project_manager.project_manager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ProjectManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectManagerApplication.class, args);
  }

}
