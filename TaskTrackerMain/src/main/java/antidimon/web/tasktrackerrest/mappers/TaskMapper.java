package antidimon.web.tasktrackerrest.mappers;

import antidimon.web.tasktrackerrest.models.dto.task.TaskEditDTO;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.models.dto.task.TaskInputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskOutputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public Task inputDTOToTask(TaskInputDTO taskInputDTO, Project project) {
        Task task = modelMapper.map(taskInputDTO, Task.class);
        task.setProject(project);
        return task;
    }

    public TaskOutputDTO taskToOutputDTO(Task task){
        return modelMapper.map(task, TaskOutputDTO.class);
    }

    public TaskEditDTO taskToEditDTO(Task task) {
        return modelMapper.map(task, TaskEditDTO.class);
    }

    public Task tastEditDTOToTask(TaskEditDTO taskEditDTO, Project project) {
        Task task = modelMapper.map(taskEditDTO, Task.class);
        task.setProject(project);
        return task;
    }
}
