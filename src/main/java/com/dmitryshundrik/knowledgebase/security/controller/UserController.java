package com.dmitryshundrik.knowledgebase.security.controller;

import com.dmitryshundrik.knowledgebase.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "security/login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}
