package antidimon.web.tasktrackerstatistics.models;


import antidimon.web.tasktrackerstatistics.kafka.Actions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_updates")
public class MainEventEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser user;
    @Column(name = "action_name")
    @Enumerated(EnumType.STRING)
    private Actions action;
    @Column(name = "action_time")
    private LocalDateTime time;
    @Column(name = "action_msg")
    private String msg;

    public MainEventEntity(MyUser user, Actions action, LocalDateTime time, String msg) {
        this.user = user;
        this.action = action;
        this.time = time;
        this.msg = msg;
    }
}
