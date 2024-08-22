package com.challenge.discountcalculator.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record PriceCalculationResponse(String productId, int amount, @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal price) {
}
