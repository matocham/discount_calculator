package com.challenge.discountcalculator.domain.discount;

import java.math.BigDecimal;

public final class AbsoluteDiscountPolicy extends DiscountPolicy{

    public AbsoluteDiscountPolicy(Long id, String applicableProductId, int minimumProductAmount, BigDecimal discountValue) {
        super(id, applicableProductId, minimumProductAmount, discountValue);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.subtract(getDiscountValue());
    }
}
