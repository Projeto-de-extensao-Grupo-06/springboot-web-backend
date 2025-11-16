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

class NewTest {
//     static Stream<Arguments> invalidTransitionsProvider() {
//        return Stream.of(
//                Arguments.of("NEW -> NEW",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
//                Arguments.of("NEW -> PRE_BUDGET",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
//                Arguments.of("NEW -> CLIENT_AWAITING_CONTACT",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
//                Arguments.of("NEW -> AWAITING_RETRY",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingRetry(p)),
//                Arguments.of("NEW -> RETRYING",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),
//                Arguments.of("NEW -> SCHEDULED_TECHNICAL_VISIT",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
//                Arguments.of("NEW -> TECHNICAL_VISIT_COMPLETED",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
//                Arguments.of("NEW -> FINAL_BUDGET",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
//                Arguments.of("NEW -> AWAITING_MATERIALS",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
//                Arguments.of("NEW -> SCHEDULED_INSTALLING_VISIT",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
//                Arguments.of("NEW -> INSTALLED",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
//                Arguments.of("NEW -> COMPLETED",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
//                Arguments.of("NEW -> NEGOTIATION_FAILED",
//                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
//        );
//    }



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
    @DisplayName("Should set status to SCHEDULED_TECHNICAL_VISIT and previewStatus to NEW when a valid future technical visit is scheduled")
    void applyToScheduledTechnicalVisitSetStatusScheduledTechnicalVisitAndPreviewStatusNew() {
        List<Schedule> schedule = List.of(
                ScheduleBuilder.builder()
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withFutureDate()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .build()
        );


        Project project = ProjectBuilder.builder()
                .withSchedules(schedule)
                .withStatus(ProjectStatusEnum.NEW)
                .build();

        project.getStatus().getState().applyToScheduledTechnicalVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.NEW, project.getPreviewStatus());
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
                .withStatus(ProjectStatusEnum.NEW)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
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
                                .withStatus(ProjectStatusEnum.NEW)
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
                                .withStatus(ProjectStatusEnum.NEW)
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
                                .withStatus(ProjectStatusEnum.NEW)
                                .build()
                )
        );
    }


    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("NEW -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),

                Arguments.of("NEW -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),

                Arguments.of("NEW -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),

                Arguments.of("NEW -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingRetry(p)),

                Arguments.of("NEW -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),

                Arguments.of("NEW -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),

                Arguments.of("NEW -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),

                Arguments.of("NEW -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),

                Arguments.of("NEW -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),

                Arguments.of("NEW -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),

                Arguments.of("NEW -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),

                Arguments.of("NEW -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }


}
