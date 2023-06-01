package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.user.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {
    final private int PAGE_SIZE = 10;
    private final UserService userService;
    private final UserMapping userMapping;

    @GetMapping("admin/")
    String getRepostPage(@RequestParam(required = false) Optional<String> search,
                         Map<String, Object> model,
                         @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "fullname"));
        List<UserDTO> users;
        if (search.isPresent()) {
            String str = search.get();
            users = userService.findUsersByRoleAndSearch(List.of(Role.RULER, Role.TEACHER), str, pageable);
            model.put("search", str);
        } else {
            users = userService.findUsersByRole(List.of(Role.RULER, Role.TEACHER), pageable);
        }
        model.put("users", users);
        model.put("user", user);

        return "users_list_page";
    }


    @PostMapping("admin/")
    ResponseEntity<List<UserDTO>> getRepostPage(@RequestParam(required = false) Optional<String> search,
                                          @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "fullname"));
        List<UserDTO> users;
        if (search.isPresent()) {
            String str = search.get();
            users = userService.findUsersByRoleAndSearch(List.of(Role.RULER, Role.TEACHER), str, pageable);
        } else {
            users = userService.findUsersByRole(List.of(Role.RULER, Role.TEACHER), pageable);
        }

        return ResponseEntity.ok(users);
    }


    @GetMapping("admin/save_user")
    String getAdminSavePage(@RequestParam(required = false) Optional<Long> userId,
                            Map<String, Object> model,
                            @AuthenticationPrincipal User user) {
        if (userId.isPresent()) {
            Long id = userId.get();
            UserDTO userDTO = userService.loadUserById(id);
            if (userDTO == null) {
                model.put("message", "Пользователь не найден");
            } else {
                model.put("ur", userDTO);
            }
        }
        model.put("user", user);

        return "save_user";
    }


    @PostMapping("admin/save_user")
    ResponseEntity<Object> saveAdmin(@RequestBody UserDTO userDTO) {
        UserDTO res;
        if (userDTO.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("Нельзя изменять пользователей");
        } else {
            res = userService.registerUser(userDTO);
        }

        if (res == null) {
            return ResponseEntity.badRequest()
                    .body("Что-то пошло не так и пользователь не был зарегестрирован");
        }
        return ResponseEntity.ok("Пользователь создан");
    }


    @PostMapping("admin/delete_user")
    ResponseEntity<Object> deleteAdmin(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.loadUserById(userDTO.getId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }
        userService.deleteUser(user);
        return ResponseEntity.ok("Пользователь удалён");
    }
}
