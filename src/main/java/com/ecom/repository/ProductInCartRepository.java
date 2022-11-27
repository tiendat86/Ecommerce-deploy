package com.ecom.repository;

import com.ecom.entity.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
    List<ProductInCart> findAllByCart_User_Id(Long userId);
    ProductInCart findByCart_User_IdAndProduct_Id_AndBillNull(Long userId, Long productId);
}
