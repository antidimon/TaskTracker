package antidimon.web.tasktrackerrest.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name="comments")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment")
    @NotBlank
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser author;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @Column(name = "created_at")
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    public Comment(String comment, MyUser author, Task task) {
        this.comment = comment;
        this.author = author;
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment1)) return false;
        return Objects.equals(comment, comment1.comment) && Objects.equals(author, comment1.author) && Objects.equals(task, comment1.task) && Objects.equals(createdAt, comment1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, author, task, createdAt);
    }
}
