package antidimon.web.tasktrackerrest.utils.validators;

import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.util.validators.ProjectValidator;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Testcontainers
@ActiveProfiles("test")
public class ProjectValidatorTest {

    @Autowired
    private ProjectValidator validator;
    @Autowired
    private MyUserRepository myUserRepository;
    private Errors errors;

    private final Flyway flyway = TestDBConfig.getFlyway();


    @BeforeEach
    void setUp() {

        flyway.migrate();
        this.errors = mock(Errors.class);

    }

    @Test
    void shouldReturnZeroErrors() {

        Project project = new Project(myUserRepository.findByUsername("admin").get(), "ZeroTest");
        validator.validate(project, errors);
        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void shouldReturnOneError() {
        Project project = new Project(myUserRepository.findByUsername("admin").get(), "test");
        validator.validate(project, errors);
        verify(errors).rejectValue("name", "", "Duplicate project name");
    }
}
