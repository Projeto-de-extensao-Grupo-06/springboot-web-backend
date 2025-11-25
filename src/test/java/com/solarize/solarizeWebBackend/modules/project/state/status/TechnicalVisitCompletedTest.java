package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.budget.Budget;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
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

class TechnicalVisitCompletedTest {
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
    @DisplayName("Should set status to FINAL_BUDGET and previewStatus to TECHNICAL_VISIT_COMPLETED")
    void applyToFinalBudgetSetCorrectStatusAndAndPreview() {
        Budget budget = new Budget();
        budget.setFinalBudget(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED)
                .withBudget(budget)
                .build();

        project.getStatus().getStateHandler().applyToFinalBudget(project);

        assertEquals(ProjectStatusEnum.FINAL_BUDGET, project.getStatus());
        assertEquals(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToScheduledInstallingVisit Throws Exception When Budget Not Exists")
    void applyToFinalBudgetThrowsExceptionWhenBudgetNotExists() {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.AWAITING_MATERIALS)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToFinalBudget(project));
    }

    @Test
    @DisplayName("applyToScheduledInstallingVisit Throws Exception When Final Budget Not Exists")
    void applyToFinalBudgetThrowsExceptionWhenFinalBudgetNotExists() {
        Budget budget = new Budget();
        budget.setFinalBudget(false);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.AWAITING_MATERIALS)
                .withBudget(budget)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToFinalBudget(project));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Budget budget = new Budget();
        budget.setFinalBudget(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED)
                .withBudget(budget)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("NEW -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("NEW -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("NEW -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("NEW -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("NEW -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),
                Arguments.of("NEW -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledTechnicalVisit(p)),
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