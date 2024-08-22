package com.challenge.discountcalculator.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record DiscountPolicyDto(Long id, int minimumProductAmount, String applicableProductId, String discountType, @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal discountValue) {
}
