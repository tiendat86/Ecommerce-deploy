package com.ecom.service;

import com.ecom.entity.Product;
import com.ecom.entity.ProductInCart;
import com.ecom.entity.User;

import java.util.List;

public interface BuyProductService {
    String addProductInCart(Long productId, int quantity, User user);
    List<ProductInCart> getProductInCartByUser(User user);
}
