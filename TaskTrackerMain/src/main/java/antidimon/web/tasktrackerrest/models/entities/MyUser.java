package antidimon.web.tasktrackerrest.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MyUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank
    private String username;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Project> projects;



    public MyUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return Objects.equals(username, myUser.username) && Objects.equals(email, myUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
