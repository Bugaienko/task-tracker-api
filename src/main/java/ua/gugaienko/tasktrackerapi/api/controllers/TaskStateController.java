package ua.gugaienko.tasktrackerapi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gugaienko.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import ua.gugaienko.tasktrackerapi.api.dto.ProjectDTO;
import ua.gugaienko.tasktrackerapi.api.dto.TaskStateDTO;
import ua.gugaienko.tasktrackerapi.api.exceptions.BadRequestException;
import ua.gugaienko.tasktrackerapi.api.factories.TaskStateDtoFactory;
import ua.gugaienko.tasktrackerapi.store.models.ProjectEntity;
import ua.gugaienko.tasktrackerapi.store.models.TaskStateEntity;
import ua.gugaienko.tasktrackerapi.store.repositories.TaskStateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Sergii Bugaienko
 */

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskStateController {
    private final TaskStateRepository taskStateRepository;
    private final TaskStateDtoFactory taskStateDtoFactory;

    private final ControllerHelper controllerHelper;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";


    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDTO> getTaskStates(@PathVariable Long project_id) {

        ProjectEntity project = controllerHelper.getProjectOrThrowException(project_id);

        return project
                .getTaskStates()
                .stream()
                .map(taskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDTO createTaskState(
            @PathVariable(name = "project_id") Long projectId,
            @RequestParam(name = "task_state_name") String taskStateName
    ) {

        if (taskStateName.trim().isBlank()) {
            throw new BadRequestException("Task State name can't be empty.");
        }
        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        project
                .getTaskStates()
                .stream()
                .map(TaskStateEntity::getName)
                .filter(anotherTaskStateName -> anotherTaskStateName.equalsIgnoreCase(taskStateName))
                .findAny()
                .ifPresent(it -> {
                    throw new BadRequestException(String.format("Task state \"%s\" already exists.", taskStateName));
                });
        TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                TaskStateEntity.builder()
                        .name(taskStateName)
                        .build()
        );

    }

}
