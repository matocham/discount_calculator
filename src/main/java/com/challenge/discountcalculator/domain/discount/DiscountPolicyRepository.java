package com.challenge.discountcalculator.domain.discount;

import com.challenge.discountcalculator.domain.valueobjects.NewDiscountPolicy;
import com.challenge.discountcalculator.domain.valueobjects.UpdateDiscountPolicy;

import java.util.List;
import java.util.Optional;

public interface DiscountPolicyRepository {
    DiscountPolicy create(NewDiscountPolicy discountPolicy);
    DiscountPolicy update(UpdateDiscountPolicy discountPolicy);
    Optional<DiscountPolicy> findById(Long id);
    void deleteById(Long id);
    List<DiscountPolicy> findPoliciesForProduct(String productId);
}
