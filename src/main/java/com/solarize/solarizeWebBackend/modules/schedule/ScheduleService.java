package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.schedule.dto.CreateScheduleDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final CoworkerRepository coworkerRepository;

    public Schedule createSchedule(CreateScheduleDTO dto) {
        Schedule schedule = ScheduleMapper.toEntity(dto);

        Coworker coworker = coworkerRepository.findById(dto.getCoworkerId())
                .orElseThrow(() -> new NotFoundException("Colaborador com ID " + dto.getCoworkerId() + " não encontrado."));

        schedule.setCoworker(coworker);
        return repository.save(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found "));
    }

    public Schedule updateSchedule(Long id, Schedule schedule){
        Schedule existingSchedule = getScheduleById(id);

        existingSchedule.setTitle(schedule.getTitle());
        existingSchedule.setDescription(schedule.getDescription());
        existingSchedule.setStartDate(schedule.getStartDate());
        existingSchedule.setEndDate(schedule.getEndDate());
        existingSchedule.setStatus(schedule.getStatus());
        existingSchedule.setType(schedule.getType());
        existingSchedule.setIsActive(schedule.getIsActive() != null ? schedule.getIsActive() : true);

        existingSchedule.setCoworker(schedule.getCoworker());
        return repository.save(existingSchedule);

    }

    public List<Schedule> getAllSchedules() {
        return repository.findAll();
    }

    public void deleteSchedule(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Schedule not found");
        }
        repository.deleteById(id);
    }

}
