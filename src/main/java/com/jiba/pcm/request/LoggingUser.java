package com.jiba.pcm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoggingUser {
    @NotBlank(message = "Username is required")
    private String usernameOrEmail;
    @NotBlank(message = "Password is required")
    private String password;
    private boolean rememberMe;
}
