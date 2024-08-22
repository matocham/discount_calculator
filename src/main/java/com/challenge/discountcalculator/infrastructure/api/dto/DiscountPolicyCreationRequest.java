package com.challenge.discountcalculator.infrastructure.api.dto;

import com.challenge.discountcalculator.infrastructure.db.DiscountPolicyEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record DiscountPolicyCreationRequest(@NotNull @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$") String applicableProductId,
                                            @Min(1) int minimumAmount,
                                            @NotNull DiscountPolicyEntity.DiscountType discountType,
                                            @NotNull @Min(1) BigDecimal discountValue) {
}
