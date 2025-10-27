package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.schedule.dto.ScheduleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody Schedule schedule) {
        Schedule createdSchedule = service.createSchedule(schedule);
        ScheduleResponseDTO dto = ScheduleMapper.toDto(createdSchedule);
        return ResponseEntity.status(201).body(dto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        Schedule schedule = service.getScheduleById(id);
        ScheduleResponseDTO dto = ScheduleMapper.toDto(schedule);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();
        if (schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ScheduleResponseDTO> dtos = ScheduleMapper.toDtoList(schedules);
        return ResponseEntity.ok(dtos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        service.deleteSchedule(id);
        return ResponseEntity.status(204).build();
    }

}
