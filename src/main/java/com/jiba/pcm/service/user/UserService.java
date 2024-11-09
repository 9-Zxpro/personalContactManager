package com.jiba.pcm.service.user;

import com.jiba.pcm.exceptions.UserNotFoundException;
import com.jiba.pcm.model.User;
import com.jiba.pcm.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        return user!=null;
    }

    @Override
    public User updateUser(User user) {
        User userUpdate = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userUpdate.setFirstname(user.getFirstname());
        userUpdate.setLastname(user.getLastname());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPhoneNumber(user.getPhoneNumber());
        userUpdate.setAbout(user.getAbout());
        userUpdate.setProfileLink(user.getProfileLink());
        userUpdate.setProfileImg(user.getProfileImg());
        userUpdate.setEmailVerified(user.isEmailVerified());
        userUpdate.setEnabled(user.isEnabled());
        return userRepository.save(userUpdate);
    }

    @Override
    public User getUserByEmailOrUsername(String usernameOrEmail) {
        boolean isEmail = usernameOrEmail.contains("@") && usernameOrEmail.contains(".");
        User user = null;
        try {
            if(isEmail) {
                user = userRepository.findByEmail(usernameOrEmail);
            } else  {
                user = userRepository.findByUsername(usernameOrEmail);
            }
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public boolean isUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
