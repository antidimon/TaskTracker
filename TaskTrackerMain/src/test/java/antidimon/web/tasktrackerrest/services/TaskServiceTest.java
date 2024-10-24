package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.TestDBConfig;
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

    private Flyway flyway = TestDBConfig.getFlyway();

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void shouldSaveTask(){

    }
}
