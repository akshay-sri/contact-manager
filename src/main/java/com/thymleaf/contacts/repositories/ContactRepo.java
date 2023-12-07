package com.thymleaf.contacts.repositories;

import com.thymleaf.contacts.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contact,Integer> {
    @Query("from Contact as c where c.user.userId = :userId")
    List<Contact> findContactsByUser(@Param("userId")int userId);
}
