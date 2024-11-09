package com.jiba.pcm.controller;

import com.jiba.pcm.config.Helper;
import com.jiba.pcm.model.User;
import com.jiba.pcm.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    @Autowired
    private IUserService userService;

    @ModelAttribute
    public void loggedUser(Model model, Authentication authentication) {
        if(authentication == null) return;
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User userDetails = userService.getUserByUsername(username);
        System.out.println(userDetails.getEmail());

        model.addAttribute("inUser", userDetails);
    }
}
