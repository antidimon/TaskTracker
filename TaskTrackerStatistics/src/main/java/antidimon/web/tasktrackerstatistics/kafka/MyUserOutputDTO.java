package antidimon.web.tasktrackerstatistics.kafka;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyUserOutputDTO {

    private String username;
    private String email;
    private LocalDateTime time;
}
