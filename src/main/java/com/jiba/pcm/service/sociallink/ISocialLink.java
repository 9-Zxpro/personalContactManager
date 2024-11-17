package com.jiba.pcm.service.sociallink;

import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.SocialLink;

import java.util.List;

public interface ISocialLink {
    List<SocialLink> getByContactId(Long contactId);

    SocialLink saveLinks(SocialLink socialLink);

    List<SocialLink> saveSocialLink(Contact contact, String... Urls);

    List<SocialLink> updateLinks(Contact contact, String... Urls);

    SocialLink deleteLinks(Long contactId);
}
