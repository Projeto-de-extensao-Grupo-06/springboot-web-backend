package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectFile;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

import java.util.List;

public class Installed implements Status {
    @Override
    public void applyToCompleted(Project project) {
        List<ProjectFile> files = project.getFiles().stream().filter(ProjectFile::getHomologationDoc).toList();

        if(files.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + this.getClass().getSimpleName() + " -> COMPLETED. " +
                            "The project don't have a homologation doc."
            );
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.COMPLETED);
    }
}
