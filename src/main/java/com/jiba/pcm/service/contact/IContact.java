package com.jiba.pcm.service.contact;

import com.jiba.pcm.model.Contact;
import com.jiba.pcm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.awt.*;
import java.util.List;

public interface IContact {
    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(Long id);

//    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
//
//    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
//
//    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
//                                      User user);

    void delete(Long id);

//    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int pageNo, int size, String sortField, Sort.Direction sortDirection);

    char getFirstLetter(String name);

    Color getRandomColor();
}
