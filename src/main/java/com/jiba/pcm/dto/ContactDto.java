package com.jiba.pcm.dto;

import com.jiba.pcm.model.SocialLink;
import com.jiba.pcm.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ContactDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private List<SocialLink> socialLinks;
    private User user;
}
