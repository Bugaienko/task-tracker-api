package ua.gugaienko.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ua.gugaienko.tasktrackerapi.api.dto.ProjectDTO;
import ua.gugaienko.tasktrackerapi.store.models.ProjectEntity;

/**
 * @author Sergii Bugaienko
 */

@Component
public class ProjectDtoFactory {

    public ProjectDTO makeProjectDto(ProjectEntity entity){
        return ProjectDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
