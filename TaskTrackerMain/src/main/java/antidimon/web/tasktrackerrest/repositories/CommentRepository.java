package antidimon.web.tasktrackerrest.repositories;

import antidimon.web.tasktrackerrest.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
