package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueue;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueueBuilder;
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

class PreBudgetTest {
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
    @DisplayName("Should set status to CLIENT_AWAITING_CONTACT and preview to PRE_BUDGET")
    void applyToClientAwaitingContactSetCorrectStatusAndPreviewStatus() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.PRE_BUDGET)
                .build();

        project.getStatus().getState().applyToClientAwaitingContact(project);

        assertEquals(ProjectStatusEnum.CLIENT_AWAITING_CONTACT, project.getStatus());
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("PRE_BUDGET → AWAITING_RETRY sets correct status and preserves previous status")
    void applyToAwaitingRetrySetCorrectStatusAndPreviewStatus() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withFutureDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.PRE_BUDGET)
                .withRetry(retry)
                .build();

        project.getStatus().getState().applyToAwaitingRetry(project);

        assertEquals(ProjectStatusEnum.AWAITING_RETRY, project.getStatus());
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getPreviewStatus());
    }

    @Test
    @DisplayName("Throws exception if retry date is in the past when moving to AWAITING_RETRY")
    void projectThrowsExceptionIfRetryIsWithPastDateInApplyToAwaitingRetry() {
        RetryQueue retry = RetryQueueBuilder.builder()
                .withPastDate()
                .build();

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.PRE_BUDGET)
                .withRetry(retry)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getState().applyToAwaitingRetry(project));

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.PRE_BUDGET)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("PRE_BUDGET -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("PRE_BUDGET -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("PRE_BUDGET -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),
                Arguments.of("PRE_BUDGET -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
                Arguments.of("PRE_BUDGET -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("PRE_BUDGET -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("PRE_BUDGET -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
                Arguments.of("PRE_BUDGET -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("PRE_BUDGET -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
                Arguments.of("PRE_BUDGET -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("PRE_BUDGET -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }
}