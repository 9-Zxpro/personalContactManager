package com.jiba.pcm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserConfig {
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    private OAuthauthenticationSuccessHandler handler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/**").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(loginConfig -> {
                    loginConfig.loginPage("/login")
                            .loginProcessingUrl("/user-login")
                            .usernameParameter("usernameOrEmail")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/user/dashboard", true)
                            .failureUrl("/login?error=true");
                })
                .logout(logoutConfig -> {
                    logoutConfig.logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/login")
                            .successHandler(handler);
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImp);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
