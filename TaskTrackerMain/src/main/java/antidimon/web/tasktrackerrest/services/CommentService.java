package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.mappers.CommentMapper;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentInputDTO;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.Comment;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.repositories.CommentRepository;
import antidimon.web.tasktrackerrest.util.comparators.CommentComparator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentComparator commentComparator;
    private final MyUserService myUserService;

    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<CommentOutputDTO> getCommentsOutputDTO(List<Comment> comments){
        return comments.stream().sorted(commentComparator).map(commentMapper::commentToOutputDTO).toList();
    }

    public Comment getCommentFromInputDTO(CommentInputDTO commentInputDTO, Task task, MyUserOutputDTO myUserOutputDTO) {
        MyUser user = myUserService.findByUsername(myUserOutputDTO.getUsername());
        return this.commentMapper.inputDTOToComment(commentInputDTO, task, user);
    }
}
