package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
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

        project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.NEW, project.getPreviewStatus());
    }


    @Test
    @DisplayName("Should set status to FINAL_BUDGET and previewStatus to NEW when a valid final budge exists")
    void applyToFinalBudgetSetStatusFinalBudgetAndPreviewStatusNew() {
        Budget budget = new Budget();
        budget.setFinalBudget(true);

        Project project = ProjectBuilder.builder()
                .withBudget(budget)
                .withStatus(ProjectStatusEnum.NEW)
                .build();

        project.getStatus().getStateHandler().applyToFinalBudget(project);

        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getStatus());
        assertEquals(ProjectStatusEnum.NEW, project.getPreviewStatus());
    }

    @Test
    @DisplayName("Should set status to PRE_BUDGET and previewStatus to NEW when a valid final budge exists")
    void applyToPreBudgetSetStatusPreBudgetAndPreviewStatusNew() {
        Budget budget = new Budget();
        budget.setFinalBudget(false);

        Project project = ProjectBuilder.builder()
                .withBudget(budget)
                .withStatus(ProjectStatusEnum.NEW)
                .build();

        project.getStatus().getStateHandler().applyToPreBudget(project);

        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getStatus());
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
                () -> project.getStatus().getStateHandler().applyToScheduledTechnicalVisit(project)
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
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),

                Arguments.of("NEW -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),

                Arguments.of("NEW -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),

                Arguments.of("NEW -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),

                Arguments.of("NEW -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToTechnicalVisitCompleted(p)),

                Arguments.of("NEW -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingMaterials(p)),

                Arguments.of("NEW -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledInstallingVisit(p)),

                Arguments.of("NEW -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),

                Arguments.of("NEW -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToCompleted(p)),

                Arguments.of("NEW -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNegotiationFailed(p))
        );
    }


}
