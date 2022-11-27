package com.ecom.repository;

import com.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    List<Product> findProductByBrand_Id(Long idBrand);
}
