package com.project_manager.project_manager.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectMemberDto(
    Long id,
    UserDto user,
    String projectRole,
    boolean active,
    String status,
    LocalDate projectJoinDate,
    LocalDate projectLeaveDate
) {

}
