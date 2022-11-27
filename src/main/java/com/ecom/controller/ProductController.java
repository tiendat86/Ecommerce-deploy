package com.ecom.controller;

import com.ecom.entity.Brand;
import com.ecom.entity.Product;
import com.ecom.entity.ProductInCart;
import com.ecom.entity.User;
import com.ecom.service.BuyProductService;
import com.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService productService;
    private final BuyProductService buyProductService;

    @GetMapping("brand/create")
    public Brand createBrand(@RequestParam String name) {
        Brand brand = new Brand();
        brand.setName(name);
        return productService.addBrand(brand);
    }

    @GetMapping(value = "brand/get_all", produces = "application/json")
    public List<Brand> getAllBrand() {
        return productService.getAll();
    }

    @PostMapping("product/create")
    public Product createProduct(@RequestPart MultipartFile image, @RequestPart Product product) {
        return productService.addProduct(image, product);
    }

    @GetMapping("product/find/brand/{idBrand}")
    public List<Product> findProductByBrand(@PathVariable Long idBrand) {
        return productService.findProductByBrand(idBrand);
    }

    @PostMapping("product/change_image/{id}")
    public Product changeImageProduct(@RequestPart MultipartFile file, @PathVariable("id") Long idProduct) {
        return productService.changeImageProduct(file, idProduct);
    }

    @GetMapping("user/add/product")
    public String addProductInCart(@RequestParam Long productId, @RequestParam int quantity) {
        return buyProductService.addProductInCart(productId, quantity, getUser());
    }

    @GetMapping("user/get_cart")
    public List<ProductInCart> getProductInCart() {
        return buyProductService.getProductInCartByUser(getUser());
    }
}
