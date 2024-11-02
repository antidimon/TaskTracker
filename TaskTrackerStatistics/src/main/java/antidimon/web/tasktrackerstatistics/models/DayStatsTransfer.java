package antidimon.web.tasktrackerstatistics.models;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayStatsTransfer {

    private String email;
    private int startedTasks;
    private int completedTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayStatsTransfer that)) return false;
        return startedTasks == that.startedTasks && completedTasks == that.completedTasks && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, startedTasks, completedTasks);
    }
}


//   1/ (1/(9*10^-3) * (270 + 360p*10^-3)/240 + 2/(3p * 10^-3) + 1) == 1/ (1/(8*10^-3) + p/6 + 2/(3p*10^-3) + 1) ==
//   == 1/ (3p*10^3/24p + 4p^2/24p + 16*10^3/24p + 24p/24p) == 24p / (16 + 27p + 4p^2)