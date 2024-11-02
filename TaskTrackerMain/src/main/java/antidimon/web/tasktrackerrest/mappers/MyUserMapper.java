package antidimon.web.tasktrackerrest.mappers;

import antidimon.web.tasktrackerrest.models.entities.MyUser;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserCreateDTO;
import antidimon.web.tasktrackerrest.models.dto.user.MyUserOutputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public MyUser createDTOToUser(MyUserCreateDTO inputUser){
        return modelMapper.map(inputUser, MyUser.class);
    }

    public MyUserOutputDTO userToOutputDto(MyUser myUser) {
        return modelMapper.map(myUser, MyUserOutputDTO.class);
    }
}
