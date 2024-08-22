package com.challenge.discountcalculator.infrastructure.mapper;

import com.challenge.discountcalculator.domain.discount.AbsoluteDiscountPolicy;
import com.challenge.discountcalculator.domain.discount.DiscountPolicy;
import com.challenge.discountcalculator.domain.discount.PercentageDiscountPolicy;
import com.challenge.discountcalculator.infrastructure.api.dto.DiscountPolicyDto;
import com.challenge.discountcalculator.infrastructure.db.DiscountPolicyEntity;
import org.springframework.stereotype.Service;

@Service
public class DiscountPolicyDomainMapper {
    public DiscountPolicyDto mapToDto(DiscountPolicy result) {
        return switch (result) {
            case PercentageDiscountPolicy pdp ->
                    new DiscountPolicyDto(pdp.getId(), pdp.getMinimumProductAmount(), pdp.getApplicableProductId(), DiscountPolicyEntity.DiscountType.PERCENTAGE.name(), pdp.getDiscountValue());
            case AbsoluteDiscountPolicy adp ->
                    new DiscountPolicyDto(adp.getId(), adp.getMinimumProductAmount(), adp.getApplicableProductId(), DiscountPolicyEntity.DiscountType.ABSOLUTE.name(), adp.getDiscountValue());
        };
    }

    public DiscountPolicy mapToDomain(DiscountPolicyEntity discountPolicy) {
        if(discountPolicy.getDiscountType() == DiscountPolicyEntity.DiscountType.ABSOLUTE) {
            return new AbsoluteDiscountPolicy(discountPolicy.getId(), discountPolicy.getProductId(), discountPolicy.getMinimumProductAmount(), discountPolicy.getDiscountValue());
        } else {
            return new PercentageDiscountPolicy(discountPolicy.getId(), discountPolicy.getProductId(), discountPolicy.getMinimumProductAmount(), discountPolicy.getDiscountValue());
        }
    }
}
