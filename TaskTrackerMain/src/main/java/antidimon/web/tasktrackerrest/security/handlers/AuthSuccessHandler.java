package antidimon.web.tasktrackerrest.security.handlers;

import antidimon.web.tasktrackerrest.kafka.Actions;
import antidimon.web.tasktrackerrest.services.AuthService;
import antidimon.web.tasktrackerrest.services.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final EventService eventService;
    private final AuthService authService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        setDefaultTargetUrl("/");
        log.info("Successfully logged in user {}", authentication.getName());
        eventService.sendEvent(authService.getUser(authentication), Actions.LOG_IN, null);
        log.info("Sent logIN({}) event to kafka", authentication.getName());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
