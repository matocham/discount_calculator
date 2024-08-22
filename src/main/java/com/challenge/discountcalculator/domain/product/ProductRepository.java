package com.challenge.discountcalculator.domain.product;

import com.challenge.discountcalculator.domain.valueobjects.NewProduct;

import java.util.Optional;

public interface ProductRepository {
    Product create(NewProduct product);
    Optional<Product> findById(String id);
}
