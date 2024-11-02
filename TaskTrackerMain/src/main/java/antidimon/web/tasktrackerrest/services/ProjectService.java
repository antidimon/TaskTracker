package antidimon.web.tasktrackerrest.services;

import antidimon.web.tasktrackerrest.exceptions.UnableToDeleteException;
import antidimon.web.tasktrackerrest.mappers.ProjectMapper;
import antidimon.web.tasktrackerrest.mappers.TaskMapper;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectInputDTO;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskOutputDTO;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.repositories.ProjectRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final MyUserRepository myUserRepository;
    private final MyUserService myUserService;


    @Transactional
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Transactional
    public void deleteById(long id) throws UnableToDeleteException {
        try{
            projectRepository.deleteById(id);
        }catch (Exception e){
            throw new UnableToDeleteException("Can't delete project");
        }
    }
    @Transactional
    public void addDeveloper(Project project, MyUser user) {
        projectRepository.addDeveloper(project.getId(), user.getId());
    }

    @Transactional
    public void kickDeveloper(Project project, MyUser user) {
        projectRepository.kickDeveloper(project.getId(), user.getId());
    }

    public List<String> getProjectNamesByOwnerId(long id){
        return projectRepository.findAllProjectNames(id);
    }

    public Project findById(long user_id) {
        Optional<Project> foundedProject =  projectRepository.findById(user_id);
        return foundedProject.orElse(null);
    }

    public Project getProjectFromDto(ProjectInputDTO projectInputDTO, MyUserOutputDTO myUserOutputDTO) {
        MyUser user = myUserService.findByUsername(myUserOutputDTO.getUsername());
        return projectMapper.inputDtoToProject(projectInputDTO, user);
    }

    public List<ProjectOutputDTO> getUserDeveloperProjects(MyUserOutputDTO userOutputDTO) {
        MyUser user = myUserRepository.findByUsername(userOutputDTO.getUsername()).get();
        return projectRepository.getDeveloperProjectsByUserId(user.getId()).stream()
                .map(projectMapper::projectToOutputDto).toList();
    }

    public ProjectOutputDTO getProjectOutputDTO(Project project) {
        return projectMapper.projectToOutputDto(project);
    }

    public List<ProjectOutputDTO> findUserProjects(MyUserOutputDTO myUserOutputDTO) {
        MyUser user = myUserService.findByUsername(myUserOutputDTO.getUsername());
        return user.getProjects().stream().map(projectMapper::projectToOutputDto).toList();
    }
}
