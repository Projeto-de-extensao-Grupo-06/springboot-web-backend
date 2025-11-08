package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.schedule.dto.CreateScheduleDTO;
import com.solarize.solarizeWebBackend.modules.schedule.dto.ScheduleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleMapper {

    public static ScheduleResponseDTO toDto(Schedule schedule) {
        if (schedule == null) return null;

        return ScheduleResponseDTO.builder()
                    .id(schedule.getId())
                    .startDate(schedule.getStartDate())
                    .endDate(schedule.getEndDate())
                    .isActive(schedule.getIsActive())
                    .projectId(schedule.getProject() != null ? schedule.getProject().getId() : null)
                    .type(schedule.getType().name())
                    .coworkerId(schedule.getCoworker().getId())
                    .status(schedule.getStatus().name())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .build();

    }

    public static List<ScheduleResponseDTO> toDtoList(List<Schedule> schedules) {
        if (schedules == null) return null;

        return schedules.stream()
                .map(ScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Schedule toEntity(CreateScheduleDTO dto){
        if (dto == null)return null;

        Coworker coworker = new Coworker();
        coworker.setId(dto.getCoworkerId());

        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setDescription(dto.getDescription());
        schedule.setStartDate(dto.getStartDate());
        schedule.setEndDate(dto.getEndDate());
        schedule.setType(dto.getType());
        schedule.setStatus(dto.getStatus());
        schedule.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        schedule.setCoworker(coworker);
        schedule.setNotificationAlertTime(dto.getNotificationAlertTime());

        return schedule;


    }


}
