package com.jiba.pcm.service.user;

import com.jiba.pcm.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    User getUserById(String id);
    User getUserByUsername(String username);
    boolean isUserByUserName(String userName);
    User updateUser(User user);
    User getUserByEmailOrUsername(String usernameOrEmail);
    void deleteUser(String id);
    boolean isUserByEmail(String email);
    List<User> getAllUsers();
}
