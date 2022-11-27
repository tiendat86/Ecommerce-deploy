package com.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
    @Column(name = "country")
    private String country;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "ward")
    private String ward;
    @Column(name = "detail")
    private String detail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
