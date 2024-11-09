package com.jiba.pcm.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import java.util.Map;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication){
        String username = null;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            for (Map.Entry<String, Object> e: oauthUser.getAttributes().entrySet()) {
                System.out.println(e);
            }
            if(oauthUser.getAttributes().containsKey("sub")) {
                username=(String) oauthUser.getAttributes().get("name");
            }
//            else{
//                for other social accounts
//            }
        } else {
            username = authentication.getName();

        }

        return username;
    }
}
