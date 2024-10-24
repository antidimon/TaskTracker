package antidimon.web.tasktrackerstatistics.repositories;

import antidimon.web.tasktrackerstatistics.models.MainEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MainEventRepository extends JpaRepository<MainEventEntity, Long> {
    @Query(value = "SELECT * FROM user_updates WHERE action_name = 'UPDATE_TASK' AND user_id = :id AND action_time >= NOW() - INTERVAL '24 hours'", nativeQuery = true)
    List<MainEventEntity> getUserDayEvents(@Param("id") long user_id);

    @Query(value = "SELECT uu.*, u.username, u.email, u.created_at FROM user_updates AS uu LEFT JOIN users AS u ON uu.user_id = u.id WHERE u.username = :username" +
            " AND uu.action_name = :action AND uu.action_time = :time", nativeQuery = true)
    Optional<MainEventEntity> findByUsernameAndActionAndTime(@Param("username") String username, @Param("action") String action, @Param("time") LocalDateTime time);
}
