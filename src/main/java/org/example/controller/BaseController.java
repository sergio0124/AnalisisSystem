package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.user.Role;
import org.example.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BaseController {
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    private String sayHello(@AuthenticationPrincipal User curUser) {

        var roles = curUser.getAuthorities();
        if (roles.contains(Role.ADMIN)) {
            return "redirect:admin/";
        } else if (roles.contains(Role.RULER)) {
            return "redirect:ruler/";
        } else if (roles.contains(Role.TEACHER)) {
            return "redirect:teacher/";
        }

        return "error";
    }

}
