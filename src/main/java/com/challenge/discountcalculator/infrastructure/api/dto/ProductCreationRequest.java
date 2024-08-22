package com.challenge.discountcalculator.infrastructure.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreationRequest(@NotNull String name, @NotNull @Min(1) BigDecimal unitPrice) {
}
