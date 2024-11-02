package antidimon.web.tasktrackerrest.security;

import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.security.model.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> foundedUser = myUserRepository.findByUsername(username);
        if (foundedUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        MyUser user = foundedUser.get();

        return new MyUserDetails(user);
    }
}
