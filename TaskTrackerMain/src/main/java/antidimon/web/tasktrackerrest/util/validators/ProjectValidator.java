package antidimon.web.tasktrackerrest.util.validators;

import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProjectValidator implements Validator {

    private final ProjectService projectService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Project project = (Project) target;

        if (projectService.getProjectNamesByOwnerId(project.getOwner().getId()).contains(project.getName())) {
            errors.rejectValue("name", "", "Duplicate project name");
        }
    }

}
