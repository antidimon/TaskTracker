package antidimon.web.tasktrackerrest.models.dto.task;

import antidimon.web.tasktrackerrest.models.TaskStatuses;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInputDTO {

    @NotBlank
    private String name;
    private int priority = 1;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
    private TaskStatuses status = TaskStatuses.TODO;
}
