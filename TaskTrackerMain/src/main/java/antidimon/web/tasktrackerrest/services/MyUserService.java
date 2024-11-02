package antidimon.web.tasktrackerrest.services;

import antidimon.web.tasktrackerrest.mappers.MyUserMapper;
import antidimon.web.tasktrackerrest.mappers.ProjectMapper;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.entities.Project;
import antidimon.web.tasktrackerrest.models.dto.project.ProjectOutputDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;
    private final MyUserMapper myUserMapper;

    public MyUser findByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> foundedUser = myUserRepository.findByUsername(username);
        return foundedUser.orElse(null);
    }

    public boolean hasEmail(String email) {
        return myUserRepository.getEmails().contains(email);
    }

    public List<MyUser> getProjectDevelopers(Project project) {
        return myUserRepository.getDevelopersByProjectId(project.getId());
    }

    public List<MyUser> getPossibleDevelopers(String username, String authName, Project project) {
        List<MyUser> foundedUsers = myUserRepository.findByUsernameStartingWithIgnoreCase(username)
                .stream().filter(user -> !user.getUsername().equals(authName)).toList();
        List<MyUser> projectDevelopers = this.getProjectDevelopers(project);
        List<String> projectDevelopersUsernames = projectDevelopers.stream().map(MyUser::getUsername).toList();

        return foundedUsers.stream().filter(user -> !projectDevelopersUsernames.contains(user.getUsername())).toList();
    }

    public List<MyUserOutputDTO> getOutputDto(List<MyUser> users){
        return users.stream().map(myUserMapper::userToOutputDto).toList();
    }

    public boolean isUserDevelopProject(Project project, String username){

        List<MyUser> developers = myUserRepository.getDevelopersByProjectId(project.getId());
        return developers.stream().map(MyUser::getUsername).toList().contains(username);
    }
}

