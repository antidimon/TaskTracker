package antidimon.web.tasktrackerrest.controllers;

import antidimon.web.tasktrackerrest.kafka.Actions;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.services.AuthService;
import antidimon.web.tasktrackerrest.services.EventService;
import antidimon.web.tasktrackerrest.util.validators.MyUserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MyUserValidator myUserValidator;
    private final AuthService authService;
    private final EventService eventService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false, name = "error") String exception, Model model) {
        if (exception != null) {
            model.addAttribute("error", exception);
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new MyUserCreateDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid MyUserCreateDTO user, BindingResult bindingResult) {

        log.info("Request for register new user {}", user.getUsername());

        myUserValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Errors in validation new user {}", user.getUsername());
            return "auth/register";
        }

        MyUserOutputDTO newUser = authService.registerUser(user);
        log.info("Successfully registered new user {}", user.getUsername());
        eventService.sendEvent(newUser, Actions.CREATE_ACCOUNT, null);
        log.info("Sen createAccount({}) event to kafka", newUser.getUsername());

        return "auth/login";
    }

}
