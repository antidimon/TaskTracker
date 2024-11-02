package antidimon.web.tasktrackerrest.repositories;

import antidimon.web.tasktrackerrest.models.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);

    @Query(value = "SELECT email FROM users", nativeQuery = true)
    List<String> getEmails();

    @Query(value = "SELECT u.id, u.username, u.email, u.password,u.created_at " +
            "FROM users AS u, developers AS d WHERE d.project_id = :id AND d.user_id = u.id",
            nativeQuery = true)
    List<MyUser> getDevelopersByProjectId(@Param("id") Long id);

    List<MyUser> findByUsernameStartingWithIgnoreCase(String username);
}
