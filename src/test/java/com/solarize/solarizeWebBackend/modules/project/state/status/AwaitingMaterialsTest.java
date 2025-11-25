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

class AwaitingMaterialsTest {
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
    @DisplayName("Should set status to SCHEDULED_INSTALLING_VISIT and previewStatus to AWAITING_MATERIALS")
    void applyToScheduledInstallingVisitSetCorrectStatusAndAndPreview() {
        List<Schedule> schedules = List.of(
                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.INSTALL_VISIT)
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withFutureDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.AWAITING_MATERIALS)
                .withSchedules(schedules)
                .build();

        project.getStatus().getStateHandler().applyToScheduledInstallingVisit(project);

        assertEquals(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT, project.getStatus());
        assertEquals(ProjectStatusEnum.AWAITING_MATERIALS, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToScheduledInstallingVisit Throws Exception When MARKED Install Visit Not Exists")
    void applyToScheduledInstallingVisitThrowsExceptionWhenFinishedInstallVisitNotExists() {
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
                        .withStatus(ScheduleStatusEnum.FINISHED)
                        .withPastDate()
                        .build(),

                ScheduleBuilder.builder()
                        .withType(ScheduleTypeEnum.TECHNICAL_VISIT)
                        .withStatus(ScheduleStatusEnum.MARKED)
                        .withPastDate()
                        .build()
        );

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.AWAITING_MATERIALS)
                .withSchedules(schedules)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToScheduledInstallingVisit(project));
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
                .withStatus(ProjectStatusEnum.AWAITING_MATERIALS)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }


    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("AWAITING_MATERIALS -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("AWAITING_MATERIALS -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("AWAITING_MATERIALS -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("AWAITING_MATERIALS -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("AWAITING_MATERIALS -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),
                Arguments.of("AWAITING_MATERIALS -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledTechnicalVisit(p)),
                Arguments.of("AWAITING_MATERIALS -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToTechnicalVisitCompleted(p)),
                Arguments.of("AWAITING_MATERIALS -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToFinalBudget(p)),
                Arguments.of("AWAITING_MATERIALS -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingMaterials(p)),
                Arguments.of("AWAITING_MATERIALS -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),
                Arguments.of("AWAITING_MATERIALS -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToCompleted(p)),
                Arguments.of("AWAITING_MATERIALS -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNegotiationFailed(p))
        );
    }
}