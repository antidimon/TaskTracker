package antidimon.web.tasktrackerrest.controllers;

import antidimon.web.tasktrackerrest.models.dto.project.ProjectOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectInputDTO;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.services.AuthService;
import antidimon.web.tasktrackerrest.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthService authService;
    private final ProjectService projectService;


    @GetMapping("/")
    public String mainPage(@RequestParam(required = false, name = "error") String exception, Model model, Authentication auth) {
        if (auth != null) {

            MyUserOutputDTO user = authService.getUser(auth);
            List<ProjectOutputDTO> userProjects = projectService.findUserProjects(user);
            List<ProjectOutputDTO> devProjects = projectService.getUserDeveloperProjects(user);

            model.addAttribute("user", auth.getName());
            model.addAttribute("user_projects", userProjects);
            model.addAttribute("dev_projects", devProjects);
        }

        if (exception != null) model.addAttribute("exception", exception);

        model.addAttribute("createProject", new ProjectInputDTO());
        return "home";
    }
}
