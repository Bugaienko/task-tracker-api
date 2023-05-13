package ua.gugaienko.tasktrackerapi.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.gugaienko.tasktrackerapi.api.dto.TaskStateDTO;
import ua.gugaienko.tasktrackerapi.store.models.TaskStateEntity;

import java.util.stream.Collectors;

/**
 * @author Sergii Bugaienko
 */

@RequiredArgsConstructor
@Component
public class TaskStateDtoFactory {

    private final TaskDtoFactory taskDtoFactory;

    public TaskStateDTO makeTaskStateDto(TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .leftTaskStateId(entity.getLeftTaskState().map(TaskStateEntity::getId).orElse(null))
                .rightTaskStateId(entity.getRightTaskState().map(TaskStateEntity::getId).orElse(null))
                .tasks(entity
                        .getTasks()
                        .stream()
                        .map(taskDtoFactory::makeTaskDto)
                        .collect(Collectors.toList())
                )
                .build();

    }
}
