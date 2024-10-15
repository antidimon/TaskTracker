package antidimon.web.tasktrackerrest.models.dto.task;

import antidimon.web.tasktrackerrest.models.TaskStatuses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskOutputDTO {

    private String name;
    private int priority;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime deadline;
    private TaskStatuses status;

    public String getStringDeadline(){
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(this.deadline);
    }
}
