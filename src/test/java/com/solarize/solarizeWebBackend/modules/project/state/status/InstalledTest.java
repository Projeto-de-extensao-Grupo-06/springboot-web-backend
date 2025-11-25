package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectBuilder;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFile;
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

class InstalledTest {
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
        ProjectFile file = new ProjectFile();
        file.setHomologationDoc(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.INSTALLED)
                .withFiles(List.of(file))
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> transition.accept(project));
    }

    @Test
    @DisplayName("Should set status to COMPLETED and previewStatus to INSTALLED")
    void applyToCompletedSetCorrectStatusAndAndPreview() {
        ProjectFile file = new ProjectFile();
        file.setHomologationDoc(true);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.INSTALLED)
                .withFiles(List.of(file))
                .build();

        project.getStatus().getStateHandler().applyToCompleted(project);

        assertEquals(ProjectStatusEnum.COMPLETED, project.getStatus());
        assertEquals(ProjectStatusEnum.INSTALLED, project.getPreviewStatus());
    }

    @Test
    @DisplayName("applyToCompleted Throws Exception When Homologation Doc Not Exists")
    void applyToInstalledThrowsExceptionWhenFinishedInstallVisitNotExists() {
        ProjectFile file = new ProjectFile();
        file.setHomologationDoc(false);

        Project project = ProjectBuilder.builder()
                .withStatus(ProjectStatusEnum.INSTALLED)
                .withFiles(List.of(file))
                .build();

        assertThrows(InvalidStateTransitionException.class,
                () -> project.getStatus().getStateHandler().applyToCompleted(project));
    }

    static Stream<Arguments> invalidTransitionsProvider() {
        return Stream.of(
                Arguments.of("INSTALLED -> NEW",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNew(p)),
                Arguments.of("INSTALLED -> PRE_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToPreBudget(p)),
                Arguments.of("INSTALLED -> CLIENT_AWAITING_CONTACT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToClientAwaitingContact(p)),
                Arguments.of("INSTALLED -> AWAITING_RETRY",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingRetry(p)),
                Arguments.of("INSTALLED -> RETRYING",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToRetrying(p)),
                Arguments.of("INSTALLED -> SCHEDULED_TECHNICAL_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledTechnicalVisit(p)),
                Arguments.of("INSTALLED -> TECHNICAL_VISIT_COMPLETED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToTechnicalVisitCompleted(p)),
                Arguments.of("INSTALLED -> FINAL_BUDGET",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToFinalBudget(p)),
                Arguments.of("INSTALLED -> AWAITING_MATERIALS",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToAwaitingMaterials(p)),
                Arguments.of("INSTALLED -> SCHEDULED_INSTALLING_VISIT",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToScheduledInstallingVisit(p)),
                Arguments.of("INSTALLED -> INSTALLED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToInstalled(p)),
                Arguments.of("INSTALLED -> NEGOTIATION_FAILED",
                        (Consumer<Project>) p -> p.getStatus().getStateHandler().applyToNegotiationFailed(p))
        );
    }
}