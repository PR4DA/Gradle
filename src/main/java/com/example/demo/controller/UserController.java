package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.FileExporter;
import com.example.demo.service.UserService;
import com.example.demo.user.CrmUser;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final FileExporter fileExporter;

    public UserController(UserService userService, FileExporter fileExporter) {
        this.userService = userService;
        this.fileExporter = fileExporter;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // add mapping for "/list"

    @GetMapping("/list")
    public String showHome(Model theModel) {
        // get employees from db
        List<User> users = userService.findAll();
        // add to the spring model
        theModel.addAttribute("users", users);
        return "users/home";
    }


    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("crmUser", new CrmUser());
        return "users/register";
    }

    @GetMapping("/showFormForUpdate/{id}")
//    public String showFormForUpdate(@RequestParam("id") Long theId, Model theModel) {
    public String showFormForUpdate(@PathVariable("id") Long theId, Model theModel) {
        // get the user from the service
        User user = userService.findById(theId);
        List<Role> listRoles = userService.listRoles();
        theModel.addAttribute("listRoles", listRoles);
        theModel.addAttribute("user", user);
        // send over to our form
        return "users/update-form";
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("id") Long id, HttpServletResponse response) {
        User user = userService.findById(id);
        final File file = new File(user.getFileUrl());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + user.getUserName() + ".txt");
        return new FileSystemResource(file);
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult theBindingResult, Model theModel) {
        String userName = crmUser.getUserName();
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
        userService.save(crmUser);
        logger.info("Successfully created user: " + userName);
        return "users/registration-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long theId) {
        try {
            userService.deleteById(theId);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        // redirect to /employees/list
        return "redirect:/users/list";
    }
}