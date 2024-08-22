package com.challenge.discountcalculator.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record ProductDto(String id, String name, @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal unitPrice) {
}
