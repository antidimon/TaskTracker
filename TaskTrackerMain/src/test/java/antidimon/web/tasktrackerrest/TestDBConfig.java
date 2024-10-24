package antidimon.web.tasktrackerrest;


import lombok.Getter;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestDBConfig {
    private static final int localPort = 5432;
    private static final int containerPort = 29546;


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testDB")
            .withUsername("test")
            .withPassword("123")
            .withReuse(true);

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.name", postgres::getDatabaseName);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
    }

    @Getter
    private static final Flyway flyway;

    static {
        postgres.setPortBindings(List.of(containerPort + ":" + localPort));
        postgres.start();
        flyway = Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .cleanDisabled(false)
                .load();
    }

    public static PostgreSQLContainer<?> getDB() {
        return postgres;
    }

}
