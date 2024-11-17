package com.jiba.pcm.dto;

import com.jiba.pcm.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String about;
    private String profileLink;
    private Image profileImg;
    private List<ContactDto> contacts;
}
