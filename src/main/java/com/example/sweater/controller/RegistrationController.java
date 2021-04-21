package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import com.example.sweater.utils.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Value("${upload.path}q")
    private String uploadPathq;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration") 
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid
            @ModelAttribute("userForm") User user,
            BindingResult bindingResult,
            Model model,
            @RequestParam("fileq") MultipartFile fileq
    ) throws IOException {

        userValidator.validate(user, bindingResult);

        userValidator.bindingResultErrors(bindingResult, model);

        if (!userService.searchEmail(user)) { model.addAttribute("emailError", "Email are used"); }
        if (!userService.searchUserName(user)) { model.addAttribute("usernameError", "User name are used"); }
        if (!userService.searchPhone(user)) { model.addAttribute("phoneError", "User phone are used"); }
        if (user.getDate().equals("")){ model.addAttribute("dateError", "User date cann`t be empty"); }
        if(!user.getPassword().equals(user.getPassword2())){ model.addAttribute("password",  "password don`t equals password2");}

        if (user.getUsername() != null && !user.getUsername().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getPassword2()!= null && !user.getPassword2().isEmpty() &&
                user.getPassword().equals(user.getPassword2()) &&
                userService.searchUserName(user) &&
                userService.searchPhone(user) &&
                userService.searchEmail(user) &&
                user.getFirstname()!= null && !user.getFirstname().isEmpty() &&
                user.getLastname()!= null && !user.getLastname().isEmpty() &&
                user.getDate()!= null && !user.getDate().isEmpty() &&
                user.getEmail()!= null && !user.getEmail().isEmpty() &&
                user.getPhone()!= null && !user.getPhone().isEmpty() &&
                user.getPhone().trim().length()==12 &&
                isFilet(user, fileq)) {

            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivatorCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/main";
        } else {
            model.addAttribute("user", user);
            return "registration";}
    }

    private boolean isFilet(User user, MultipartFile fileq) throws IOException {
        if (fileq != null && !Objects.requireNonNull(fileq.getOriginalFilename()).isEmpty()) {
            File uploadDirq = new File(uploadPathq);
            if (!uploadDirq.exists()) {
                uploadDirq.mkdir();
            }
            String uuidFileq = UUID.randomUUID().toString();
            String resultFilenameq = uuidFileq + "." + fileq.getOriginalFilename();
            fileq.transferTo(new File(uploadPathq + "/" + resultFilenameq));
            user.setFilenameq(resultFilenameq);
            return true;
        }
        else { return false;}
    }

}