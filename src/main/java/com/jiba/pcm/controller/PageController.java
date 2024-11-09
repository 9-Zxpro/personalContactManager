package com.jiba.pcm.controller;

import com.jiba.pcm.config.UserDetailsServiceImp;
import com.jiba.pcm.model.User;
import com.jiba.pcm.request.LoggingUser;
import com.jiba.pcm.request.RegisterUser;
import com.jiba.pcm.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImp serviceImp;

    @GetMapping("/")
    public String index() {
        return "index";
    }
//    @GetMapping("/user/dashboard")
//    public String userDashboard() {
//        return "user/dashboard";
//    }
//    @GetMapping("/user/profile")
//    public String userProfile() {
//        return "user/profile";
//    }
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("home page hai");
        model.addAttribute("pageName", "This is jiba.com");
        model.addAttribute("github", "https://github.com/9-zxpro");
        return "index";
    }
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login(Model model) {
        LoggingUser loggingUser = new LoggingUser();
        model.addAttribute("loggingUser", loggingUser);
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "register";
    }

//    @RequestMapping(value = "/user-logging", method = RequestMethod.POST)
//    public String userLogging(@Valid @ModelAttribute LoggingUser loggingUser, BindingResult bindingResult) {
//        if(loggingUser.getUsernameOrEmail() == null || loggingUser.getPassword() == null) {
//            bindingResult.rejectValue("usernameOrEmail", "error.usernameOrEmail", "Username or password is incorrect.");
//            System.out.println("Logging User null: " + loggingUser);
//            return "login";
//        }
//        if(bindingResult.hasErrors()) {
//            System.out.println("Logging User2: " + loggingUser);
//            return "login";
//        }
//        String usernameOrEmail = loggingUser.getUsernameOrEmail();
//        boolean isEmail = usernameOrEmail.contains("@") && usernameOrEmail.contains(".");
//        User user = null;
//        try {
//            if(isEmail) {
//                user = userService.getUserByUsername(usernameOrEmail);
//            } else  {
//                user = userService.getUserByUsername(usernameOrEmail);
//            }
//        } catch (Exception e) {
//            bindingResult.rejectValue("usernameOrEmail", "error.usernameOrEmail", "Username or password is incorrect.");
//            System.out.println("Logging User2: " + loggingUser);
//            return "login";
//        }
//        String rawPassword = loggingUser.getPassword();
//        if(passwordEncoder.matches(rawPassword, user.getPassword())) {
//            UserDetails userDetails = serviceImp.loadUserByUsername(usernameOrEmail);
//            var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            System.out.println("Database User2: " + user.getEmail());
//            return "redirect:/user/dashboard";
//        } else {
//            bindingResult.rejectValue("usernameOrEmail", "error.usernameOrEmail", "Username or password is incorrect.");
//            System.out.println("Logging User4: " + loggingUser);
//            return "login";
//        }
//    }

    @RequestMapping(value = "/user-register", method = RequestMethod.POST)
    public String userRegister(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }
        System.out.println(registerUser.getEmail());
        User user = User.builder()
                .username(registerUser.getUsername())
                .firstname(registerUser.getFirstName())
                .lastname(registerUser.getLastName())
                .phoneNumber(registerUser.getPhoneNumber())
                .email(registerUser.getEmail())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .about(registerUser.getAbout())
                .termsAndPrivacy(registerUser.isPrivacyTerms())
                .build();
        try {
            User savedUser = userService.saveUser(user);
            System.out.println(savedUser.getEmail());
        } catch (Exception e) {
            bindingResult.rejectValue("email", "error.email", "Email already exists. Please choose a different one.");
            return "register";
        }

        return "redirect:/login";
    }

}
