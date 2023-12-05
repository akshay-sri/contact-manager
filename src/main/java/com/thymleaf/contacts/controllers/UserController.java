package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.Contact;
import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @ModelAttribute
    public void addCommonData(Model m,Principal p){
        String userName=p.getName();
        User user=userRepo.getUserByUserName(userName);
        m.addAttribute("user",user);
    }
    @RequestMapping("/index")
    public String dashboard(Model m)
    {
        m.addAttribute("title", "Dashboard - MyContacts");
        return "user_dashboard";
    }
    @GetMapping("/add-contact")
    public String openAddContactForm(Model m){
        m.addAttribute("title", "AddContact - MyContacts");
        m.addAttribute("contact",new Contact());
        return "add_contact";
    }
}
