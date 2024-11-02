package antidimon.web.tasktrackerstatistics.kafka;

import antidimon.web.tasktrackerstatistics.services.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaEventListener {

    private static final String topicName = "mainEvent";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private final EventService eventService;

    @Transactional
    @KafkaListener(topics = topicName, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=antidimon.web.tasktrackerstatistics.kafka.MainEvent"})
    public void listen(MainEvent event) {
        log.info("Received message [{}]", event);
        eventService.save(event);
        log.info("Saved new event to db");
    }
}
