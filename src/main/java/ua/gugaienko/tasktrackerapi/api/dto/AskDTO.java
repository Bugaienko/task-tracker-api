package ua.gugaienko.tasktrackerapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Sergii Bugaienko
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AskDTO {

    Boolean answer;

    public static AskDTO makeDefault(Boolean answer) {
        return builder()
                .answer(answer)
                .build();
    }
}
