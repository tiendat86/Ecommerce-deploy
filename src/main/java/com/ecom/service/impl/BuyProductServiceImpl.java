package com.ecom.service.impl;

import com.ecom.controller.UserController;
import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.ProductInCart;
import com.ecom.entity.User;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductInCartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.BuyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyProductServiceImpl implements BuyProductService {
    private final ProductInCartRepository productInCartRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public String addProductInCart(Long productId, int quantity, User user) {
        if (user == null)
            return "Login to buy";
        Product product = productRepository.findProductById(productId);
        if (product.getInventoryNumber() <= 0)
            return "Product is blank";
        ProductInCart productInCart = productInCartRepository
                .findByCart_User_IdAndProduct_Id_AndBillNull(user.getId(), productId);
        if (productInCart == null) {
            productInCart = createNewProductInCart(product, user.getId(), quantity);
        } else {
            productInCart.setQuantity(productInCart.getQuantity() + quantity);
        }
        productInCartRepository.save(productInCart);
        return "Success";
    }

    private ProductInCart createNewProductInCart(Product product, Long userId, int quantity) {
        ProductInCart productInCart = new ProductInCart();
        Cart cart = cartRepository.getCartByUser_Id(userId);
        productInCart.setCart(cart);
        productInCart.setProduct(product);
        productInCart.setQuantity(quantity);
        return productInCart;
    }

    @Override
    public List<ProductInCart> getProductInCartByUser(User user) {
        if (user == null)
            return null;
        List<ProductInCart> list = productInCartRepository.findAllByCart_User_Id(user.getId());
        return list;
    }
}
