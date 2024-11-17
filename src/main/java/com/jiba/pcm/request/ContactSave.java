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
public class ContactSave {
    @NotBlank(message = "Name is required")
    private String cName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address [ example@gmail.com ]")
    private String cEmail;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String cPhone;

    @NotBlank(message = "Description is required")
    private String cDesc;

    @NotBlank(message = "Link is required")
    private String cUrl1;

    @NotBlank(message = "Link is required")
    private String cUrl2;
}
