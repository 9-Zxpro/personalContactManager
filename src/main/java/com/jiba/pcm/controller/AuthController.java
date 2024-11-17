package com.jiba.pcm.controller;

import com.jiba.pcm.model.User;
import com.jiba.pcm.repository.UserRepository;
import com.jiba.pcm.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify-email")
    public String verifyMail(@RequestParam("token") String token) {
        User user = userRepository.findByEmailToken(token).orElse(null);
        if(user!=null && user.getEmailToken().equals(token)) {
            user.setEnabled(true);
            user.setEmailVerified(true);
            userRepository.save(user);
            return "redirect:/mail-verification?verified=true";
        }
        return "redirect:/mail-verification";
    }
}
