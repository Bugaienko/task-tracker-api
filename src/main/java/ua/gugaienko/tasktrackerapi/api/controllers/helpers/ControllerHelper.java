package ua.gugaienko.tasktrackerapi.api.controllers.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.gugaienko.tasktrackerapi.api.exceptions.NotFoundException;
import ua.gugaienko.tasktrackerapi.store.models.ProjectEntity;
import ua.gugaienko.tasktrackerapi.store.repositories.ProjectRepository;

/**
 * @author Sergii Bugaienko
 */

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ControllerHelper {

    private final ProjectRepository projectRepository;

    public ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(
                                String.format(
                                        "Project with \"%s\" doesn't exist",
                                        projectId
                                )
                        )
                );
    }

}
