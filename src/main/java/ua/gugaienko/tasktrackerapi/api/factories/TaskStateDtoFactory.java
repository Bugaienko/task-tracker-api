package ua.gugaienko.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ua.gugaienko.tasktrackerapi.api.dto.TaskStateDTO;
import ua.gugaienko.tasktrackerapi.store.models.TaskStateEntity;

/**
 * @author Sergii Bugaienko
 */

@Component
public class TaskStateDtoFactory {

    public TaskStateDTO makeTaskStateDto(TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(entity.getOrdinal())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
