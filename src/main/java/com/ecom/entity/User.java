package com.ecom.entity;

import com.ecom.enumuration.EUserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(unique = true, name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(unique = true, name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "verify_code", length = 64)
    private String verifyCode;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private EUserStatus userStatus;
    @Column(name = "image_url")
    private String imageUrl;
    private String role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Cart cart;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<Bill> bills;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<Address> addresses;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<PhoneNumber> phoneNumbers;
}
