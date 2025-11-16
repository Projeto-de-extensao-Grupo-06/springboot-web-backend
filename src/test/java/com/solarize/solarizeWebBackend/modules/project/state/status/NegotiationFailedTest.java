package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NegotiationFailedTest {
    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTransitionsProvider")
    void invalidTransitionsThrowsException(String description, Consumer<Project> transition) {
        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.NEGOTIATION_FAILED)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("NEGOTIATION_FAILED -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNew(p)),
                Arguments.of("NEGOTIATION_FAILED -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToPreBudget(p)),
                Arguments.of("NEGOTIATION_FAILED -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToClientAwaitingContact(p)),
                Arguments.of("NEGOTIATION_FAILED -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingRetry(p)),
                Arguments.of("NEGOTIATION_FAILED -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToRetrying(p)),
                Arguments.of("NEGOTIATION_FAILED -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledTechnicalVisit(p)),
                Arguments.of("NEGOTIATION_FAILED -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToTechnicalVisitCompleted(p)),
                Arguments.of("NEGOTIATION_FAILED -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToFinalBudget(p)),
                Arguments.of("NEGOTIATION_FAILED -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToAwaitingMaterials(p)),
                Arguments.of("NEGOTIATION_FAILED -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToScheduledInstallingVisit(p)),
                Arguments.of("NEGOTIATION_FAILED -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToInstalled(p)),
                Arguments.of("NEGOTIATION_FAILED -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToCompleted(p)),
                Arguments.of("NEGOTIATION_FAILED -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getState().applyToNegotiationFailed(p))
        );
    }
}