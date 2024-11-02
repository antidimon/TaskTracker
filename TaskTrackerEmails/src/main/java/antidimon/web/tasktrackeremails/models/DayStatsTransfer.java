package antidimon.web.tasktrackeremails.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayStatsTransfer {

    private String email;
    private int startedTasks;
    private int completedTasks;

}
