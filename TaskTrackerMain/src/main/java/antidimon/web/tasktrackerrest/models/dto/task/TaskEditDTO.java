package antidimon.web.tasktrackerrest.models.dto.task;


import antidimon.web.tasktrackerrest.models.TaskStatuses;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskEditDTO {

    private long id;
    @NotBlank
    private String name;
    private int priority;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
    private TaskStatuses status;


    public String getStringCreatedAt(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createdAt);
    }
}
