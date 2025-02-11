package com.project_manager.project_manager.mapper;

import com.project_manager.project_manager.dto.UserDto;
import com.project_manager.project_manager.model.User;

public class UserMapper {

  public static UserDto toDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .name(user.getName())
        .surname(user.getSurname())
        .build();
  }
}
