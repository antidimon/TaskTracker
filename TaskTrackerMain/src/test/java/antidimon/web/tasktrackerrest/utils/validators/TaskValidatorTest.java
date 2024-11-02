package antidimon.web.tasktrackerrest.utils.validators;

import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.models.TaskStatuses;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.repositories.ProjectRepository;
import antidimon.web.tasktrackerrest.repositories.TaskRepository;
import antidimon.web.tasktrackerrest.util.validators.TaskValidator;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Testcontainers
@ActiveProfiles("test")
public class TaskValidatorTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskValidator taskValidator;
    private Errors errors;

    private final Flyway flyway = TestDBConfig.getFlyway();

    @BeforeEach
    void setUp() {

        flyway.migrate();
        this.errors = mock(Errors.class);

    }

    @Test
    void newTaskShouldReturnZeroErrors(){

        Project project = projectRepository.findById(1L).get();
        Task newTask = new Task(TaskStatuses.IN_PROGRESS, project, 3, "ZeroErrorsTest", LocalDateTime.now().plusDays(10));

        taskValidator.validate(newTask, errors);
        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void newTaskShouldReturnOneError(){
        Project project = projectRepository.findById(1L).get();
        Task newTask = new Task(TaskStatuses.IN_PROGRESS, project, 3, "test1", LocalDateTime.now().plusDays(10));

        taskValidator.validate(newTask, errors);
        verify(errors).rejectValue("name", "", "Duplicate task name");
    }

    @Test
    void editTaskShouldReturnZeroErrors(){
        Task task = taskRepository.findById(1L).get();
        task.setPriority(2);

        taskValidator.validateAfterEdit(task, errors);
        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());

        task.setName("CheckName");
        task.setStatus(TaskStatuses.COMPLETED);
        taskValidator.validate(task, errors);
        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void editTaskSameNamesCheck(){
        Task task = taskRepository.findById(1L).get();
        task.setName("test2");
        taskValidator.validateAfterEdit(task, errors);
        verify(errors).rejectValue("name", "", "Duplicate task name");
    }

}
