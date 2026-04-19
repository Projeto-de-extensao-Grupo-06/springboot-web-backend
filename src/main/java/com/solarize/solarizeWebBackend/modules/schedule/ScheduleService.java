package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.schedule.dto.CancelScheduleDTO;
import com.solarize.solarizeWebBackend.modules.schedule.helper.ScheduleValidationsHelper;
import com.solarize.solarizeWebBackend.shared.event.ScheduleCreatedEvent;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import com.solarize.solarizeWebBackend.shared.rabbit.RabbitPropertiesConfiguration;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;
    private final CoworkerRepository coworkerRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ProjectRepository projectRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitPropertiesConfiguration properties;

    @Transactional
    public Schedule createSchedule(Schedule schedule, Boolean force) {
        if((schedule.getProject() == null || schedule.getProject().getId() == null) && schedule.getType() != ScheduleTypeEnum.NOTE) {
            throw new BadRequestException("A project ID must be provided for this schedule type. " +
                    "Only NOTE type schedules can be created without an associated project.");
        }

        if(schedule.getEndDate() != null && schedule.getStartDate().isAfter(schedule.getEndDate())) {
            throw new BadRequestException("Schedule cannot have a start date after end date");
        }

        if(schedule.getProject() != null && schedule.getProject().getId() != null) {
            Project managedProject = projectRepository.findById(schedule.getProject().getId())
                    .orElseThrow(() -> new NotFoundException("Project does not exist."));

            schedule.setProject(managedProject);
        }

        Coworker coworker = coworkerRepository
                .findByEmailAndIsActiveTrue(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("Coworker not found."));
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
                newSchedule.getProject() != null ? newSchedule.getProject().getId() : null,
                newSchedule.getNotificationAlertTime()
        ));

        String exchangeName = properties.exchange().name();
        String routingKey = properties.createQueue().name();

        rabbitTemplate.convertAndSend(exchangeName, routingKey, ScheduleMapper.toCreateScheduleMessage(newSchedule));
        System.out.println("Mensagem enviada para o RabbitMQ");

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
                existingSchedule.getProject() != null ? existingSchedule.getProject().getId() : null,
                existingSchedule.getNotificationAlertTime()
        ));

        String exchangeName = properties.exchange().name();
        String routingKey = properties.updateQueue().name();

        rabbitTemplate.convertAndSend(exchangeName, routingKey, ScheduleMapper.toCreateScheduleMessage(existingSchedule));

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

        String exchangeName = properties.exchange().name();
        String routingKey = properties.cancelQueue().name();

        rabbitTemplate.convertAndSend(exchangeName, routingKey, CancelScheduleDTO.builder().scheduleId(id).build());

        repository.deleteById(id);
    }

    public LocalDateTime findNextScheduleDateByProjectId(Long projectId) {
        return repository.findNextScheduleByProjectId(projectId);
    }
}
