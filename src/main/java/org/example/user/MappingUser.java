package org.example.user;

import lombok.AllArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingUser {

    private DozerBeanMapper mapper;

    //из entity в dto
    public UserDTO mapToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));

        return userDTO;
    }

    //из dto в entity
    public User mapToUserEntity(UserDTO dto) {
        var user = mapper.map(dto, User.class);
        user.setRoles(dto.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        return user;
    }
}
