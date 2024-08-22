package com.challenge.discountcalculator.domain.product;

import java.math.BigDecimal;

public record Product(String id, String name, BigDecimal unitPrice){}
