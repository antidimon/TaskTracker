package antidimon.web.tasktrackerstatistics.repositories;

import antidimon.web.tasktrackerstatistics.models.MainEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainEventRepository extends JpaRepository<MainEventEntity, Long> {
    @Query(value = "SELECT * FROM user_updates WHERE action_name = 'UPDATE_TASK' AND user_id = :id AND action_time >= NOW() - INTERVAL '24 hours'", nativeQuery = true)
    List<MainEventEntity> getUserDayEvents(@Param("id") long user_id);
}
