package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.Contact;
import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.repositories.ContactRepo;
import com.thymleaf.contacts.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContactRepo contactRepo;
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
        User user=this.userRepo.getUserByUserName(principal.getName());
        List<Contact> contacts=this.contactRepo.findBycontactNameContainingAndUser(query,user);
        return ResponseEntity.ok(contacts);
    }
}
