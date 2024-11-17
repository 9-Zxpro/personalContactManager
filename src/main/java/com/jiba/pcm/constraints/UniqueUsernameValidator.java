package com.jiba.pcm.constraints;

import com.jiba.pcm.annotations.UniqueUsername;
import com.jiba.pcm.service.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    @Autowired
    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }
        boolean isUsernameExists = userService.isUserByUserName(username);
        if (isUsernameExists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username already exists").addConstraintViolation();
            return false;
        }
        return true;
    }
}
