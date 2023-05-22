package com.dmitryshundrik.knowledgebase.security.service;

import com.dmitryshundrik.knowledgebase.security.model.User;
import com.dmitryshundrik.knowledgebase.security.model.UserRole;
import com.dmitryshundrik.knowledgebase.security.model.UserSignupDTO;
import com.dmitryshundrik.knowledgebase.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserService {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public void createUser(UserSignupDTO userDTO) {
//        User user = new User();
//        user.setName(userDTO.getName());
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setUserRoles(Set.of(UserRole.USER));
//        userRepository.save(user);
//    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
}
