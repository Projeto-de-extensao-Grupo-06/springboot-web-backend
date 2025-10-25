package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.schedule.dto.ScheduleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleMapper {

    public static ScheduleResponseDTO toDto(Schedule schedule) {
        if (schedule == null) return null;

            {

            return ScheduleResponseDTO.builder()
                    .id(schedule.getId())
                    .date(schedule.getDate())
                    .isActive(schedule.getIsActive())
                    .projectId(schedule.getProject().getId())
                    .type(schedule.getType().name())
                    .coworkerId(schedule.getCoworker().getId())
                    .status(schedule.getStatus().name())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .build();
        }
    }

    public static List<ScheduleResponseDTO> toDtoList(List<Schedule> schedules) {
        if (schedules == null) {
            return null;
        }
        return schedules.stream()
                .map(ScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

}
