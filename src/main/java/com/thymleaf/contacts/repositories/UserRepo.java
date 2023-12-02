package com.thymleaf.contacts.repositories;

import com.thymleaf.contacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.userEmail = :userEmail")
    public User getUserByUserName(@Param("userEmail") String userEmail);
}
