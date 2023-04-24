package org.example.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@AllArgsConstructor
public class UserController {
    UserMapping userMapping;
    UserService userService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("user/user_info")
    String updateUser(@AuthenticationPrincipal User user,
                    Map<String, Object> model){
        model.put("user", userMapping.mapToUserDto(user));
        return "user_info_page";
    }

    @PostMapping("user/update_user")
    ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO,
                              @AuthenticationPrincipal User user){

        if(bCryptPasswordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            userService.updateUser(userDTO);
            return ResponseEntity.ok("Данные изменены");
        } else {
            return ResponseEntity.badRequest().body("Введите старый пароль");
        }
    }
}
