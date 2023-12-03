package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal)
    {
        String userName=principal.getName();
        User user=userRepo.getUserByUserName(userName);
        model.addAttribute("user",user);
        model.addAttribute("title", "Dashboard - MyContacts");
        return "user_dashboard";
    }
}
