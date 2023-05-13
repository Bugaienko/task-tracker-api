package ua.gugaienko.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStateDTO {
    @NonNull
    long id;

    @NonNull
    String name;

    @JsonProperty("left_task_state_id")
    Long leftTaskStateId;
    @JsonProperty("right_task_state_id")
    Long rightTaskStateId;


    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();

    @NonNull
    List<TaskDTO> tasks;
}
