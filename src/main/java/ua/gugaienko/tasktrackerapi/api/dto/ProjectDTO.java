package ua.gugaienko.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/**
 * @author Sergii Bugaienko
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDTO {

    @NonNull
    long id;

    @NonNull
    String name;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

}
