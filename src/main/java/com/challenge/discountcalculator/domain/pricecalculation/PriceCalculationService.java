package com.challenge.discountcalculator.domain.pricecalculation;

import com.challenge.discountcalculator.domain.discount.DiscountPolicy;
import com.challenge.discountcalculator.domain.discount.DiscountPolicyRepository;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import com.challenge.discountcalculator.infrastructure.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Comparator;

public class PriceCalculationService {
    private final ProductRepository productRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    public PriceCalculationService(ProductRepository productRepository, DiscountPolicyRepository discountPolicyRepository) {
        this.productRepository = productRepository;
        this.discountPolicyRepository = discountPolicyRepository;
    }

    public BigDecimal calculatePriceWithDiscount(String productId, int amount) {
        var product = productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
        var discounts = discountPolicyRepository.findPoliciesForProduct(productId);
        discounts.sort(Comparator.comparing(DiscountPolicy::getDiscountValue).reversed());
        BigDecimal originalPrice = product.unitPrice().multiply(BigDecimal.valueOf(amount));
        return discounts
                .stream()
                .filter(it -> it.isApplicableFor(product, amount)).findFirst()
                .map(it -> it.applyDiscount(originalPrice))
                .orElse(originalPrice);
    }
}
