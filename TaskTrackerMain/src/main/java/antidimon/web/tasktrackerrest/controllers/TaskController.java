package antidimon.web.tasktrackerrest.controllers;

import antidimon.web.tasktrackerrest.kafka.Actions;
import antidimon.web.tasktrackerrest.models.TaskStatuses;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentInputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskEditDTO;
import antidimon.web.tasktrackerrest.models.entities.Comment;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.models.dto.task.TaskInputDTO;
import antidimon.web.tasktrackerrest.services.*;
import antidimon.web.tasktrackerrest.util.validators.TaskValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller
@RequestMapping("/projects/{projectId}/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final TaskValidator taskValidator;
    private final MyUserService myUserService;
    private final CommentService commentService;
    private final AuthService authService;
    private final EventService eventService;

    @PostMapping
    public String createTask(@PathVariable("projectId") long projectId, @ModelAttribute("taskInput") @Valid TaskInputDTO taskInputDTO, BindingResult bindingResult, Authentication auth) {

        log.info("Request for new task");
        Project project = projectService.findById(projectId);

        Task task = taskService.getTaskFromDto(taskInputDTO, project);
        taskValidator.validate(task, bindingResult);

        if (bindingResult.hasErrors()) {
            log.debug("Errors in creating new tasks");
            return "redirect:/projects/"+projectId;
        }


        taskService.save(task);
        log.info("Sucessfully created new task {}", task.getName());
        HashMap<String, String> map = new HashMap<>();
        map.put("taskName", task.getName());
        map.put("projectName", project.getName());
        eventService.sendEvent(authService.getUser(auth),
                Actions.ADD_TASK,
                map);
        log.info("Sent createTask({}) event to kafka", task.getName());

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{taskName}")
    public String taskInfo(@PathVariable("taskName") String taskName, @PathVariable("projectId") long projectId, Model model, Authentication auth) {

        log.info("Request for info about task {}", taskName);
        Project project = projectService.findById(projectId);
        boolean isOwner = project.getOwner().getUsername().equals(auth.getName());

        if (!myUserService.isUserDevelopProject(project, auth.getName()) && !isOwner){
            log.info("User is not develop project {}", project.getName());
            return "redirect:/";
        }

        Task task = taskService.findTaskByProjectAndName(project, taskName);
        log.debug("Founded task {}", task.getName());

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", auth.getName());
        model.addAttribute("task", taskService.getTaskEditDTO(task));
        model.addAttribute("comments", commentService.getCommentsOutputDTO(task.getComments()));
        model.addAttribute("newComment", new CommentInputDTO());
        model.addAttribute("project", projectService.getProjectOutputDTO(project));

        return "tasks/task_page";
    }

    @PostMapping("/{taskName}")
    public String updateTask(@PathVariable("taskName") String taskName, @PathVariable("projectId") long projectId, @ModelAttribute("task") @Valid TaskEditDTO taskEditDTO, BindingResult bindingResult, Authentication auth) {

        log.info("Request for update task {}", taskName);
        Project project = projectService.findById(projectId);

        Task task = taskService.getTaskFromEditDTO(taskEditDTO, project);
        log.debug("Founded task {}", task.getName());

        taskValidator.validateAfterEdit(task, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("Errors in updating task {}", task.getName());
            return "redirect:/projects/"+projectId+"/tasks/"+taskName;
        }

        TaskStatuses taskStatusBeforeUpdate = taskService.findTaskByProjectAndName(project, task.getName()).getStatus();
        taskService.updateTask(task);
        log.info("Successfully updated task {}", task.getName());

        HashMap<String, String> map = new HashMap<>();
        map.put("taskName", task.getName());
        map.put("projectName", project.getName());
        map.put("taskNewStatus", (taskStatusBeforeUpdate.equals(task.getStatus())) ? null : task.getStatus().toString());
        eventService.sendEvent(authService.getUser(auth),
                Actions.UPDATE_TASK,
                map);
        log.info("Sent updateTask({}) event to kafka", task.getName());

        return "redirect:/projects/"+projectId;
    }

    @PostMapping("/{taskName}/delete")
    public String deleteTask(@PathVariable("taskName") String taskName, @PathVariable("projectId") long projectId, Authentication auth) {

        log.info("Request for delete task {}", taskName);

        Project project = projectService.findById(projectId);

        if (project.getOwner().getUsername().equals(auth.getName())) {

            Task task = taskService.findTaskByProjectAndName(project, taskName);
            log.debug("Founded task {}", task.getName());
            taskService.deleteTask(task);
            log.info("Successfully deleted task {}", task.getName());
            HashMap<String, String> map = new HashMap<>();
            map.put("taskName", task.getName());
            map.put("projectName", project.getName());
            eventService.sendEvent(authService.getUser(auth),
                    Actions.DELETE_TASK,
                    map);
            log.info("Sent deleteTask({}) event to kafka", task.getName());
        }else log.info("User is not owner of project {}", project.getName());

        return "redirect:/projects/"+projectId;
    }

    @PostMapping("/{taskName}/comment")
    public String addComment(@PathVariable("taskName") String taskName, @PathVariable("projectId") long projectId, @ModelAttribute("newComment") @Valid CommentInputDTO commentInputDTO, BindingResult bindingResult, Authentication auth){

        log.info("Request for add comment to task {}", taskName);

        Project project = projectService.findById(projectId);
        Task task = taskService.findTaskByProjectAndName(project, taskName);
        log.debug("Founded task {}", task.getName());
        Comment comment = commentService.getCommentFromInputDTO(commentInputDTO, task, authService.getUser(auth));

        if (bindingResult.hasErrors()) {
            log.info("Errors in adding comment {}", task.getName());
            return "redirect:/projects/"+projectId+"/tasks/"+taskName;
        }

        commentService.save(comment);
        log.info("Successfully added comment {}", task.getName());
        return "redirect:/projects/"+projectId+"/tasks/"+taskName;
    }
}
