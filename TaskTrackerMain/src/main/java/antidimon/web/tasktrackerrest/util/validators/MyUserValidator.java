package antidimon.web.tasktrackerrest.util.validators;

import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.services.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MyUserValidator implements Validator {

    private final MyUserService myUserService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MyUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MyUserCreateDTO user = (MyUserCreateDTO) target;

        if (!Objects.equals(user.getPassword(), user.getConfirmationPassword())){
            errors.rejectValue("password", "", "Wrong confirmation password");
        }
        if (myUserService.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username", "", "Username is taken");
        }
        if (myUserService.hasEmail(user.getEmail())){
            errors.rejectValue("email", "", "Email is already in use");
        }
    }
}
