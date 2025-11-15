package com.solarize.solarizeWebBackend.modules.project.state.status;


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

class AwaitingRetryTest {
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
    @DisplayName("Should set status to RETRYING and preserve existing previewStatus when transitioning from AWAITING_RETRY")
    void applyToClientAwaitingContactSetCorrectStatusButCannotUpdatePreviewStatus() {
        Project project = ProjectBuilder.builder()
                .withPreviewStatus(ProjectStatusEnum.PRE_BUDGET)
                .withStatus(ProjectStatusEnum.AWAITING_RETRY)
                .build();

        project.getStatus().getState().applyToRetrying(project);

        assertEquals(ProjectStatusEnum.RETRYING, project.getStatus());
        assertEquals(ProjectStatusEnum.PRE_BUDGET, project.getPreviewStatus());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.AWAITING_RETRY)
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("AWAITING_RETRY -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("AWAITING_RETRY -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("AWAITING_RETRY -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
                Arguments.of("AWAITING_RETRY -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingRetry(p)),
                Arguments.of("AWAITING_RETRY -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
                Arguments.of("AWAITING_RETRY -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("AWAITING_RETRY -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("AWAITING_RETRY -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
                Arguments.of("AWAITING_RETRY -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("AWAITING_RETRY -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
                Arguments.of("AWAITING_RETRY -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("AWAITING_RETRY -> NEGOCIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegociationFailed(p))
        );
    }


}