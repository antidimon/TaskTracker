package antidimon.web.tasktrackerrest.repositories;

import antidimon.web.tasktrackerrest.models.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "select project_name from Projects where user_id = :id", nativeQuery = true)
    List<String> findAllProjectNames(@Param("id") long user_id);

    @Modifying
    @Query(value = "INSERT INTO developers (project_id, user_id) VALUES (:project_id, :user_id)", nativeQuery = true)
    void addDeveloper(@Param("project_id") long project_id, @Param("user_id") long user_id);

    @Modifying
    @Query(value = "DELETE FROM developers WHERE project_id = :project_id AND user_id = :user_id", nativeQuery = true)
    void kickDeveloper(@Param("project_id") long project_id, @Param("user_id") long user_id);

    @Query(value = "SELECT p.id, p.user_id, p.project_name, p.is_active, p.created_at " +
            "FROM developers AS d LEFT JOIN projects AS p ON d.project_id = p.id WHERE d.user_id = :user_id", nativeQuery = true)
    List<Project> getDeveloperProjectsByUserId(@Param("user_id") long user_id);
}
