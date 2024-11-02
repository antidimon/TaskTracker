package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.mappers.TaskMapper;
import antidimon.web.tasktrackerrest.models.TaskStatuses;
import antidimon.web.tasktrackerrest.models.dto.task.TaskOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.repositories.ProjectRepository;
import antidimon.web.tasktrackerrest.repositories.TaskRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
@Testcontainers
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private final Flyway flyway = TestDBConfig.getFlyway();
    @Autowired
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void shouldSaveTask(){
        Project project = projectRepository.findById(1L).get();
        Task newTask = new Task(TaskStatuses.IN_PROGRESS, project, 3, "testTaskToSave", LocalDateTime.now().plusDays(10));
        taskService.save(newTask);
        assertTrue(newTask.getId() != 0, "Expected not 0 id after save");
    }

    @Test
    void shouldUpdateTask(){
        Task task = taskRepository.findById(1L).get();
        task.setStatus(TaskStatuses.IN_PROGRESS);
        task.setPriority(3);
        task.setName("taskUpdate");
        taskService.updateTask(task);
        Task updatedTask = taskRepository.findById(1L).get();
        assertTrue(updatedTask.getStatus() == TaskStatuses.IN_PROGRESS
                && updatedTask.getPriority() == 3
                && updatedTask.getName().equals("taskUpdate"),
                "Expected successfully updated fields of task");
    }

    @Test
    void shouldDeleteTask(){
        taskService.deleteTask(taskRepository.findById(1L).get());
        assertNull(taskService.findTaskByID(1L));
    }

    @Test
    void shouldReturnAllCategoriesTasksSorted(){
        List<TaskOutputDTO> tasks = projectRepository.findById(1L).get().getTasks()
                .stream().map(taskMapper::taskToOutputDTO).toList();
        List<TaskOutputDTO> todoTasks = taskService.getToDoTasksSorted(tasks);
        List<TaskOutputDTO> inProgressTasks = taskService.getInProgressTasksSorted(tasks);
        List<TaskOutputDTO> completedTasks = taskService.getCompletedTasksSorted(tasks);

        int[] expectedToDoPriorities = {2, 1, 1};
        int[] toDoPriorities = new int[3];
        for (int i = 0; i < 3; i++){
            toDoPriorities[i] = todoTasks.get(i).getPriority();
        }
        todoTasks.forEach(task -> assertSame(TaskStatuses.TODO, task.getStatus()));
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedToDoPriorities[i], toDoPriorities[i]);
        }

        int[] expectedProgressPriorities = {3, 1};
        int[] progressPriorities = new int[2];
        for (int i = 0; i < 2; i++){
            progressPriorities[i] = inProgressTasks.get(i).getPriority();
        }
        inProgressTasks.forEach(task -> assertSame(TaskStatuses.IN_PROGRESS, task.getStatus()));
        for (int i = 0; i < 2; i++) {
            assertEquals(expectedProgressPriorities[i], progressPriorities[i]);
        }

        int[] expectedCompletedPriorities = {1, 1, 1};
        int[] completedPriorities = new int[3];
        for (int i = 0; i < 3; i++){
            completedPriorities[i] = completedTasks.get(i).getPriority();
        }
        completedTasks.forEach(task -> assertSame(TaskStatuses.COMPLETED, task.getStatus()));
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedCompletedPriorities[i], completedPriorities[i]);
        }
    }


    @Test
    void shouldReturnNullForEmptyTaskAndEntityForAnother(){
        assertNotNull(taskService.findTaskByID(1L));
        assertNotNull(taskService.findTaskByID(2L));
        assertNull(taskService.findTaskByID(0L));
        assertNull(taskService.findTaskByID(13151353));
    }

    @Test
    void shouldReturnRightTask(){
        Project project = projectRepository.findById(1L).get();
        assertEquals(1L, taskService.findTaskByProjectAndName(project, "test1").getId());
        assertEquals(2L, taskService.findTaskByProjectAndName(project, "test2").getId());
        assertNotEquals(2L, taskService.findTaskByProjectAndName(project, "test3").getId());
    }
}
