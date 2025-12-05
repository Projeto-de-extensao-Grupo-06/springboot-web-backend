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

class CompletedTest {
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
                .withStatus(ProjectStatusEnum.COMPLETED)
                .build();


        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("COMPLETED -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("COMPLETED -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("COMPLETED -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("COMPLETED -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("COMPLETED -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),
                Arguments.of("COMPLETED -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledTechnicalVisit(p)),
                Arguments.of("COMPLETED -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToTechnicalVisitCompleted(p)),
                Arguments.of("COMPLETED -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToFinalBudget(p)),
                Arguments.of("COMPLETED -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingMaterials(p)),
                Arguments.of("COMPLETED -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledInstallingVisit(p)),
                Arguments.of("COMPLETED -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),
                Arguments.of("COMPLETED -> COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToCompleted(p)),
                Arguments.of("COMPLETED -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNegotiationFailed(p))
        );
    }
}