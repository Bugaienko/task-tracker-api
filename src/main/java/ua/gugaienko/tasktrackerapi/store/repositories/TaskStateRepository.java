package ua.gugaienko.tasktrackerapi.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gugaienko.tasktrackerapi.store.models.TaskStateEntity;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
