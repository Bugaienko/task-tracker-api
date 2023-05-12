package ua.gugaienko.tasktrackerapi.store.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Sergii Bugaienko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private String description;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
