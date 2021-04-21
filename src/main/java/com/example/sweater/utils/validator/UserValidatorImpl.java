package com.example.sweater.utils.validator;

import com.example.sweater.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidatorImpl implements Validator, UserValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getUsername().length()<1 || user.getUsername().length()>255){
            errors.rejectValue("username","Size.userForm.username", "User name not validates length(must have < 1 and > 255)");
        }
        if (user.getEmail().length()<1 || user.getEmail().length()>255){
            errors.rejectValue("email","Size.userForm.email", "User email not validates length(must have < 1 and > 255)");
        }
        if (user.getPassword().length()<1 || user.getPassword().length()>255){
            errors.rejectValue("password","Size.userForm.password", "User password not validates length(must have < 1  and > 255)");
        }
        if (user.getPassword2().length()<1 || user.getPassword2().length()>255){
            errors.rejectValue("password2","Size.userForm.password2", "User password2 not validates length(must have < 1  and > 255)");
        }
        if (user.getFirstname().length()<1 || user.getFirstname().length()>255){
            errors.rejectValue("firstname","Size.userForm.firstname", "User firstname not validates length(must have < 1 and > 255)");
        }
        if (user.getLastname().length()<1 || user.getLastname().length()>255){
            errors.rejectValue("lastname","Size.userForm.lastname", "User lastname not validates length(must have < 1 and > 255)");
        }
        if (user.getEmail().length()<1 || user.getEmail().length()>255){
            errors.rejectValue("email","Size.userForm.email", "User email not validates length(must have < 1 and > 255)");
        }
    }

    @Override
    public void bindingResultErrors(BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("username")) {
                model.addAttribute("usernameError", bindingResult.getFieldError("username").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("password")) {
                model.addAttribute("passwordError", bindingResult.getFieldError("password").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("password2")) {
                model.addAttribute("password2Error", bindingResult.getFieldError("password2").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("firstname")) {
                model.addAttribute("firstnameError", bindingResult.getFieldError("firstname").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("lastname")) {
                model.addAttribute("lastnameError", bindingResult.getFieldError("lastname").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("email")) {
                model.addAttribute("emailError", bindingResult.getFieldError("email").getDefaultMessage());
            }
        }
    }
}
