package ua.gugaienko.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ua.gugaienko.tasktrackerapi.api.dto.TaskDTO;
import ua.gugaienko.tasktrackerapi.store.models.TaskEntity;

/**
 * @author Sergii Bugaienko
 */

@Component
public class TaskDtoFactory {

    public TaskDTO makeTaskDto(TaskEntity entity) {
        return TaskDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
