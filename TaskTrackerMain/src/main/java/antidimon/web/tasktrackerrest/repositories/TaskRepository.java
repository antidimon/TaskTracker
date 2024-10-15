package antidimon.web.tasktrackerrest.repositories;

import antidimon.web.tasktrackerrest.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select t.task_name from Tasks AS t where t.project_id = :id", nativeQuery = true)
    List<String> findAllTaskNames(@Param("id") Long id);

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findByProjectIdAndName(long project_id, String taskName);
}
