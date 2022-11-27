package com.ecom.repository;

import com.ecom.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getCartByUser_Id(Long userId);
}
