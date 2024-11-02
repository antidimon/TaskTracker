package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.exceptions.UnableToDeleteException;
import antidimon.web.tasktrackerrest.mappers.MyUserMapper;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectInputDTO;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
@Testcontainers
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private MyUserMapper myUserMapper;

    private final Flyway flyway = TestDBConfig.getFlyway();

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldSaveEntity(){
        Project project = new Project(myUserRepository.findById(1L).get(), "testProject");
        projectService.save(project);
        assertTrue(project.getId() != 0, "Expected success save");
    }

    @Test
    void shouldReturnRightProjectNames(){
        List<String> names = projectRepository.findAllProjectNames(1);
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("test");
        expectedNames.add("test2");
        assertEquals(expectedNames, names, "Expected equal names for test-data projects");
    }

    @Test
    void shouldDeleteEntity(){
        try {
            projectService.deleteById(1);
        }catch (Exception e){
            assertInstanceOf(UnableToDeleteException.class, e);
            return;
        }
        assertNull(projectService.findById(1), "expected null for deleted project");
    }

    @Test
    void shouldAddNewDeveloper(){
        MyUser newDeveloper = myUserRepository.findById(2L).get();
        MyUser newDeveloper2 = myUserRepository.findById(3L).get();
        Project project = projectRepository.findById(2L).get();
        projectService.addDeveloper(project, newDeveloper);
        projectService.addDeveloper(project, newDeveloper2);
        List<MyUser> expectedDevelopers = new ArrayList<>();
        expectedDevelopers.add(newDeveloper);
        expectedDevelopers.add(newDeveloper2);
        assertEquals(myUserRepository.getDevelopersByProjectId(project.getId()), expectedDevelopers,
                "Expected list with onlhy new developers");
    }

    @Test
    void shouldDeleteDeveloper(){
        MyUser developer = myUserRepository.findById(2L).get();
        Project project = projectRepository.findById(4L).get();
        projectService.kickDeveloper(project, developer);
        assertEquals(myUserRepository.getDevelopersByProjectId(project.getId()), List.of(), "expected empty list for project with id 4");
    }

    @Test
    void shouldReturnProjectOrNull(){
        assertNull(projectService.findById(1242141L), "big id for tests expected null");
        assertNull(projectService.findById(0L), "0 id expected null");
        assertNotNull(projectService.findById(1L), "1 id expected project");
        assertNotNull(projectService.findById(2L), "2 id expected project");
    }

    @Test
    void shouldReturnNewProjectEntity(){
        MyUserOutputDTO user = myUserMapper.userToOutputDto(myUserRepository.findById(1L).get());
        ProjectInputDTO projectInputDTO = new ProjectInputDTO("testProject", true);
        Project project = new Project(myUserRepository.findById(1L).get(), "testProject");
        assertEquals(projectService.getProjectFromDto(projectInputDTO, user), project, "Expected new project entity");
    }

    @Test
    void shouldReturnUserProjectsDTO(){
        MyUserOutputDTO user = myUserMapper.userToOutputDto(myUserRepository.findById(1L).get());
        List<ProjectOutputDTO> list = projectService.findUserProjects(user);
        assertEquals(list.size(), 2);
        list.forEach(project -> assertInstanceOf(ProjectOutputDTO.class, project));
    }

}
