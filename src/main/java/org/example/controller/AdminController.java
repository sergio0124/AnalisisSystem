package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.user.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private static final int PAGE_SIZE = 10;

    private final UserService userService;
    private final MappingUser mappingUser;

    @GetMapping("admin/")
    String getRepostPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                         @RequestParam(required = false) Optional<String> search,
                         Map<String, Object> model,
                         @AuthenticationPrincipal User user) {

        int page = pageNumber.orElse(0);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<UserDTO> users;
        if (search.isPresent()) {
            String str = search.get();
            users = userService.findUsersByRoleAndSearch(pageable,
                    List.of(Role.RULER, Role.TEACHER), str);
            model.put("search", str);
        } else {
            users = userService.findUsersByRole(pageable, List.of(Role.ADMIN));
        }
        model.put("admins", users);
        model.put("page", page);
        model.put("user", mappingUser.mapToUserDto(user));

        return "main_admin_home_page";
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
                model.put("admin", userDTO);
            }
        }
        model.put("user", mappingUser.mapToUserDto(user));

        return "save_admin";
    }


    @PostMapping("admin/save_user")
    ResponseEntity<Object> saveAdmin(@RequestBody UserDTO userDTO) {
        UserDTO res;
        if (userDTO.getId() != null) {
            res = userService.updateUser(userDTO);
        } else {
            res = userService.registerUser(userDTO);
        }

        if (res == null) {
            return ResponseEntity.badRequest()
                    .body("Что-то пошло не так и пользователь не был зарегестрирован");
        }
        return ResponseEntity.ok("Пользователь создан");
    }

    @GetMapping("admin/delete_user")
    private String deleteAdminPage(@RequestParam Long userId,
                                   Map<String, Object> model,
                                   @AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.loadUserById(userId);
        if (userDTO != null) {
            model.put("admin", userDTO);
        } else {
            model.put("message", "Пользователь с таким id не найден");
        }
        model.put("user", mappingUser.mapToUserDto(user));
        return "delete_admin";
    }


    @PostMapping("admin/delete_user")
    private ResponseEntity<Object> deleteAdmin(@RequestParam Long userId) {
        UserDTO userDTO = userService.loadUserById(userId);
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }
        userService.deleteUser(userDTO);
        return ResponseEntity.ok("Пользователь удалён");
    }
}
