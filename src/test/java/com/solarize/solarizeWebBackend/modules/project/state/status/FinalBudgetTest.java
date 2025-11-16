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

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FinalBudgetTest {
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
    @DisplayName("Should set status to AWAITING_RETRY and previewStatus to FINAL_BUDGET")
    void applyToAwaitingRetrySetCorrectStatusAndAndPreview() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withFutureDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.FINAL_BUDGET)
                .withRetry(retry)
                .build();

        project.getStatus().getState().applyToAwaitingRetry(project);

        assertEquals(ProjectStatusEnum.AWAITING_RETRY, project.getStatus());
        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToAwaitingRetry should throws exception when a retry not exists in queue")
    void applyToAwaitingRetryThrowsExceptionWhenRetryNotInQueue() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToAwaitingRetry(project));
    }

    @Test
    @DisplayName("Should set status to AWAITING_MATERIALS and previewStatus to FINAL_BUDGET")
    void applyToAwaitingMaterialsSetCorrectStatusAndAndPreview() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withFutureDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.FINAL_BUDGET)
                .withRetry(retry)
                .build();

        project.getStatus().getState().applyToAwaitingMaterials(project);

        assertEquals(ProjectStatusEnum.AWAITING_MATERIALS, project.getStatus());
        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getPreviewStatus());
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.FINAL_BUDGET)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }


    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("FINAL_BUDGET -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("FINAL_BUDGET -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("FINAL_BUDGET -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
                Arguments.of("FINAL_BUDGET -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),
                Arguments.of("FINAL_BUDGET -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
                Arguments.of("FINAL_BUDGET -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("FINAL_BUDGET -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("FINAL_BUDGET -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("FINAL_BUDGET -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
                Arguments.of("FINAL_BUDGET -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("FINAL_BUDGET -> NEGOCIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }
}