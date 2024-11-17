package com.jiba.pcm.config;

import com.jiba.pcm.enums.Provider;
import com.jiba.pcm.enums.Roles;
import com.jiba.pcm.model.User;
import com.jiba.pcm.repository.UserRepository;
import com.jiba.pcm.service.user.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Component
public class OAuthauthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        save data before redirect
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        if(!userService.isUserByEmail(user.getAttribute("email"))){
            String[] name = Objects.requireNonNull(user.getAttribute("name")).toString().split("\\s+");
            user.getAttributes().forEach((key, value)->{
                System.out.println(key+": "+value);
            });
            String[] email = Objects.requireNonNull(user.getAttribute("name")).toString().split("@");

//        create user to save into database
            User savingUser = User.builder()
                    .username(email[0])
                    .firstname(name[0])
                    .lastname(name[1])
                    .email(user.getAttribute("email"))
                    .password("password")
                    .provider(Provider.GOOGLE)
                    .enabled(true)
                    .emailVerified(true)
                    .role(Set.of(Roles.USER))
                    .termsAndPrivacy(true)
                    .build();
            userService.saveUser(savingUser);
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}
