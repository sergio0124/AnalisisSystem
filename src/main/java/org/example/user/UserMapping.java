package org.example.user;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapping {

    private ModelMapper mapper;

    //из entity в dto
    public UserDTO mapToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(Role::getTitle).collect(Collectors.toSet()));

        return userDTO;
    }

    //из dto в entity
    public User mapToUserEntity(UserDTO userDTO) {
        var user = mapper.map(userDTO, User.class);
        user.setRoles(userDTO.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        return user;
    }
}
