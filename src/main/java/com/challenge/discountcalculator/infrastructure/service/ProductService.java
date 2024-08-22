package com.challenge.discountcalculator.infrastructure.service;

import com.challenge.discountcalculator.domain.product.Product;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import com.challenge.discountcalculator.domain.valueobjects.NewProduct;
import com.challenge.discountcalculator.infrastructure.api.dto.ProductCreationRequest;
import com.challenge.discountcalculator.infrastructure.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    public Product createProduct(ProductCreationRequest productCreationRequest) {
        return productRepository.create(new NewProduct(productCreationRequest.name(), productCreationRequest.unitPrice()));
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
}
