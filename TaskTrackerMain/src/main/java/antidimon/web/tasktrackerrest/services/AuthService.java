package antidimon.web.tasktrackerrest.services;

import antidimon.web.tasktrackerrest.mappers.MyUserMapper;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.repositories.MyUserRepository;
import antidimon.web.tasktrackerrest.security.model.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MyUserRepository myUserRepository;
    private final MyUserMapper myUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final MyUserService myUserService;

    @Transactional
    @SneakyThrows
    public MyUserOutputDTO registerUser(MyUserCreateDTO myUserCreateDTO) {
        MyUser user = myUserMapper.createDTOToUser(myUserCreateDTO);
        encodePersonPassword(user);
        myUserRepository.save(user);
        return myUserMapper.userToOutputDto(user);
    }

    private void encodePersonPassword(MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("Encoded password for user {}", user.getUsername());
    }

    public MyUserOutputDTO getUser(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        return myUserMapper.userToOutputDto(myUserService.findByUsername(userDetails.getUsername()));
    }
}
