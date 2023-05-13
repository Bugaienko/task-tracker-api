package ua.gugaienko.tasktrackerapi.store.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_state")
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;

    @OneToOne
    TaskStateEntity leftTaskState;
    @OneToOne
    TaskStateEntity rightTaskState;


    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_state_id", referencedColumnName = "id")
    private List<TaskEntity> tasks = new ArrayList<>();

    public Optional<TaskStateEntity> getLeftTaskState() {
        return Optional.ofNullable(leftTaskState);
    }

    public Optional<TaskStateEntity> getRightTaskState() {
        return Optional.ofNullable(rightTaskState);
    }

}
