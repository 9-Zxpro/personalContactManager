package com.jiba.pcm.service.contact;

import com.jiba.pcm.exceptions.ResourceNotFoundException;
import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.User;
import com.jiba.pcm.repository.ContactRepository;
import com.jiba.pcm.service.sociallink.SocialLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Random;

@Service
public class ContactService implements IContact{
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private SocialLinkService socialLinkService;

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact oldContact = contactRepository.findById(contact.getContactId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        oldContact.setName(contact.getName());
        oldContact.setEmail(contact.getEmail());
        oldContact.setPhoneNumber(contact.getPhoneNumber());
        oldContact.setDescription(contact.getDescription());
        oldContact.getSocialLinks().clear();
        oldContact.getSocialLinks().addAll(contact.getSocialLinks());
        return contactRepository.save(oldContact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

    @Override
    public void delete(Long id) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
        contactRepository.delete(contact);
    }

//    @Override
//    public List<Contact> getByUserId(String userId) {
//        return contactRepository.findByUserId(userId);
//    }

    @Override
    public Page<Contact> getByUser(User user, int pageNo, int size, String sortField, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(sortDirection, sortField));
        return contactRepository.findByUser(user, pageable);
    }

    @Override
    public char getFirstLetter(String name) {
        return name != null && !name.isEmpty() ? name.toUpperCase().charAt(0) : ' ';
    }

    @Override
    public Color getRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

}
