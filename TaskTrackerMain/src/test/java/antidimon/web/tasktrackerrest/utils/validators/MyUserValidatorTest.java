package antidimon.web.tasktrackerrest.utils.validators;


import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.util.validators.MyUserValidator;
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


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Testcontainers
@ActiveProfiles("test")
public class MyUserValidatorTest {

    @Autowired
    private MyUserValidator myUserValidator;
    private Errors errors;

    private final Flyway flyway = TestDBConfig.getFlyway();


    @BeforeEach
    void setUp() {

        flyway.migrate();
        this.errors = mock(Errors.class);

    }


    @Test
    void shouldReturnZeroErrors() {

        MyUserCreateDTO newUser = new MyUserCreateDTO(
                "testUser", "testUser@testUser.ru", "123", "123");

        myUserValidator.validate(newUser, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void shouldReturnErrorsWithEmail(){

        MyUserCreateDTO newUser2 = new MyUserCreateDTO(
                "testUser", "ivasenkodiman@mail.ru", "123", "123");

        myUserValidator.validate(newUser2, errors);
        verify(errors).rejectValue("email", "", "Email is already in use");
    }

    @Test
    void shouldReturnErrorsWithUsername(){

        MyUserCreateDTO newUser3 = new MyUserCreateDTO(
                "admin", "ADMIN@admin.ru", "123", "123");

        myUserValidator.validate(newUser3, errors);
        verify(errors).rejectValue("username", "", "Username is taken");
    }

}
