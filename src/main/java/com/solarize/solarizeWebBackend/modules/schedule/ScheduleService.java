package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetRepository;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.schedule.dto.CreateScheduleDTO;
import com.solarize.solarizeWebBackend.shared.event.ScheduleCreatedEvent;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;
    private final CoworkerRepository coworkerRepository;
    private ApplicationEventPublisher eventPublisher;

    public Schedule createSchedule(Schedule schedule, Boolean force) {
        Coworker coworker;

        if(schedule.getCoworker().getId() == null) {
            coworker = coworkerRepository
                    .findByEmailAndIsActiveTrue(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new NotFoundException("Coworker not found."));
        } else {
            coworker = coworkerRepository.findById(schedule.getCoworker().getId())
                    .orElseThrow(() -> new NotFoundException("Coworker not found"));
        }

        schedule.setCoworker(coworker);

        if(!force) {
            List<Schedule> currentSchedules = repository.findScheduleByCoworkerAndIsActiveTrue(schedule.getCoworker());

            boolean technicalVisit = currentSchedules.stream()
                    .anyMatch(s -> s.getType() == ScheduleTypeEnum.TECHNICAL_VISIT);

            boolean installVisit = currentSchedules.stream()
                    .anyMatch(s -> s.getType() == ScheduleTypeEnum.INSTALL_VISIT);

            boolean hasOverlap = currentSchedules.stream().anyMatch(
                    existingSchedule -> {
                        LocalDateTime start1 = existingSchedule.getStartDate();
                        LocalDateTime end1 = existingSchedule.getEndDate();
                        LocalDateTime start2 = schedule.getStartDate();
                        LocalDateTime end2 = schedule.getEndDate();

                        return start1.isBefore(end2) && end1.isAfter(start2);
                    });

            if (hasOverlap) {
                throw new ConflictException("The selected date and time range overlaps with an existing schedule for this coworker. " +
                        "Use force to do this.");
            }

            if(schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT && installVisit) {
                throw new ConflictException("A past or future installation visit already exists for this project. " +
                        "This operation does not make sense. Resubmit the request with the force flag if you really want to perform this operation.");
            }

            if(schedule.getType() == ScheduleTypeEnum.INSTALL_VISIT && !technicalVisit) {
                throw new ConflictException("An installation visit is being created without a technical visit ever having been created. " +
                        "This operation does not make sense. If you really want to do this, use the force flag.");
            }
        }

        if(schedule.getProject().getId() == null && schedule.getType() != ScheduleTypeEnum.NOTE) {
            throw new BadRequestException("A project ID must be provided for this schedule type. " +
                    "Only NOTE type schedules can be created without an associated project.");
        }

        schedule.setStatus(ScheduleStatusEnum.MARKED);

        Schedule newSchedule = repository.save(schedule);

        eventPublisher.publishEvent(new ScheduleCreatedEvent(
                newSchedule.getStartDate(),
                newSchedule.getEndDate(),
                newSchedule.getStatus(),
                newSchedule.getType(),
                schedule.getProject().getId()
        ));

        return newSchedule;
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


    public LocalDateTime findNextScheduleDateByProjectId(Long projectId) {
        return repository.findNextScheduleByProjectId(projectId);
    }

}
