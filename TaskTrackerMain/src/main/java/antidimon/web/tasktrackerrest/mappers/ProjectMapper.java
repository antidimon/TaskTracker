package antidimon.web.tasktrackerrest.mappers;

import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectInputDTO;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectOutputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public Project inputDtoToProject(ProjectInputDTO ProjectInputDTO, MyUser user) {
        Project project = modelMapper.map(ProjectInputDTO, Project.class);
        project.setOwner(user);
        return project;
    }

    public ProjectOutputDTO projectToOutputDto(Project project) {return modelMapper.map(project, ProjectOutputDTO.class);}
}
