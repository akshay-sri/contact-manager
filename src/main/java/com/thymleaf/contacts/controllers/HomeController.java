package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.helper.Message;
import com.thymleaf.contacts.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - MyContacts");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - MyContacts");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register - MyContacts");
        model.addAttribute("user", new User());
        return "signup";
    }

    //Handler for registering user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                               Model model, HttpSession session) {
        try {
            if (!agreement) {
                throw new Exception("Please agree terms and conditions");
            }
            if(result.hasErrors()){
                System.out.println("Error"+result.toString());
                model.addAttribute("user",user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setIsActive(true);
            user.setUserImage("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User res = this.userRepo.save(user);
            model.addAttribute("User", res);
            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong!!" + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
    //handler for custom login
    @GetMapping("/login")
    public String customLogin(Model model){
        model.addAttribute("title", "Login - MyContacts");
        return "login";
    }
}
