package antidimon.web.tasktrackerrest.models.dto.project;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectInputDTO {


    @NotBlank
    private String name;

    private boolean is_active = true;
}
