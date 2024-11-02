package antidimon.web.tasktrackerrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class TaskTrackerMain {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerMain.class, args);
    }

}
