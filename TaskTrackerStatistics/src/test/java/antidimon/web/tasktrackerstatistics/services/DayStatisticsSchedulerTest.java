package antidimon.web.tasktrackerstatistics.services;

import antidimon.web.tasktrackerstatistics.models.DayStatsTransfer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.lazy-initialization=true")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DayStatisticsSchedulerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Container
    static PostgreSQLContainer<?> postgres = EventServiceTest.postgres;
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.show-sql", () -> "false");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "false");
    }

    @Value("${schedule.url}")
    private String url;
    @Autowired
    private EventService eventService;

    @Test
    void shouldSendDataWithOkRespond(){
        for (DayStatsTransfer dayStat: eventService.getDayStats()) {
            ResponseEntity<DayStatsTransfer> response = restTemplate.postForEntity(url, dayStat, DayStatsTransfer.class);

            assertEquals(200, response.getStatusCode().value());
        }
    }
}
