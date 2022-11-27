package com.ecom.entity;

import com.ecom.enumuration.EUserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber extends BaseEntity {
    @Column(name = "phone")
    private String phone;
    @Column(name = "verify_code", length = 6)
    private String verifyCode;
    @Column(name = "phone_status")
    @Enumerated(EnumType.STRING)
    private EUserStatus phoneStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}