package com.jiba.pcm.controller;

import com.jiba.pcm.config.Helper;
import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.SocialLink;
import com.jiba.pcm.model.User;
import com.jiba.pcm.request.ContactSave;
import com.jiba.pcm.request.ProfileSave;
import com.jiba.pcm.request.RegisterUser;
import com.jiba.pcm.service.contact.IContact;
import com.jiba.pcm.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IContact iContact;

    @RequestMapping(value = "/dashboard")
    public String userDashBoard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication, Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User userDetails = userService.getUserByUsername(username);


        if(userDetails.getProfileImg() == null) {
            char firstLetter = iContact.getFirstLetter(userDetails.getFirstname());
            Color color = iContact.getRandomColor();
            String cssColor = String.format("rgb(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
            model.addAttribute("profilePicLetter", firstLetter);
            model.addAttribute("profilePicColor", cssColor);
        } else {
            String profilePic = userDetails.getProfileImg().getDownloadUrl();
            model.addAttribute("profileImg", profilePic);
        }

        model.addAttribute("inUser", userDetails);

        System.out.println(username);
        return "user/profile";
    }

    @GetMapping("/view/{userId}")
    public String updateContactFormView(@PathVariable("userId") String userId, Model model) {
        User user = userService.getUserById(userId);
        ProfileSave profileSave = new ProfileSave();
        profileSave.setUsername(user.getUsername());
        profileSave.setFirstname(user.getFirstname());
        profileSave.setLastname(user.getLastname());
        profileSave.setEmail(user.getEmail());
        profileSave.setPhone(user.getPhoneNumber());
        profileSave.setAbout(user.getAbout());
        if(user.getProfileImg()!=null) {
            profileSave.setProfileImg(user.getProfileImg().getDownloadUrl());
        }
        else {
            profileSave.setProfileImg(null);
            char firstLetter = iContact.getFirstLetter(user.getFirstname());
            Color color = iContact.getRandomColor();
            String cssColor = String.format("rgb(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
            model.addAttribute("profilePicLetter", firstLetter);
            model.addAttribute("profilePicColor", cssColor);
        }

        model.addAttribute("profileSave", profileSave);
        model.addAttribute("userId", userId);

        return "user/edit-profile";
    }

    @RequestMapping(value = "/editing-profile/{userId}", method = RequestMethod.POST)
    public String editProfile(@PathVariable("userId") String userId,
                                @Valid @ModelAttribute ProfileSave profileSave,
                                BindingResult result,
                                RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong.");
            return "redirect:/user/addContact";
        }

        User user = userService.getUserById(userId);

//        contact.setName(contactSave.getCName());
//        contact.setEmail(contactSave.getCEmail());
//        contact.setPhoneNumber(contactSave.getCPhone());
//        contact.setDescription(contactSave.getCDesc());
//        contact.getSocialLinks().clear();
//        List<SocialLink> links = socialLinkService.saveSocialLink(contact, contactSave.getCUrl1(), contactSave.getCUrl2());
//        contact.getSocialLinks().addAll(links);
//
//        contactService.update(contact);

        redirectAttributes.addFlashAttribute("successMessage","Contact updated successfully.");
        return "redirect:/user/contacts";
    }
}
