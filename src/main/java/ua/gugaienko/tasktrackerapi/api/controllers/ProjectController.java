package ua.gugaienko.tasktrackerapi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gugaienko.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import ua.gugaienko.tasktrackerapi.api.dto.AskDTO;
import ua.gugaienko.tasktrackerapi.api.dto.ProjectDTO;
import ua.gugaienko.tasktrackerapi.api.exceptions.BadRequestException;
import ua.gugaienko.tasktrackerapi.api.factories.ProjectDtoFactory;
import ua.gugaienko.tasktrackerapi.store.models.ProjectEntity;
import ua.gugaienko.tasktrackerapi.store.repositories.ProjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Sergii Bugaienko
 */

@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;

    private final ProjectDtoFactory projectDtoFactory;

    private final ControllerHelper controllerHelper;

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";


    @GetMapping(FETCH_PROJECT)
    public List<ProjectDTO> fetchProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartingWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);


//        if (optionalPrefixName.isPresent()){
//            projectStream = projectRepository.streamAllByNameStartingWithIgnoreCase(optionalPrefixName.get());
//        } else {
//            projectStream = projectRepository.streamAll();
//        }

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName
            // Another params
    ) {

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        boolean isCreate = optionalProjectId.isEmpty();

        if (isCreate && optionalProjectName.isEmpty()) {
            throw new BadRequestException("Project name can't be empty.");
        }

        final ProjectEntity project = optionalProjectId
                .map(controllerHelper::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());


        optionalProjectName
                .ifPresent(projectName -> {
                    projectRepository
                            .findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(
                                        String.format("Project \"%s\" already exist.", projectName)
                                );
                            });
                    project.setName(projectName);
                });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);


        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @Transactional
    @PostMapping(CREATE_PROJECT)
    public ProjectDTO createProject(@RequestParam String project_name) {

        if (project_name.trim().isEmpty()) {
            throw new BadRequestException("Name can't be empty.");
        }

        projectRepository
                .findByName(project_name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exist.", project_name));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(project_name)
                        .build()
        );


        return projectDtoFactory.makeProjectDto(project);
    }

    @Transactional
    @PatchMapping(EDIT_PROJECT)
    public ProjectDTO editProject(@PathVariable("project_id") Long projectId,
                                  @RequestParam String projectName) {

        if (projectName.trim().isEmpty()) {
            throw new BadRequestException("Name can't be empty.");
        }

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        projectRepository
                .findByName(projectName)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exist.", projectName));
                });


        project.setName(projectName);

        project = projectRepository.saveAndFlush(project);


        return projectDtoFactory.makeProjectDto(project);
    }



    @Transactional
    @DeleteMapping(DELETE_PROJECT)
    public AskDTO deleteProject(@PathVariable("project_id") Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AskDTO.makeDefault(true);
    }


}
