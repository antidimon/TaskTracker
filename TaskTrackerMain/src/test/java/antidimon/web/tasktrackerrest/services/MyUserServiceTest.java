package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.repositories.ProjectRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
@Testcontainers
public class MyUserServiceTest {


    @Autowired
    private MyUserService myUserService;
    @Autowired
    private MyUserRepository myUserRepository;
    private final Flyway flyway = TestDBConfig.getFlyway();
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        flyway.migrate();
    }

    @Test
    void shouldReturnUser(){
        assertNotNull(myUserService.findByUsername("admin"));
        assertNotNull(myUserRepository.findByUsername("dima"));
    }

    @Test
    void shouldReturnNullWhenNotFoundUsername(){
        assertNull(myUserService.findByUsername("Test user"));
        assertNull(myUserService.findByUsername(null));
    }


    @Test
    void shouldReturnTrueForExistingEmail(){
        assertTrue(myUserService.hasEmail("ivasenkodiman@mail.ru"));
        assertTrue(myUserService.hasEmail("ivasenkodim@gmail.com"));
    }

    @Test
    void shouldReturnFalseForNonExistingEmail(){
        assertFalse(myUserService.hasEmail("testEmail@testEmail.ru"));
        assertFalse(myUserService.hasEmail(null));
    }

    @Test
    void shouldReturnListOfDevelopers(){
        Project project = projectRepository.findById(1L).get();
        List<MyUser> devs = myUserService.getProjectDevelopers(project);
        assertEquals(2, devs.size());
    }

    @Test
    void shouldReturnRightPossibleDevelopers(){
        Project project = projectRepository.findById(2L).get();
        assertEquals(1, myUserService.getPossibleDevelopers("d", project.getOwner().getUsername(), project).size());
        assertEquals(2, myUserService.getPossibleDevelopers("v", project.getOwner().getUsername(), project).size());
        assertEquals(0, myUserService.getPossibleDevelopers("do", project.getOwner().getUsername(), project).size());
        assertEquals(0, myUserService.getPossibleDevelopers("a", project.getOwner().getUsername(), project).size());
        assertEquals(1, myUserService.getPossibleDevelopers("va", project.getOwner().getUsername(), project).size());
        project = projectRepository.findById(1L).get();
        assertEquals(0, myUserService.getPossibleDevelopers("d", project.getOwner().getUsername(), project).size());
        assertEquals(1, myUserService.getPossibleDevelopers("v", project.getOwner().getUsername(), project).size());
        assertEquals(0, myUserService.getPossibleDevelopers("va", project.getOwner().getUsername(), project).size());
    }

    @Test
    void shouldReturnisUserDeveloperOfProject(){
        Project project = projectRepository.findById(1L).get();
        assertTrue(myUserService.isUserDevelopProject(project, "dima"));
        assertTrue(myUserService.isUserDevelopProject(project, "vanya"));
        assertFalse(myUserService.isUserDevelopProject(project, "vova"));
    }
}
