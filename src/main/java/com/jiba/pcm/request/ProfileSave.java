package com.jiba.pcm.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileSave {
    @NotBlank(message = "Name is required")
    private String username;

    @NotBlank(message = "Name is required")
    private String firstname;

    @NotBlank(message = "Name is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address [ example@gmail.com ]")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phone;

    private String about;

    private String profileImg;

}
