package com.challenge.discountcalculator.infrastructure.api;

import com.challenge.discountcalculator.infrastructure.api.dto.ProductCreationRequest;
import com.challenge.discountcalculator.infrastructure.api.dto.ProductDto;
import com.challenge.discountcalculator.infrastructure.mapper.ProductDomainMapper;
import com.challenge.discountcalculator.infrastructure.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDomainMapper productDomainMapper;

    public ProductController(ProductService productService, ProductDomainMapper productDomainMapper) {
        this.productService = productService;
        this.productDomainMapper = productDomainMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductCreationRequest productCreationRequest) {
        return productDomainMapper.mapToDto(productService.createProduct(productCreationRequest));
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") String id) {
        return productDomainMapper.mapToDto(productService.getProduct(id));
    }
}
