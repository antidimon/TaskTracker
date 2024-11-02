package antidimon.web.tasktrackerstatistics.services;

import antidimon.web.tasktrackerstatistics.kafka.Actions;
import antidimon.web.tasktrackerstatistics.kafka.MainEvent;
import antidimon.web.tasktrackerstatistics.kafka.MyUserOutputDTO;
import antidimon.web.tasktrackerstatistics.models.DayStatsTransfer;
import antidimon.web.tasktrackerstatistics.models.MainEventEntity;
import antidimon.web.tasktrackerstatistics.models.MyUser;
import antidimon.web.tasktrackerstatistics.models.TaskStatuses;
import antidimon.web.tasktrackerstatistics.repositories.MainEventRepository;
import antidimon.web.tasktrackerstatistics.repositories.MyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.show-sql", () -> "false");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "false");
    }

    @Autowired
    private MainEventRepository mainEventRepository;
    @Autowired
    private MyUserRepository myUserRepository;

    private EventService eventService;
    private MainEvent event1, event2;
    private MyUserOutputDTO userDto1, userDto2;
    private MyUser user1, user2;

    @BeforeEach
    void setUp() {

        this.eventService = new EventService(mainEventRepository, myUserRepository);

        this.mainEventRepository.deleteAll();
        this.myUserRepository.deleteAll();

        myUserRepository.count();
        mainEventRepository.count();


        user1 = new MyUser("admin", "ivasenkodiman@mail.ru", LocalDateTime.now());
        userDto1 = new MyUserOutputDTO("admin", "ivasenkodiman@mail.ru", LocalDateTime.now());
        HashMap<String, String> map = new HashMap<>();
        map.put("taskName", "hello");
        map.put("projectName", "main");
        map.put("taskNewStatus", TaskStatuses.COMPLETED.toString());
        event1 = new MainEvent(userDto1, Actions.UPDATE_TASK, LocalDateTime.now(), map);

        user2 = new MyUser("dima", "ivasenkodim@gmail.com", LocalDateTime.now());
        userDto2 = new MyUserOutputDTO("dima", "ivasenkodim@gmail.com", LocalDateTime.now());
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("taskName", "aloha");
        map2.put("projectName", "lesson");
        map2.put("taskNewStatus", TaskStatuses.IN_PROGRESS.toString());
        event2 = new MainEvent(userDto2, Actions.UPDATE_TASK, LocalDateTime.now(), map2);

        myUserRepository.save(user1);
        myUserRepository.save(user2);

        eventService.save(event1);
        eventService.save(event2);

    }


    @Test
    void shouldReturnMyUserObject(){
        assertEquals(eventService.getUser(userDto1), user1);
        assertEquals(eventService.getUser(userDto2), user2);
    }


    @Test
    void shouldReturnListOfStats(){
        List<DayStatsTransfer> dayStats = eventService.getDayStats();
        List<DayStatsTransfer> expectedStats = new ArrayList<>();
        expectedStats.add(new DayStatsTransfer("ivasenkodiman@mail.ru", 0, 1));
        expectedStats.add(new DayStatsTransfer("ivasenkodim@gmail.com", 1, 0));

        assertEquals(expectedStats, dayStats);
    }

    @Test
    void shouldSaveNewUserAndEvent(){
        MyUser expectedUser = new MyUser("antidimon", "ivasen@mail.ru", LocalDateTime.now());
        MainEventEntity expectedEvent = new MainEventEntity(expectedUser, Actions.CREATE_ACCOUNT, LocalDateTime.now(), null);
        MyUserOutputDTO newUser = new MyUserOutputDTO("antidimon", "ivasen@mail.ru", LocalDateTime.now());
        MainEvent event = new MainEvent(newUser, Actions.CREATE_ACCOUNT, LocalDateTime.now(), null);
        eventService.save(event);
        assertEquals(expectedUser, myUserRepository.findByUsername("antidimon").get());
    }
}
