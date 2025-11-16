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

class ScheduledInstallingVisitTest {
    // applyToRetrying
    // applyToInstalled

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
    @DisplayName("Should set status to RETRYING and previewStatus to SCHEDULED_INSTALLING_VISIT")
    void applyToRetryingSetCorrectStatusAndAndPreview() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT)
                .build();

        project.getStatus().getState().applyToRetrying(project);

        assertEquals(ProjectStatusEnum.RETRYING, project.getStatus());
        assertEquals(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToRetrying should throws exception when a installing visit is scheduled")
    void applyToRetryingThrowsExceptionWhenInstallingVisitScheduled() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withFutureDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT)
                .withSchedules(schedules)
                .build();



        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToRetrying(project));
    }


    @Test
    @DisplayName("Should set status to INSTALLED and previewStatus to SCHEDULED_INSTALLING_VISIT")
    void applyToInstalledCompletedSetCorrectStatusAndAndPreview() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .withStatus(ScheduleStatusEnum.FINISHED)
                        .withPastDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT)
                .withSchedules(schedules)
                .build();

        project.getStatus().getState().applyToInstalled(project);

        assertEquals(ProjectStatusEnum.INSTALLED, project.getStatus());
        assertEquals(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToInstalled Throws Exception When Finished Install Visit Not Exists")
    void applyToInstalledThrowsExceptionWhenFinishedInstallVisitNotExists() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .withStatus(ScheduleStatusEnum.IN_PROGRESS)
                        .withPastDate()
                        .build(),

                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withStatus(ScheduleStatusEnum.FINISHED)
                        .withPastDate()
                        .build(),

                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withPastDate()
                        .build(),

                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withPastDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT)
                .withSchedules(schedules)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToInstalled(project));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        List<Schedule> schedule = List.of(
                ScheduleBuilder.builder()
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withSchedules(schedule)
                .withStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingRetry(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("SCHEDULED_INSTALLING_VISIT -> NEGOCIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }
}