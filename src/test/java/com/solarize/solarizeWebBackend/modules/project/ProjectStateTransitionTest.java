package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Project State Machine Transition Tests")
class ProjectStateTransitionTest {

    @Test
    @DisplayName("Should transition from NEW to PRE_BUDGET")
    void shouldTransitionNewToPreBudget() {
        Project project = ProjectBuilder.builder().withStatus(ProjectStatusEnum.NEW).build();
        
        project.getStatus().getStateHandler().applyToPreBudget(project);
        
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getStatus());
        assertEquals(ProjectStatusEnum.NEW, project.getPreviewStatus());
    }

    @Test
    @DisplayName("Should throw exception when transitioning from NEW to COMPLETED (Invalid)")
    void shouldThrowExceptionNewToCompleted() {
        Project project = ProjectBuilder.builder().withStatus(ProjectStatusEnum.NEW).build();
        
        assertThrows(InvalidStateTransitionException.class, () -> 
            project.getStatus().getStateHandler().applyToCompleted(project));
    }

    @Test
    @DisplayName("Should transition from NEW to SCHEDULED_TECHNICAL_VISIT when there is a future schedule")
    void shouldTransitionNewToScheduledTechnicalVisit() {
        Schedule schedule = new Schedule();
        schedule.setType(ScheduleTypeEnum.TECHNICAL_VISIT);
        schedule.setStartDate(LocalDateTime.now().plusDays(1));
        schedule.setStatus(ScheduleStatusEnum.MARKED);
        schedule.setIsActive(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.NEW)
                .withSchedules(List.of(schedule))
                .build();

        project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getStatus());
    }

    @Test
    @DisplayName("Should throw exception from NEW to SCHEDULED_TECHNICAL_VISIT when no future schedule exists")
    void shouldThrowExceptionNewToScheduledTechnicalVisitNoSchedule() {
        Project project = ProjectBuilder.builder().withStatus(ProjectStatusEnum.NEW).build();

        assertThrows(InvalidStateTransitionException.class, () ->
                project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project));
    }

    @Test
    @DisplayName("Should transition from SCHEDULED_TECHNICAL_VISIT to TECHNICAL_VISIT_COMPLETED when schedule is finished")
    void shouldTransitionToTechnicalVisitCompleted() {
        Schedule schedule = new Schedule();
        schedule.setType(ScheduleTypeEnum.TECHNICAL_VISIT);
        schedule.setStatus(ScheduleStatusEnum.FINISHED);
        schedule.setIsActive(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .withSchedules(List.of(schedule))
                .build();

        project.getStatus().getStateHandler().applyToTechnicalVisitCompleted(project);

        assertEquals(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED, project.getStatus());
    }

    @Test
    @DisplayName("Should transition from TECHNICAL_VISIT_COMPLETED to FINAL_BUDGET when budget is final")
    void shouldTransitionToFinalBudget() {
        Budget budget = new Budget();
        budget.setFinalBudget(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED)
                .withBudget(budget)
                .build();

        project.getStatus().getStateHandler().applyToFinalBudget(project);

        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getStatus());
    }

    @Test
    @DisplayName("Should throw exception from TECHNICAL_VISIT_COMPLETED to FINAL_BUDGET when budget is not final")
    void shouldThrowExceptionToFinalBudgetNotFinal() {
        Budget budget = new Budget();
        budget.setFinalBudget(false);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED)
                .withBudget(budget)
                .build();

        assertThrows(InvalidStateTransitionException.class, () ->
                project.getStatus().getStateHandler().applyToFinalBudget(project));
    }
}
