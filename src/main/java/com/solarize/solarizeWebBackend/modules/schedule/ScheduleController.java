package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.schedule.dto.CreateScheduleDTO;
import com.solarize.solarizeWebBackend.modules.schedule.dto.ScheduleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PreAuthorize("hasAuthority('SCHEDULE_WRITE')")
    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody @Valid CreateScheduleDTO scheduleDTO) {
        Schedule schedule = ScheduleMapper.toEntity(scheduleDTO);
        Schedule createdSchedule = service.createSchedule(schedule);
        ScheduleResponseDTO dto = ScheduleMapper.toDto(createdSchedule);
        return ResponseEntity.status(201).body(dto);
    }

    @PreAuthorize("hasAuthority('SCHEDULE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody CreateScheduleDTO scheduleDTO){
        Schedule updatedSchedule = service.updateSchedule(id, ScheduleMapper.toEntity(scheduleDTO));

        ScheduleResponseDTO dto = ScheduleMapper.toDto(updatedSchedule);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('SCHEDULE_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        Schedule schedule = service.getScheduleById(id);
        ScheduleResponseDTO dto = ScheduleMapper.toDto(schedule);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('SCHEDULE_READ')")
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();
        if (schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ScheduleResponseDTO> dtos = ScheduleMapper.toDtoList(schedules);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('SCHEDULE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        service.deleteSchedule(id);
        return ResponseEntity.status(204).build();
    }

}
