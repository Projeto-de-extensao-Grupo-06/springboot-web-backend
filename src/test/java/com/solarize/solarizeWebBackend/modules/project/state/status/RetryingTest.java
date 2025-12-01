package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueue;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueueBuilder;
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

class RetryingTest {
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
    @DisplayName("RETRYING → AWAITING_RETRY sets correct status and preserves previous status")
    void applyToAwaitingRetrySetCorrectStatusAndPreviewStatus() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withFutureDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withPreviewStatus(ProjectStatusEnum.PRE_BUDGET)
                .withRetry(retry)
                .build();

        project.getStatus().getStateHandler().applyToAwaitingRetry(project);

        assertEquals(ProjectStatusEnum.AWAITING_RETRY, project.getStatus());
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("RETRYING → SCHEDULED_TECHNICAL_VISIT sets correct status and preserves previous status")
    void applyToScheduledTechnicalVisitSetCorrectStatusAndPreviewStatus() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .build()
        );
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withPreviewStatus(ProjectStatusEnum.PRE_BUDGET)
                .withSchedules(schedules)
                .build();

        project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("AWAITING_MATERIALS → SCHEDULED_TECHNICAL_VISIT sets correct status and preserves previous status")
    void applyToAwaitingMaterialsVisitSetCorrectStatusAndPreviewStatus() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withPreviewStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED)
                .build();

        project.getStatus().getStateHandler().applyToAwaitingMaterials(project);

        assertEquals(ProjectStatusEnum.AWAITING_MATERIALS, project.getStatus());
        assertEquals(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED, project.getPreviewStatus());
    }

    @Test
    @DisplayName("RETRYING → SCHEDULED_INSTALLING_VISIT sets correct status and preserves previous status")
    void applyToScheduledInstallingVisitSetCorrectStatusAndPreviewStatus() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .build()
        );
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withPreviewStatus(ProjectStatusEnum.FINAL_BUDGET)
                .withSchedules(schedules)
                .build();

        project.getStatus().getStateHandler().applyToScheduledInstallingVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("Throws exception if retry date is in the past when moving to AWAITING_RETRY")
    void projectThrowsExceptionIfRetryIsWithPastDateInApplyToAwaitingRetry() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withPastDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withRetry(retry)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToAwaitingRetry(project));

    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("applyToScheduledTechnicalVisitThrowsExceptionWhenNoActiveFutureTechnicalVisitExistsProvider")
    void applyToScheduledTechnicalVisitThrowsExceptionWhenNoActiveFutureTechnicalVisitExists(
            String description,
            Project project
    ) {
        assertThrows(
                InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project)
        );
    }

    @Test
    @DisplayName("Throws exception if retry exists when moving to SCHEDULED_TECHNICAL_VISIT")
    void projectThrowsExceptionIfRetryExistsOnApplyToScheduledTechnicalVisit() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withRetry(retry)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project));

    }

    @Test
    @DisplayName("Throws exception if retry exists when moving to NEGOTIATION_FAILED")
    void projectThrowsExceptionIfRetryExistsOnApplyToNegotiationFailed() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .withRetry(retry)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToNegotiationFailed(project));

    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("applyToScheduledInstallingVisitThrowsExceptionWhenNoActiveFutureInstallVisitExistsProvider")
    void applyToScheduledInstallingVisitThrowsExceptionWhenNoActiveFutureInstallVisitExists (
            String description,
            Project project
    ) {
        assertThrows(
                InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToScheduledInstallingVisit(project)
        );
    }

    static Stream<Arguments> applyToScheduledTechnicalVisitThrowsExceptionWhenNoActiveFutureTechnicalVisitExistsProvider() {
        return Stream.of(
                Arguments.of(
                        "Should throw when the schedule is inactive, even if it is marked and in the future",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.MARKED)
                                                .withInactiveSchedule()
                                                .withFutureDate()
                                                .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                ),
                Arguments.of(
                        "Should throw when the schedule is finished and in the past, even if it is a technical visit",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.FINISHED)
                                                .withPastDate()
                                                .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                ),
                Arguments.of(
                        "Should throw when the schedule is a future active visit but not a technical visit",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.MARKED)
                                                .withFutureDate()
                                                .withType(ScheduleTypeEnum.INSTALL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                )
        );
    }

    static Stream<Arguments> applyToScheduledInstallingVisitThrowsExceptionWhenNoActiveFutureInstallVisitExistsProvider () {
        return Stream.of(
                Arguments.of(
                        "Should throw when the schedule is inactive, even if it is marked and in the future",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.MARKED)
                                                .withInactiveSchedule()
                                                .withFutureDate()
                                                .withType(ScheduleTypeEnum.INSTALL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                ),
                Arguments.of(
                        "Should throw when the schedule is finished and in the past, even if it is a installing visit",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.FINISHED)
                                                .withPastDate()
                                                .withType(ScheduleTypeEnum.INSTALL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                ),
                Arguments.of(
                        "Should throw when the schedule is a future active visit but not a installing visit",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withStatus(ScheduleStatusEnum.MARKED)
                                                .withFutureDate()
                                                .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.RETRYING)
                                .build()
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.RETRYING)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("RETRYING -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("RETRYING -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("RETRYING -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("RETRYING -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("RETRYING -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),
                Arguments.of("RETRYING -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToTechnicalVisitCompleted(p)),
                Arguments.of("RETRYING -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToFinalBudget(p)),
                Arguments.of("RETRYING -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),
                Arguments.of("RETRYING -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToCompleted(p))
        );
    }
}