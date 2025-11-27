package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleBuilder;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScheduledTechnicalVisitTest {
    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("Should set status to RETRYING and previewStatus to SCHEDULED_TECHNICAL_VISIT")
    void applyToRetryingSetCorrectStatusAndAndPreview() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .build();

        project.getStatus().getStateHandler().applyToRetrying(project);

        assertEquals(ProjectStatusEnum.RETRYING, project.getStatus());
        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToRetrying should throws exception when a technical visit is scheduled")
    void applyToRetryingThrowsExceptionWhenTechnicalVisitScheduled() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withFutureDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .withSchedules(schedules)
                .build();



        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToRetrying(project));
    }

    @Test
    @DisplayName("Should set status to TECHNICAL_VISIT_COMPLETED and previewStatus to SCHEDULED_TECHNICAL_VISIT")
    void applyToTechnicalVisitCompletedSetCorrectStatusAndAndPreview() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withStatus(ScheduleStatusEnum.FINISHED)
                        .withFutureDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .withSchedules(schedules)
                .build();

        project.getStatus().getStateHandler().applyToTechnicalVisitCompleted(project);

        assertEquals(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED, project.getStatus());
        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToTechnicalVisitCompleted should throw exception when a FINISHED technical visit does not exist")
    void applyToTechnicalVisitCompletedThrowsExceptionWhenTechnicalVisitFinishedNotExists() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withFutureDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .withSchedules(schedules)
                .build();



        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToTechnicalVisitCompleted(project));
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        List<Schedule> schedule = List.of(
                ScheduleBuilder.builder()
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .build()
        );


        Project project = ProjectBuilder.builder()
                .withSchedules(schedule)
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledTechnicalVisit(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToFinalBudget(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingMaterials(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledInstallingVisit(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToCompleted(p)),
                Arguments.of("SCHEDULED_TECHNICAL_VISIT -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNegotiationFailed(p))
        );
    }

}