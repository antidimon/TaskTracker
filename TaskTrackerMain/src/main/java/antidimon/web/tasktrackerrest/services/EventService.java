package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.kafka.Actions;
import antidimon.web.tasktrackerrest.kafka.KafkaEventProducer;
import antidimon.web.tasktrackerrest.kafka.MainEvent;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EventService {

    private final KafkaEventProducer kafkaEventProducer;

    public void sendEvent(MyUserOutputDTO user, Actions action, HashMap<String, String> msg) {
        MainEvent event = new MainEvent(user, action, msg);
        kafkaEventProducer.send(event);
    }
}
