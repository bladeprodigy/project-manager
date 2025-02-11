package com.project_manager.project_manager.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectManagerDto(
    Long id,
    UserDto user,
    boolean active,
    String status,
    LocalDate projectJoinDate,
    LocalDate projectLeaveDate
) {

}
