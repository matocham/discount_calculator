package com.challenge.discountcalculator.infrastructure.db;

import com.challenge.discountcalculator.domain.product.Product;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import com.challenge.discountcalculator.domain.valueobjects.NewProduct;
import com.challenge.discountcalculator.infrastructure.mapper.ProductDomainMapper;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ProductJpaRepository implements ProductRepository {
    private final EntityManager entityManager;
    private final ProductDomainMapper mapper;

    public ProductJpaRepository(EntityManager entityManager, ProductDomainMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public Product create(NewProduct product) {
        var entity = new ProductEntity(UUID.randomUUID().toString(), product.name(), product.unitPrice());
        entityManager.persist(entity);
        return mapper.mapToDomain(entity);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(entityManager.find(ProductEntity.class, id)).map(mapper::mapToDomain);
    }
}
