package antidimon.web.tasktrackerrest.models.dto.comment;


import antidimon.web.tasktrackerrest.models.entities.MyUser;
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
public class CommentOutputDTO {

    private MyUser author;
    private String comment;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    public String getStringCreatedAt() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(this.createdAt);
    }
}