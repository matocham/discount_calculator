package com.challenge.discountcalculator.domain.valueobjects;

import com.challenge.discountcalculator.infrastructure.db.DiscountPolicyEntity;

import java.math.BigDecimal;

public record NewDiscountPolicy(String applicableProductId, int minimumProductAmount, BigDecimal discountValue, DiscountPolicyEntity.DiscountType discountType) {
}
