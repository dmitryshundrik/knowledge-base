package com.dmitryshundrik.knowledgebase.security.controller;

import com.dmitryshundrik.knowledgebase.security.model.UserSignupDTO;
import com.dmitryshundrik.knowledgebase.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("userDTO", new UserSignupDTO());
        return "security/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("userDTO") UserSignupDTO userDTO) {
        userService.createUser(userDTO);
        return "redirect:/login";
    }

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
