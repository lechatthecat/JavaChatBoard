package com.blogspot.noteoneverything.chatboard.validator;

import java.util.List;
import java.util.Date;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.blogspot.noteoneverything.chatboard.validator.MyValidateUtil;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        MyValidateUtil.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (user.getName().length() < 3 || user.getName().length() > 32) {
            errors.rejectValue("name", "Size.userForm.username");
        }
        List<User> usersByName = userService.findUsersByName(user.getName());
        if (usersByName.size()>0) {
            errors.rejectValue("name", "Duplicate.userForm.username");
        }
        List<User> usersByEmail = userService.findUsersByEmail(user.getEmail());
        if(usersByEmail.size()>0){
            errors.rejectValue("email", "Format.userForm.notUniqueEmail");
        }
        if(!MyValidateUtil.isValidDate(user.getBirth().toString(), "yyyy-MM-dd")){
            errors.rejectValue("birth", "Format.userForm.date");
        }
        MyValidateUtil.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        if (!user.getAgreesTerm()) {
            errors.rejectValue("agreesTerm", "Format.userForm.notAgreesTerm");
        }
    }
}