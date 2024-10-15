package antidimon.web.tasktrackerrest.kafka;

import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

@NoArgsConstructor
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

    public MainEvent(MyUserOutputDTO user, Actions action, HashMap<String, String> message) {
        this.user = user;
        this.action = action;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
