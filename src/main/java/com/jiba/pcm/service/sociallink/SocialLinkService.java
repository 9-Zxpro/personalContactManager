package com.jiba.pcm.service.sociallink;

import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.SocialLink;
import com.jiba.pcm.repository.SocialLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocialLinkService implements ISocialLink{

    @Autowired
    private SocialLinkRepository socialLinkRepository;

    @Override
    public List<SocialLink> getByContactId(Long contactId) {
        return List.of();
    }

    @Override
    public SocialLink saveLinks(SocialLink socialLink) {
        return socialLinkRepository.save(socialLink);
    }

    @Override
    public List<SocialLink> saveSocialLink(Contact contact, String... Urls) {
        List<SocialLink> lists = new ArrayList<>();
        for(String url : Urls){
            SocialLink link = new SocialLink();
            link.setTitle(getTitle(url));
            link.setLink(url);
            link.setContact(contact);
            lists.add(link);
        }
        return socialLinkRepository.saveAll(lists);
    }

    @Override
    public List<SocialLink> updateLinks(Contact contact, String... Urls) {
        List<SocialLink> existingLinks = contact.getSocialLinks();
        if (existingLinks != null) {
            existingLinks.clear();
        }

        List<SocialLink> newLinks = saveSocialLink(contact, Urls[0], Urls[1]);
        contact.getSocialLinks().addAll(newLinks);

        return newLinks;
    }

    @Override
    public SocialLink deleteLinks(Long contactId) {
        return null;
    }

    private String getTitle(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            // Remove "www." if present
            if (domain != null && domain.startsWith("www.")) {
                domain = domain.substring(4);
            }
            return domain;
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL: " + url);
            return null;
        }
    }
}
