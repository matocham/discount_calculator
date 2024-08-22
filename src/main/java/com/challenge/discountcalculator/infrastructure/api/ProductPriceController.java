package com.challenge.discountcalculator.infrastructure.api;

import com.challenge.discountcalculator.domain.pricecalculation.PriceCalculationService;
import com.challenge.discountcalculator.infrastructure.api.dto.PriceCalculationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/price")
public class ProductPriceController {

    private final PriceCalculationService priceCalculationService;

    public ProductPriceController(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @GetMapping
    public PriceCalculationResponse calculateProductPrice(@Valid @NotNull @RequestParam("productId") String productId,
                                      @Valid @NotNull @RequestParam("amount") int amount) {
        var price = priceCalculationService.calculatePriceWithDiscount(productId, amount);
        return new PriceCalculationResponse(productId, amount, price);
    }
}
