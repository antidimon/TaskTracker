package antidimon.web.tasktrackerrest.services;

import antidimon.web.tasktrackerrest.mappers.TaskMapper;
import antidimon.web.tasktrackerrest.models.dto.task.TaskEditDTO;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.models.TaskStatuses;
import antidimon.web.tasktrackerrest.models.dto.task.TaskInputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskOutputDTO;
import antidimon.web.tasktrackerrest.repositories.TaskRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Task task) {
        Task foundedTask = taskRepository.findById(task.getId()).get();

        foundedTask.setStatus(task.getStatus());
        foundedTask.setPriority(task.getPriority());
        foundedTask.setName(task.getName());
        foundedTask.setDeadline(task.getDeadline());

        taskRepository.save(foundedTask);
    }

    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public List<TaskOutputDTO> getToDoTasksSorted(List<TaskOutputDTO> tasks) {
        return tasks.stream().filter(taskOutputDTO -> taskOutputDTO.getStatus().equals(TaskStatuses.TODO))
                .sorted(Comparator.comparingInt(TaskOutputDTO::getPriority).reversed()).toList();
    }

    public List<TaskOutputDTO> getInProgressTasksSorted(List<TaskOutputDTO> tasks) {
        return tasks.stream().filter(taskOutputDTO -> taskOutputDTO.getStatus().equals(TaskStatuses.IN_PROGRESS))
                .sorted(Comparator.comparingInt(TaskOutputDTO::getPriority).reversed()).toList();
    }

    public List<TaskOutputDTO> getCompletedTasksSorted(List<TaskOutputDTO> tasks) {
        return tasks.stream().filter(taskOutputDTO -> taskOutputDTO.getStatus().equals(TaskStatuses.COMPLETED))
                .sorted(Comparator.comparingInt(TaskOutputDTO::getPriority).reversed()).toList();
    }

    public Task findTaskByID(long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<TaskOutputDTO> getAllProjectTasksDto(List<Task> tasks) {
        return tasks.stream().map(taskMapper::taskToOutputDTO).toList();
    }

    public TaskEditDTO getTaskEditDTO(Task task) {
        return taskMapper.taskToEditDTO(task);
    }

    public Task getTaskFromEditDTO(TaskEditDTO taskEditDTO, Project project) {
        Task task = taskMapper.tastEditDTOToTask(taskEditDTO, project);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
        task.setDeadline(LocalDateTime.parse(task.getDeadline().format(formatter), formatter));
        return task;
    }

    public Task findTaskByProjectAndName(Project project, String taskName) {
        Optional<Task> foundedTask = taskRepository.findByProjectIdAndName(project.getId(), taskName);
        return foundedTask.get();
    }

    public List<String> getTasksNamesByProjectId(long id) {
        return taskRepository.findAllTaskNames(id);
    }

    public Task getTaskFromDto(TaskInputDTO taskDTO, Project project) {
        return taskMapper.inputDTOToTask(taskDTO, project);
    }
}
