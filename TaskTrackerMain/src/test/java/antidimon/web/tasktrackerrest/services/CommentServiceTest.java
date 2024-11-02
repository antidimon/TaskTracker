package antidimon.web.tasktrackerrest.services;


import antidimon.web.tasktrackerrest.TestDBConfig;
import antidimon.web.tasktrackerrest.mappers.CommentMapper;
import antidimon.web.tasktrackerrest.mappers.MyUserMapper;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentInputDTO;
import antidimon.web.tasktrackerrest.models.dto.comment.CommentOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.Comment;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Task;
import antidimon.web.tasktrackerrest.repositories.CommentRepository;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.repositories.TaskRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
@Testcontainers
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private TaskRepository taskRepository;
    private final Flyway flyway = TestDBConfig.getFlyway();
    @Autowired
    private MyUserMapper myUserMapper;


    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnSortedList(){
        List<Comment> notSortedCommentsEntities = commentRepository.findAll();
        Comment com = notSortedCommentsEntities.get(0);
        notSortedCommentsEntities.remove(0);
        notSortedCommentsEntities.add(com);
        List<CommentOutputDTO> sortedDTO = commentService.getCommentsOutputDTO(notSortedCommentsEntities);
        assertEquals(commentRepository.findAll().stream().map(commentMapper::commentToOutputDTO).toList(), sortedDTO);
    }

    @Test
    void shouldSaveEntity(){

        Comment comment = new Comment("Test comment", myUserRepository.findById(1L).get(), taskRepository.findById(1L).get());
        commentService.save(comment);
        assertTrue(comment.getId() != 0);
    }

    @Test
    void shouldGetEntityFromInput(){
        MyUser user = myUserRepository.findById(1L).get();
        MyUserOutputDTO myUserOutputDTO = myUserMapper.userToOutputDto(user);
        Task task = taskRepository.findByProjectIdAndName(1, "test1").get();

        CommentInputDTO commentInputDTO = new CommentInputDTO("Test Comment");
        Comment checkComment = new Comment("Test Comment", user, task);
        Comment comment = commentService.getCommentFromInputDTO(commentInputDTO, task, myUserOutputDTO);
        assertEquals(checkComment, comment);
    }
}
