package antidimon.web.tasktrackerrest.models.dto.project;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectOutputDTO {

    private long id;

    private String name;

    private boolean is_active;
}
