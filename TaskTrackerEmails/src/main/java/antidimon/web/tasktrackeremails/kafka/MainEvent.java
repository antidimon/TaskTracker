package antidimon.web.tasktrackeremails.kafka;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MainEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private MyUserOutputDTO user;
    private Actions action;
    private LocalDateTime time;
    private HashMap<String, String> message;
}
