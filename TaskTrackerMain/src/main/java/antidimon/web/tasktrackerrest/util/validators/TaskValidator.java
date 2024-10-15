package antidimon.web.tasktrackerrest.util.validators;

import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class TaskValidator implements Validator {

    private final TaskService taskService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Task task = (Task) target;
        if (taskService.getTasksNamesByProjectId(task.getProject().getId()).contains(task.getName())){
            errors.rejectValue("name", "", "Duplicate task name");
        }
    }

    public void validateAfterEdit(Object target, Errors errors) {

        Task task = (Task) target;
        if (taskService.getTasksNamesByProjectId(task.getProject().getId()).contains(task.getName())){
            if (!taskService.findTaskByID(task.getId()).getName().equals(task.getName())){
                errors.rejectValue("name", "", "Duplicate task name");
            }
        }
    }
}
