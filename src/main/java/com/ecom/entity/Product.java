package com.ecom.entity;

import com.ecom.enumuration.ETypeProduct;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private ETypeProduct type;
    @Column(name = "price")
    private double price;
    @Lob
    @Column(name = "description")
    private String desc;
    @Column(name = "inventory_number")
    private int inventoryNumber;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    Collection<ProductInCart> productInCart;
}
