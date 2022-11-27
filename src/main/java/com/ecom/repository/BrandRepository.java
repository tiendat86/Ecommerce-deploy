package com.ecom.repository;

import com.ecom.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBrandById(Long id);
}
