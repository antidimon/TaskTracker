package antidimon.web.tasktrackeremails.kafka;

import antidimon.web.tasktrackeremails.services.EmailService;
import antidimon.web.tasktrackeremails.services.EventService;
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
    private final EmailService emailService;

    @Transactional
    @KafkaListener(topics = topicName, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=antidimon.web.tasktrackeremails.kafka.MainEvent"})
    public void listen(MainEvent event) {
        log.info("Received message [{}]", event);
        emailService.sendEmail(event.getUser().getEmail(), eventService.getActionTopic(event), eventService.getMessage(event));
    }
}
