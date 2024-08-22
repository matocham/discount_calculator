package com.challenge.discountcalculator.domain.valueobjects;

import java.math.BigDecimal;

public record UpdateDiscountPolicy(Long id, String applicableProductId, int minimumProductAmount, BigDecimal discountValue) {
}
