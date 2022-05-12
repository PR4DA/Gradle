package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private final UserService userService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        theModel.addAttribute("crmUser", new CrmUser());

        return "users/register";
    }

    @PostMapping("/update")
    public String saveUser(User user, BindingResult theBindingResult) {
        String userName = user.getUserName();
        logger.info("Processing information form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()) {
            return "users/register";
        }

        userService.update(user);

        logger.info("Successfully updated user: " + userName);

        return "redirect:/users/list";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, BindingResult theBindingResult, Model theModel) {
        String userName = theCrmUser.getUserName();
        logger.info("Processing registration form for: " + userName);
        // form validation
        if (theBindingResult.hasErrors()) {
            return "users/register";
        }
        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            theModel.addAttribute("crmUser", new CrmUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "users/register";
        }
        // create user account        						
        userService.save(theCrmUser);
        logger.info("Successfully created user: " + userName);
        return "users/registration-confirmation";
    }
}
