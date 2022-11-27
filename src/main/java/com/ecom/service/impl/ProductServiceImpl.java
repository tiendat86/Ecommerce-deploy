package com.ecom.service.impl;

import com.ecom.entity.Brand;
import com.ecom.entity.Product;
import com.ecom.repository.BrandRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.CloudinaryService;
import com.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands;
    }

    @Override
    public Brand findBrandById(Long idBrand) {
        return brandRepository.findBrandById(idBrand);
    }

    @Override
    public Product addProduct(MultipartFile image, Product product) {
        if (!checkValidProduct(product))
            return null;
        Product saveProduct = productRepository.save(product);
        if (image.isEmpty())
            return saveProduct;
        String fileName = saveProduct.getId() + "_product_" + saveProduct.getName();
        String imageUrl = cloudinaryService.uploadFile(image, fileName);
        saveProduct.setImageUrl(imageUrl);
        return productRepository.save(saveProduct);
    }

    private boolean checkValidProduct(Product product) {
        if (StringUtils.isBlank(product.getName().trim()) || product.getInventoryNumber() <= 0 ||
                product.getPrice() <= 0 || product.getType() == null)
            return false;
        return true;
    }

    @Override
    public Product findProductById(Long idProduct) {
        return productRepository.findProductById(idProduct);
    }

    @Override
    public List<Product> findProductByBrand(Long idBand) {
        return productRepository.findProductByBrand_Id(idBand);
    }

    @Override
    public Product changeImageProduct(MultipartFile file, Long idProduct) {
        Product product = productRepository.findProductById(idProduct);
        if (product == null) return null;
        String fileName = product.getId() + "_product_" + product.getName();
        String imageUrl = cloudinaryService.uploadFile(file, fileName);
        product.setImageUrl(imageUrl);
        return productRepository.save(product);
    }
}