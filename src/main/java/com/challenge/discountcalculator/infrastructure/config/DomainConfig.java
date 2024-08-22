package com.challenge.discountcalculator.infrastructure.config;

import com.challenge.discountcalculator.domain.discount.DiscountPolicyRepository;
import com.challenge.discountcalculator.domain.pricecalculation.PriceCalculationService;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public PriceCalculationService priceCalculationService(ProductRepository productRepository,
                                                           DiscountPolicyRepository discountPolicyRepository) {
        return new PriceCalculationService(productRepository, discountPolicyRepository);
    }
}
