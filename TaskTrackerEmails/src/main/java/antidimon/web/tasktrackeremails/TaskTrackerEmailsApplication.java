package antidimon.web.tasktrackeremails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class TaskTrackerEmailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerEmailsApplication.class, args);
    }

}
