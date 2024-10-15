package antidimon.web.tasktrackerrest.controllers;


import antidimon.web.tasktrackerrest.exceptions.UnableToDeleteException;
import antidimon.web.tasktrackerrest.kafka.Actions;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectInputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskInputDTO;
import antidimon.web.tasktrackerrest.models.dto.task.TaskOutputDTO;
import antidimon.web.tasktrackerrest.services.*;
import antidimon.web.tasktrackerrest.util.validators.ProjectValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectValidator projectValidator;
    private final AuthService authService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final MyUserService myUserService;
    private final EventService eventService;

    @PostMapping
    public String createProject(@ModelAttribute("createProject") @Valid ProjectInputDTO projectInputDTO, BindingResult bindingResult, Authentication authentication) {

        log.info("Request for new project");
        if (authentication == null) {
            log.debug("Authentication is null");
            return "redirect:/";
        }

        Project project = projectService.getProjectFromDto(projectInputDTO, authService.getUser(authentication));
        projectValidator.validate(project, bindingResult);

        if (bindingResult.hasErrors()) {
            log.debug("Project has errors in validation");
            return "redirect:/?error=name";
        }

        projectService.save(project);
        log.info("Saved new project {}", project.getName());
        HashMap<String, String> map = new HashMap<>();
        map.put("projectName", project.getName());
        eventService.sendEvent(authService.getUser(authentication),
                Actions.CREATE_PROJECT,
                map);
        log.info("Sended newProject({}) event to kafka", project.getName());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String projectInfo(@PathVariable long id, Model model, Authentication auth) {

        log.info("Request for project page");
        Project project = projectService.findById(id);
        log.debug("Founded project {}", project.getName());

        boolean isOwner = project.getOwner().getUsername().equals(auth.getName());

        if (!myUserService.isUserDevelopProject(project, auth.getName()) &&
                !isOwner) {
            log.info("User does not develop project {}", project.getName());
            return "redirect:/";
        }

        List<TaskOutputDTO> tasks = taskService.getAllProjectTasksDto(project.getTasks());
        log.debug("Collected tasks for project {}", project.getName());
        List<MyUser> developers = myUserService.getProjectDevelopers(project);
        log.debug("Collected developers for project {}", project.getName());
        List<TaskOutputDTO> todoTasks = taskService.getToDoTasksSorted(tasks);
        List<TaskOutputDTO> inProgressTasks = taskService.getInProgressTasksSorted(tasks);
        List<TaskOutputDTO> completedTasks = taskService.getCompletedTasksSorted(tasks);

        model.addAttribute("project", project);
        model.addAttribute("user", auth.getName());
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("todoTasks", todoTasks);
        model.addAttribute("taskInput", new TaskInputDTO());
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("developers", developers);

        return "projects/project_page";
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable long id, Authentication auth) {

        log.info("Request for deleting project with id {}", id);

        Project project = projectService.findById(id);
        log.debug("Founded project {}", project.getName());

        if (project.getOwner().getUsername().equals(auth.getName())) {
            try {
                projectService.deleteById(id);
                log.info("Deleted project {}", project.getName());
                HashMap<String, String> map = new HashMap<>();
                map.put("projectName", project.getName());
                eventService.sendEvent(authService.getUser(auth),
                        Actions.DELETE_PROJECT,
                        map);
                log.info("Sended deleteProject({}) event to kafka", project.getName());
            } catch (UnableToDeleteException e) {
                log.warn("Unable to delete project {}", project.getName());
                return "redirect:/error=delete";
            }
        }else log.info("Owner is another user");

        return "redirect:/";
    }


    @GetMapping("/{id}/invite")
    public String invitePage(@PathVariable long id, @ModelAttribute("username") String username, Model model, Authentication auth){
        return this.searchUsers(id, "", model, auth);
    }

    @PostMapping("/{id}/invite")
    public String searchUsers(@PathVariable long id, @RequestParam("username") String username, Model model, Authentication auth){

        log.info("Request for searching users by {}", username);

        Project project = projectService.findById(id);
        log.debug("Founded project {}", project.getName());

        boolean isOwner = project.getOwner().getUsername().equals(auth.getName());
        if (!isOwner){
            if (myUserService.isUserDevelopProject(project, auth.getName())){
                log.info("User is developer (redirect to project page)");
                return "redirect:/projects/"+id;
            }
            log.info("User is not developer (redirect to main page)");
            return "redirect:/";
        }

        List<MyUser> users = myUserService.getPossibleDevelopers(username, auth.getName(), project);
        log.debug("Found {} users for invite page like {}", users.size(), username);

        model.addAttribute("authName", auth.getName());
        model.addAttribute("project", projectService.getProjectOutputDTO(project));
        model.addAttribute("users", myUserService.getOutputDto(users));

        return "projects/search_user_page";
    }


    @PostMapping("/{id}/doInvite")
    public String inviteUser(@PathVariable long id, @RequestParam("username") String invitedUsername, Authentication auth){

        log.info("Request for invite user {}", invitedUsername);

        MyUser user = myUserService.findByUsername(invitedUsername);
        log.debug("Founded user {}", user.getUsername());
        Project project = projectService.findById(id);
        log.debug("Founded project {}", project.getName());

        projectService.addDeveloper(project, user);
        log.info("Added new developer({}) to project({})", invitedUsername, project.getName());
        HashMap<String, String> map = new HashMap<>();
        map.put("project", project.getName());
        map.put("invitedUsername", invitedUsername);
        eventService.sendEvent(authService.getUser(auth),
                Actions.INVITE_USER,
                map);
        log.info("Sent inviteUser({}) event to kafka", invitedUsername);

        return "redirect:/projects/"+id+"/invite";
    }

    @PostMapping("/{id}/kick/{username}")
    public String kickDeveloper(@PathVariable long id, @PathVariable String username, Authentication auth){

        log.info("Request for kick user {}", username);

        MyUser user = myUserService.findByUsername(username);
        log.debug("Founded user {}", user.getUsername());
        Project project = projectService.findById(id);
        log.debug("Founded project {}", project.getName());


        projectService.kickDeveloper(project, user);
        log.info("Kicked developer({}) from project({})", username, project.getName());
        HashMap<String, String> map = new HashMap<>();
        map.put("project", project.getName());
        map.put("kickedUsername", username);
        eventService.sendEvent(authService.getUser(auth),
                Actions.KICK_USER,
                map);
        log.info("Sent kickUser({}) event to kafka", username);

        return "redirect:/projects/"+id;
    }
}
