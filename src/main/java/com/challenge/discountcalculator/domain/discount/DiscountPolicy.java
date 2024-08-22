package com.challenge.discountcalculator.domain.discount;

import com.challenge.discountcalculator.domain.product.Product;

import java.math.BigDecimal;

public sealed abstract class DiscountPolicy permits AbsoluteDiscountPolicy, PercentageDiscountPolicy {
    private final Long id;
    private final String applicableProductId;
    private final int minimumProductAmount;
    private final BigDecimal discountValue;

    public DiscountPolicy(Long id, String applicableProductId, int minimumProductAmount, BigDecimal discountValue) {
        this.id = id;
        this.applicableProductId = applicableProductId;
        this.minimumProductAmount = minimumProductAmount;
        this.discountValue = discountValue;
    }

    public Long getId() {
        return id;
    }

    public String getApplicableProductId() {
        return applicableProductId;
    }

    public int getMinimumProductAmount() {
        return minimumProductAmount;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public boolean isApplicableFor(Product product, int amount) {
        return amount >= minimumProductAmount && applicableProductId.equals(product.id());
    }

    public abstract BigDecimal applyDiscount(BigDecimal price);
}
