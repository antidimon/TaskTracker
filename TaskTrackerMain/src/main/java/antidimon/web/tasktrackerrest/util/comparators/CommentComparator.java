package antidimon.web.tasktrackerrest.util.comparators;

import antidimon.web.tasktrackerrest.models.entities.Comment;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class CommentComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment o1, Comment o2) {
        if (o1.getId() == o2.getId()) return 0;
        return (o1.getId()-o2.getId() < 0) ? -1 : 1;
    }
}
