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

class ClientAwaitingContactTest {
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
    @DisplayName("Should set status to AWAITING_RETRY and preserve previous status when transitioning from CLIENT_AWAITING_CONTACT")
    void applyToAwaitingRetrySetCorrectStatusAndPreviewStatus() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withFutureDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                .withRetry(retry)
                .build();

        project.getStatus().getState().applyToAwaitingRetry(project);

        assertEquals(ProjectStatusEnum.AWAITING_RETRY, project.getStatus());
        assertEquals(ProjectStatusEnum.CLIENT_AWAITING_CONTACT, project.getPreviewStatus());
    }


    @Test
    @DisplayName("Throws exception if retry date is in the past when moving to AWAITING_RETRY")
    void projectThrowsExceptionIfRetryIsWithPastDateInApplyToAwaitingRetry() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withPastDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                .withRetry(retry)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToAwaitingRetry(project));

    }


    @Test
    @DisplayName("Should set status to SCHEDULED_TECHNICAL_VISIT and preview status to CLIENT_AWAITING_CONTACT when a valid future technical visit exists")
    void applyToScheduledTechnicalVisitSetCorrectStatusAndPreviewStatus() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                .withSchedules(schedules)
                .build();

        project.getStatus().getState().applyToScheduledTechnicalVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.CLIENT_AWAITING_CONTACT, project.getPreviewStatus());
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("applyToScheduledTechnicalVisitThrowsExceptionWhenNoActiveFutureTechnicalVisitExistsProvider")
    void applyToScheduledTechnicalVisitThrowsExceptionWhenNoActiveFutureTechnicalVisitExists(
            String description,
            Project project
    ) {
        assertThrows(
                InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToScheduledTechnicalVisit(project)
        );
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("applyToAwaitingRetryThrowsExceptionWhenActiveScheduleInFutureExistsProvider")
    void applyToAwaitingRetryThrowsExceptionWhenActiveScheduleInFutureExists (
            String description,
            Project project
    ) {
        assertThrows(
                InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToAwaitingRetry(project)
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
                                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
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
                                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
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
                                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                                .build()
                )
        );
    }


    static Stream<Arguments> applyToAwaitingRetryThrowsExceptionWhenActiveScheduleInFutureExistsProvider() {
        return Stream.of(
                Arguments.of(
                        "Should throw when there is an active future schedule with status MARKED",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withFutureDate()
                                                .withStatus(ScheduleStatusEnum.MARKED)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                                .build()
                ),
                Arguments.of(
                        "Should throw when there is an active past schedule still IN_PROGRESS",
                        ProjectBuilder.builder()
                                .withSchedules(List.of(
                                        ScheduleBuilder.builder()
                                                .withFutureDate()
                                                .withStatus(ScheduleStatusEnum.IN_PROGRESS)
                                                .build()
                                ))
                                .withStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT)
                                .build()
                )
        );
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("NEW -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("CLIENT_AWAITING_CONTACT -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }


}