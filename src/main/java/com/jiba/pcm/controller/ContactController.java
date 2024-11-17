package com.jiba.pcm.controller;

import com.jiba.pcm.config.Helper;
import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.SocialLink;
import com.jiba.pcm.model.User;
import com.jiba.pcm.request.ContactSave;
import com.jiba.pcm.service.contact.ContactService;
import com.jiba.pcm.service.contact.IContact;
import com.jiba.pcm.service.image.ImageService;
import com.jiba.pcm.service.sociallink.SocialLinkService;
import com.jiba.pcm.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private IContact contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private SocialLinkService socialLinkService;

    @RequestMapping("/add")
    public String addContact(Model model) {
        ContactSave contactSave = new ContactSave();
        model.addAttribute("contactSave", contactSave);
        return "user/addContact";
    }

    @RequestMapping("/notific")
    public String notif() {
        return "user/notification";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactSave contactSave,
                              BindingResult result,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong.");
            return "user/addContact";
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmailOrUsername(username);
        Contact contact = createContact(contactSave);
        contact.setUser(user);
        Contact savedContact = contactService.save(contact);
        List<SocialLink> links = socialLinkService.saveSocialLink(savedContact, contactSave.getCUrl1(), contactSave.getCUrl2());
        contact.setSocialLinks(links);
        contactService.save(contact);
        redirectAttributes.addFlashAttribute("successMessage","Contact added successfully.");
        return "redirect:/user/contacts";
    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "size", defaultValue = "4") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction, Model model,
            Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmailOrUsername(username);
        Page<Contact> pageContact = contactService.getByUser(user, pageNo, size, sortBy, direction);
        List<Character> profilePicLetter = new ArrayList<>();
        List<String> profilePicColor = new ArrayList<>();
        for (Contact c : pageContact) {
            char firstLetter = contactService.getFirstLetter(c.getName());
            Color color = contactService.getRandomColor();
            String cssColor = String.format("rgb(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
            profilePicLetter.add(firstLetter);
            profilePicColor.add(cssColor);
        }
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("profilePicLetter", profilePicLetter);
        model.addAttribute("profilePicColor", profilePicColor);

        return "user/contacts";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") Long contactId, RedirectAttributes redirectAttributes) {
        contactService.delete(contactId);

        redirectAttributes.addFlashAttribute("successMessage", "Contact is Deleted successfully !! ");

        return "redirect:/user/contacts";
    }

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") Long contactId, Model model) {
        Contact contact = contactService.getById(contactId);
        ContactSave contactSave = new ContactSave();
        contactSave.setCName(contact.getName());
        contactSave.setCEmail(contact.getEmail());
        contactSave.setCPhone(contact.getPhoneNumber());
        contactSave.setCDesc(contact.getDescription());
        contactSave.setCUrl1(contact.getSocialLinks().get(0).getLink());
        contactSave.setCUrl2(contact.getSocialLinks().get(1).getLink());

        model.addAttribute("contactSave", contactSave);
        model.addAttribute("contactId", contactId);

        return "user/editContact";
    }

    @RequestMapping(value = "/edit/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") Long contactId,
                                @Valid @ModelAttribute ContactSave contactSave,
                                BindingResult result,
                                RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong.");
            return "redirect:/user/addContact";
        }

        Contact contact = contactService.getById(contactId);
        contact.setName(contactSave.getCName());
        contact.setEmail(contactSave.getCEmail());
        contact.setPhoneNumber(contactSave.getCPhone());
        contact.setDescription(contactSave.getCDesc());
        contact.getSocialLinks().clear();
        List<SocialLink> links = socialLinkService.saveSocialLink(contact, contactSave.getCUrl1(), contactSave.getCUrl2());
        contact.getSocialLinks().addAll(links);

        contactService.update(contact);

        redirectAttributes.addFlashAttribute("successMessage","Contact updated successfully.");
        return "redirect:/user/contacts";
    }

    private Contact createContact(ContactSave contactSave) {
        return Contact.builder()
                .name(contactSave.getCName())
                .email(contactSave.getCEmail())
                .phoneNumber(contactSave.getCPhone())
                .description(contactSave.getCDesc())
                .build();
    }

}
