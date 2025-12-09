package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.schedule.helper.ScheduleValidationsHelper;
import com.solarize.solarizeWebBackend.shared.event.ProjectDeletedEvent;
import com.solarize.solarizeWebBackend.shared.event.ScheduleCreatedEvent;
import com.solarize.solarizeWebBackend.shared.event.ScheduleInProgress;
import com.solarize.solarizeWebBackend.shared.event.ScheduleUpdatedEvent;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import com.solarize.solarizeWebBackend.shared.scheduler.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;
    private final CoworkerRepository coworkerRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ProjectRepository projectRepository;
    private final SchedulerService schedulerService;

    public Schedule createSchedule(Schedule schedule, Boolean force) {
        Coworker coworker;

        if(schedule.getProject().getId() == null && schedule.getType() != ScheduleTypeEnum.NOTE) {
            throw new BadRequestException("A project ID must be provided for this schedule type. " +
                    "Only NOTE type schedules can be created without an associated project.");
        }

        if(schedule.getStartDate().isAfter(schedule.getEndDate())) {
            throw new BadRequestException("Schedule cannot have a start date after en date");
        }

        if(schedule.getProject().getId() != null && !projectRepository.existsById(schedule.getProject().getId())) {
            throw new NotFoundException("Project does not exists.");
        }

        if(schedule.getCoworker().getId() == null) {
            coworker = coworkerRepository
                    .findByEmailAndIsActiveTrue(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new NotFoundException("Coworker not found."));
        } else {
            coworker = coworkerRepository.findById(schedule.getCoworker().getId())
                    .orElseThrow(() -> new NotFoundException("Coworker not found."));
        }


        schedule.setCoworker(coworker);

        if(!force) {
            List<Schedule> currentSchedules = repository.findScheduleByCoworkerAndIsActiveTrue(schedule.getCoworker());

            ScheduleValidationsHelper.scheduleValidation(currentSchedules, schedule)
                    .ifPresent(e -> {throw e;});
        }

        schedule.setStatus(ScheduleStatusEnum.MARKED);
        schedule.setNotificationAlertTime(schedule.getStartDate().minusDays(1));
        schedule.setIsActive(true);

        Schedule newSchedule = repository.save(schedule);

        eventPublisher.publishEvent(new ScheduleCreatedEvent(
                newSchedule.getId(),
                newSchedule.getStartDate(),
                newSchedule.getEndDate(),
                newSchedule.getStatus(),
                newSchedule.getType(),
                newSchedule.getProject().getId(),
                newSchedule.getNotificationAlertTime()
        ));

        return newSchedule;
    }


    public Schedule updateSchedule(Long id, Schedule schedule, Boolean force){
        Schedule existingSchedule = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found."));

        if(!force) {
            List<Schedule> currentSchedules = repository.findByCoworkerAndIsActiveTrueAndIdNot(existingSchedule.getCoworker(), id);

            if(existingSchedule.getStatus() == ScheduleStatusEnum.FINISHED) {
                throw new BadRequestException("Cannot update a finished schedule.");
            }

            ScheduleValidationsHelper.scheduleValidation(currentSchedules, schedule)
                    .ifPresent(e -> {throw e;});

        }


        existingSchedule.setTitle(
                Optional.ofNullable(schedule.getTitle())
                .orElse(existingSchedule.getTitle())
        );

        existingSchedule.setDescription(
                Optional.ofNullable(schedule.getDescription())
                        .orElse(existingSchedule.getDescription())
        );

        existingSchedule.setStartDate(
                Optional.ofNullable(schedule.getStartDate())
                        .orElse(existingSchedule.getStartDate())
        );

        existingSchedule.setEndDate(
                Optional.ofNullable(schedule.getEndDate())
                        .orElse(existingSchedule.getEndDate())
        );

        existingSchedule.setType(
                Optional.ofNullable(schedule.getType())
                        .orElse(existingSchedule.getType())
        );


        if(existingSchedule.getStartDate().isAfter(LocalDateTime.now())) {
            existingSchedule.setStatus(ScheduleStatusEnum.MARKED);
        }

        existingSchedule.setNotificationAlertTime(schedule.getStartDate().minusDays(1));

        eventPublisher.publishEvent(new ScheduleCreatedEvent(
                existingSchedule.getId(),
                existingSchedule.getStartDate(),
                existingSchedule.getEndDate(),
                existingSchedule.getStatus(),
                existingSchedule.getType(),
                existingSchedule.getProject().getId(),
                existingSchedule.getNotificationAlertTime()
        ));

        return repository.save(existingSchedule);
    }

    public List<Schedule> listScheduleMonth(Integer year, Integer month) {
        LocalDateTime start;
        LocalDateTime end;

        if(year == null || month == null) {
            LocalDateTime now = LocalDateTime.now();
            start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        } else {
            start = LocalDateTime.of(year, month, 1, 0, 0);
        }

        end = start.plusMonths(1);

        return repository.findAllByStartDateBetweenAndIsActiveTrue(start, end);
    }

    public Schedule findById(Long id) {
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Schedule does not exists."));
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

    @EventListener
    public void scheduleCreatedNotification(ScheduleCreatedEvent event) {
        schedulerService.scheduleTask("schedule-notification-" + event.scheduleId(), () -> {
            System.out.println("Notification not implemented");
        }, event.notificationDateTime());


        schedulerService.scheduleTask("visit-in-progress-" + event.scheduleId(), () -> {
            eventPublisher.publishEvent(new ScheduleInProgress(
                    event.scheduleId(),
                    event.startDate(),
                    event.endDate()
            ));
        }, event.startDate());
    }

    @EventListener
    public void scheduleUpdatedNotification(ScheduleUpdatedEvent event) {
        schedulerService.cancelTask("schedule-notification-" + event.scheduleId());
        schedulerService.cancelTask("visit-in-progress-" + event.scheduleId());


        schedulerService.scheduleTask("schedule-notification-" + event.scheduleId(), () -> {
            System.out.println("Notification not implemented");
        }, event.notificationDateTime());

        schedulerService.scheduleTask("visit-in-progress-" + event.scheduleId(), () -> {
            eventPublisher.publishEvent(new ScheduleInProgress(
                    event.scheduleId(),
                    event.startDate(),
                    event.endDate()
            ));
        }, event.startDate());
    }

    @EventListener
    public void visitInProgressUpdate(ScheduleInProgress event) {
        Schedule schedule = repository.findById(event.scheduleId())
                .orElseThrow(() -> new NotFoundException("Schedule does not exists"));

        schedule.setStatus(ScheduleStatusEnum.IN_PROGRESS);

        repository.save(schedule);
    }

    @EventListener
    public void deleteSchedulesOnProjectDelete(ProjectDeletedEvent event) {
        Project project = event.project();

        List<Schedule> schedules = repository.findAllByProject(project);

        schedules.forEach(s -> {
            schedulerService.cancelTask("visit-in-progress-" + s.getId());
            schedulerService.cancelTask("schedule-notification-" + s.getId());
        });

        repository.deleteAll(schedules);
    }
}
