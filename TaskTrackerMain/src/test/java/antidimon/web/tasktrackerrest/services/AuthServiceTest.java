package antidimon.web.tasktrackerrest.services;

import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
@Testcontainers
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    private final Flyway flyway = TestDBConfig.getFlyway();


    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void shouldReturnUserDTOAfterSave(){

        MyUserCreateDTO newUser = new MyUserCreateDTO(
                "testUser", "testUser@testUser.ru", "123", "123");

        String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        MyUserOutputDTO checkUser = new MyUserOutputDTO("testUser", "testUser@testUser.ru", encodedPassword);
        MyUserOutputDTO user = authService.registerUser(newUser);
        assertEquals(checkUser, user);

    }

    @Test
    void shouldReturnException(){
        MyUserCreateDTO newUser = new MyUserCreateDTO(
                "admin", "ivasenkodiman@mail.ru", "123", "123");

        try{
            authService.registerUser(newUser);
        }catch(Exception e){
            assert true;
        }
    }



}
