package com.challenge.discountcalculator.domain.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PercentageDiscountPolicy extends DiscountPolicy{
    public static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);

    public PercentageDiscountPolicy(Long id, String applicableProductId, int minimumProductAmount, BigDecimal discountValue) {
        super(id, applicableProductId, minimumProductAmount, discountValue);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        var valueAsPercent = getDiscountValue().divide(PERCENT_DIVISOR).setScale(2, RoundingMode.HALF_UP);
        return price.subtract(price.multiply(valueAsPercent)).setScale(2, RoundingMode.HALF_UP);
    }
}
