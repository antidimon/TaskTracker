package antidimon.web.tasktrackerrest.models.entities;


import antidimon.web.tasktrackerrest.models.TaskStatuses;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "task_name")
    private String name;

    @Min(value = 1)
    @Max(value = 3)
    private int priority;

    @Column(name = "created_at")
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatuses status;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Task(TaskStatuses status, Project project, int priority, String name, LocalDateTime deadline) {
        this.status = status;
        this.project = project;
        this.priority = priority;
        this.name = name;
        this.deadline = deadline;
    }
}
