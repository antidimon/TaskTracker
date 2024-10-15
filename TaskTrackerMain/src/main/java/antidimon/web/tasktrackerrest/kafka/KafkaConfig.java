package antidimon.web.tasktrackerrest.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${event.topic}")
    private String name;

    @Bean
    public NewTopic mainEvent(){
        return TopicBuilder.name(name).partitions(1).replicas(1).build();
    }

}
