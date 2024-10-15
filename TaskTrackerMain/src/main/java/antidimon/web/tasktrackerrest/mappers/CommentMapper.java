package antidimon.web.tasktrackerrest.mappers;

import antidimon.web.tasktrackerrest.models.dto.comment.CommentInputDTO;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.Comment;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public CommentOutputDTO commentToOutputDTO(Comment comment) {
        return modelMapper.map(comment, CommentOutputDTO.class);
    }

    public Comment inputDTOToComment(CommentInputDTO commentInputDTO, Task task, MyUser user) {
        Comment comment = modelMapper.map(commentInputDTO, Comment.class);
        comment.setTask(task);
        comment.setAuthor(user);
        return comment;
    }
}
