package ua.gugaienko.tasktrackerapi.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gugaienko.tasktrackerapi.store.models.ProjectEntity;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Sergii Bugaienko
 */

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByName (String name);

    Stream<ProjectEntity> streamAllBy();
    Stream<ProjectEntity> streamAllByNameStartingWithIgnoreCase(String prefixName);
    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);


}
