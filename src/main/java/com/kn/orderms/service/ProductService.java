package com.kn.orderms.service;

import com.kn.orderms.dao.ProductRepository;
import com.kn.orderms.entity.Product;
import com.kn.orderms.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }


    public Product readProduct(String skuCode) throws InvalidDataException {
        if (skuCode == null || skuCode.isBlank()){
            throw new InvalidDataException("Product SKU code must not be blank");
        }

        Optional<Product> product = productRepository.findById(skuCode);
        return product.orElseThrow(() -> new EntityNotFoundException(
                String.format("Product with SKU code: %s not found", skuCode)));
    }
}
