package com.jiba.pcm.request;

import com.jiba.pcm.annotations.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterUser {
    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Username must be alphanumeric and/or underscore and between 3 to 15 characters")
    @UniqueUsername(message = "Username already exist")
    private String username;
    @NotBlank(message = "First name should not blank")
    private String firstName;
    @NotBlank(message = "Last name should not blank")
    private String lastName;
    @NotBlank(message = "Please enter valid phone number")
    @Size(min = 10, max = 13, message = "Please! Enter correct phone number")
    private String phoneNumber;
    @Email(message = "Please! Enter a valid email")
    @NotBlank(message = "Email can not be empty")
    private String email;
    @Size(min = 6, message = "Min 6 length is required")
    private String password;
    @NotBlank(message = "About should not be empty")
    private String about;
    @AssertTrue(message = "Must be checked")
    private boolean privacyTerms;
}
