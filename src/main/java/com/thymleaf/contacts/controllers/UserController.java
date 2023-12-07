package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.Contact;
import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.helper.Message;
import com.thymleaf.contacts.repositories.ContactRepo;
import com.thymleaf.contacts.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContactRepo contactRepo;

    @ModelAttribute
    public void addCommonData(Model m, Principal p) {
        String userName = p.getName();
        User user = userRepo.getUserByUserName(userName);
        m.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashboard(Model m) {
        m.addAttribute("title", "Dashboard - MyContacts");
        return "user_dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model m) {
        m.addAttribute("title", "AddContact - MyContacts");
        m.addAttribute("contact", new Contact());
        return "add_contact";
    }

    @PostMapping("/process-contact")
    public String processContact(
            Principal p, Model m, @Valid @ModelAttribute("contact") Contact contact, HttpSession session) {
           m.addAttribute("title", "AddContact - MyContacts");
        try {
            String userName = p.getName();
            User user = userRepo.getUserByUserName(userName);
            contact.setUser(user);
            contact.setContactImage("contact.png");
            Contact savedContact = this.contactRepo.save(contact);
            m.addAttribute("Contact", savedContact);
            m.addAttribute("contact", new Contact());

            //success message
            session.setAttribute("message", new Message("Contact added successfully","success"));
        } catch (Exception e) {
            e.printStackTrace();
            // error message
            session.setAttribute("message", new Message("Something went wrong","danger"));
        }
        return "add_contact";
    }
    @GetMapping("/show-contact")
    public String showContacts(Model m,Principal p){
        m.addAttribute("title","ViewContact - MyContacts");
        String name=p.getName();
        User user=this.userRepo.getUserByUserName(name);
        List<Contact> contacts= this.contactRepo.findContactsByUser(user.getUserId());
        m.addAttribute("contacts",contacts);
        return "show_contacts";
    }

    @GetMapping("/deleteContact/{contactId}")
    public String deleteContact(@PathVariable (value = "contactId") Integer cId,HttpSession session) {
        Optional<Contact> contactOptional=this.contactRepo.findById(cId);
        Contact contact=contactOptional.get();
        this.contactRepo.delete(contact);
        session.setAttribute("message",new Message("Contact deleted successfully","success"));
        return "redirect:/show-contact";
    }
}
