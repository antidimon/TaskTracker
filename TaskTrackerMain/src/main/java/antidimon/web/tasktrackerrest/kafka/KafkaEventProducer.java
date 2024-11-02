package antidimon.web.tasktrackerrest.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {

    @Value("${event.topic}")
    private String topic;

    private final KafkaTemplate<String, MainEvent> kafkaTemplate;

    public void send(MainEvent event) {
        log.info("Sending JSON : {}", event);
        log.info("--------------------------------");
        kafkaTemplate.send(topic, event);
    }
}
