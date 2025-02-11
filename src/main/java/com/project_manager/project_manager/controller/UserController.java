package com.project_manager.project_manager.controller;

import com.project_manager.project_manager.dto.MeDto;
import com.project_manager.project_manager.dto.UserDetailsDto;
import com.project_manager.project_manager.dto.UserDto;
import com.project_manager.project_manager.dto.request.EditUserRequest;
import com.project_manager.project_manager.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

  private final UserService service;

  @GetMapping
  ResponseEntity<List<UserDto>> findAllUsers() {
    return ResponseEntity.ok(service.findAllUsers());
  }

  @GetMapping("/{id:\\d+}")
  ResponseEntity<UserDetailsDto> findUserWithProjects(@PathVariable Long id) {
    return ResponseEntity.ok(service.findUserWithProjects(id));
  }

  @GetMapping("/me")
  ResponseEntity<MeDto> findMe() {
    return ResponseEntity.ok(service.me());
  }

  @PatchMapping("/{id}")
  ResponseEntity<UserDto> updateUser(@RequestBody EditUserRequest request, @PathVariable Long id) {
    return ResponseEntity.ok(service.editUser(request, id));
  }

  @DeleteMapping("/{id}")
  ResponseEntity<String> deleteUser(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteUser(id));
  }
}
