package com.jiba.pcm.controller;

import com.jiba.pcm.config.Helper;
import com.jiba.pcm.model.User;
import com.jiba.pcm.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/dashboard")
    public String userDashBoard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication, Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User userDetails = userService.getUserByUsername(username);
            System.out.println(userDetails);

        model.addAttribute("inUser", userDetails);
        System.out.println(username);
        return "user/profile";
    }


}
