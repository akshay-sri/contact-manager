package com.thymleaf.contacts.entities;

import jakarta.persistence.*;
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
    private String contactName;
    private String nickName;
    private String work;
    private String contactEmail;
    private String phone;
    private String contactImage;
    private String contactDesc;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
