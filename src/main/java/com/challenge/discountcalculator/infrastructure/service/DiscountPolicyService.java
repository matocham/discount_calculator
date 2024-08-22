package com.challenge.discountcalculator.infrastructure.service;

import com.challenge.discountcalculator.domain.discount.DiscountPolicy;
import com.challenge.discountcalculator.domain.discount.DiscountPolicyRepository;
import com.challenge.discountcalculator.domain.discount.PercentageDiscountPolicy;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import com.challenge.discountcalculator.domain.valueobjects.NewDiscountPolicy;
import com.challenge.discountcalculator.domain.valueobjects.UpdateDiscountPolicy;
import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyCreationRequest;
import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyUpdateRequest;
import com.challenge.discountcalculator.infrastructure.db.DiscountPolicyEntity;
import com.challenge.discountcalculator.infrastructure.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountPolicyService {
    private final DiscountPolicyRepository discountPolicyRepository;
    private final ProductRepository productRepository;

    public DiscountPolicyService(DiscountPolicyRepository discountPolicyRepository, ProductRepository productRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public DiscountPolicy createPolicy(DiscountPolicyCreationRequest request) {
        productRepository.findById(request.applicableProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (request.discountType() == DiscountPolicyEntity.DiscountType.PERCENTAGE && request.discountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage discount value cannot be greater than 100");
        }
        var policyToCreate = new NewDiscountPolicy(request.applicableProductId(), request.minimumAmount(), request.discountValue(), request.discountType());
        return discountPolicyRepository.create(policyToCreate);
    }

    public DiscountPolicy getDiscountPolicy(Long id) {
        return discountPolicyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public void deletePolicy(Long id) {
        discountPolicyRepository.deleteById(id);
    }

    @Transactional
    public DiscountPolicy updatePolicy(Long id, DiscountPolicyUpdateRequest request) {
        var existingPolicy = discountPolicyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (existingPolicy instanceof PercentageDiscountPolicy && request.discountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage discount value cannot be greater than 100");
        }
        productRepository.findById(request.applicableProductId()).orElseThrow(ResourceNotFoundException::new);

        return discountPolicyRepository.update(new UpdateDiscountPolicy(id, request.applicableProductId(), request.minimumAmount(), request.discountValue()));
    }
}
