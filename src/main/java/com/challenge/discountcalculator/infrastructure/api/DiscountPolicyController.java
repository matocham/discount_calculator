package com.challenge.discountcalculator.infrastructure.api;

import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyCreationRequest;
import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyDto;
import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyUpdateRequest;
import com.challenge.discountcalculator.infrastructure.mapper.DiscountPolicyDomainMapper;
import com.challenge.discountcalculator.infrastructure.service.DiscountPolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount_policies")
public class DiscountPolicyController {
    private final DiscountPolicyService discountPolicyService;
    private final DiscountPolicyDomainMapper discountPolicyDomainMapper;

    public DiscountPolicyController(DiscountPolicyService discountPolicyService, DiscountPolicyDomainMapper discountPolicyDomainMapper) {
        this.discountPolicyService = discountPolicyService;
        this.discountPolicyDomainMapper = discountPolicyDomainMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountPolicyDto createPolicy(@RequestBody @Valid DiscountPolicyCreationRequest request){
        var result = discountPolicyService.createPolicy(request);
        return discountPolicyDomainMapper.mapToDto(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePolicy(@PathVariable("id") Long id){
        discountPolicyService.deletePolicy(id);
    }

    @PutMapping("/{id}")
    public DiscountPolicyDto updatePolicy(@PathVariable("id") Long id, @RequestBody @Valid DiscountPolicyUpdateRequest request){
        return discountPolicyDomainMapper.mapToDto(discountPolicyService.updatePolicy(id, request));
    }

    @GetMapping("/{id}")
    public DiscountPolicyDto getPolicy(@PathVariable("id") Long id){
        return discountPolicyDomainMapper.mapToDto(discountPolicyService.getDiscountPolicy(id));
    }
}
