package com.challenge.discountcalculator.infrastructure.mapper;

import com.challenge.discountcalculator.domain.product.Product;
import com.challenge.discountcalculator.infrastructure.api.dto.ProductDto;
import com.challenge.discountcalculator.infrastructure.db.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductDomainMapper {
    public Product mapToDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getUnitPrice());
    }

    public ProductDto mapToDto(Product product) {
        return new ProductDto(product.id(), product.name(), product.unitPrice());
    }

}
