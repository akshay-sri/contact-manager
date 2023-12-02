package com.thymleaf.contacts.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Size(min=2,max = 20,message = "Name should be min. of 2 characters and max. of 20 characters")
    @NotBlank(message = "Name is required")
    private String userName;
    @Email(message = "Enter valid email address")
    @NotBlank(message = "Email is required")
    private String userEmail;
    @NotBlank(message = "Password is required")
    private String password;
    private String role;
    private String about;
    private Boolean isActive;
    private String userImage;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts=new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", about='" + about + '\'' +
                ", isActive=" + isActive +
                ", userImage='" + userImage + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
