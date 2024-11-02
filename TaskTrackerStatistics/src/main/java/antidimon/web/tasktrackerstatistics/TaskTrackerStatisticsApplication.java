package antidimon.web.tasktrackerstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class TaskTrackerStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerStatisticsApplication.class, args);
    }

}
