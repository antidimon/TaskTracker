package antidimon.web.tasktrackerrest.config;

import antidimon.web.tasktrackerrest.security.handlers.AuthFailureHandler;
import antidimon.web.tasktrackerrest.security.handlers.AuthSuccessHandler;
import antidimon.web.tasktrackerrest.services.AuthService;
import antidimon.web.tasktrackerrest.services.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class MainConfig {
    @Value("${event.topic}")
    private String name;

    @Bean
    public NewTopic mainEvent(){
        return TopicBuilder.name(name).partitions(1).replicas(1).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, EventService eventService, AuthService authService) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll();
                    registry.requestMatchers(HttpMethod.GET,"/","/styles/*", "/login", "/register").permitAll();
                    registry.anyRequest().authenticated();})
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .successHandler(new AuthSuccessHandler(eventService, authService))
                        .permitAll()
                        .failureHandler(new AuthFailureHandler()))
                .logout((logout) -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");

                    logout.logoutSuccessUrl("/login");
                })

                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
