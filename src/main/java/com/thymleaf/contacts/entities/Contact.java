package com.thymleaf.contacts.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    @NotBlank(message = "Name is required")
    private String contactName;
    private String nickName;
    private String work;
    @Email(message = "Enter valid email address")
    @NotBlank(message = "Email is required")
    private String contactEmail;
    private String phone;
    private String contactImage;
    private String contactDesc;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
