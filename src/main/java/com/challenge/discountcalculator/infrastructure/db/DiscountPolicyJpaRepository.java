package com.challenge.discountcalculator.infrastructure.db;

import com.challenge.discountcalculator.domain.discount.DiscountPolicy;
import com.challenge.discountcalculator.domain.discount.DiscountPolicyRepository;
import com.challenge.discountcalculator.domain.valueobjects.NewDiscountPolicy;
import com.challenge.discountcalculator.domain.valueobjects.UpdateDiscountPolicy;
import com.challenge.discountcalculator.infrastructure.mapper.DiscountPolicyDomainMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiscountPolicyJpaRepository implements DiscountPolicyRepository {
    private final EntityManager entityManager;
    private final DiscountPolicyDomainMapper mapper;

    public DiscountPolicyJpaRepository(EntityManager entityManager, DiscountPolicyDomainMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public DiscountPolicy create(NewDiscountPolicy discountPolicy) {
        var policy = new DiscountPolicyEntity(entityManager.getReference(ProductEntity.class, discountPolicy.applicableProductId()), discountPolicy.discountType(), discountPolicy.discountValue(), discountPolicy.minimumProductAmount());
        entityManager.persist(policy);
        return mapper.mapToDomain(policy);
    }

    @Override
    @Transactional
    public DiscountPolicy update(UpdateDiscountPolicy discountPolicy) {
        var existingPolicy = entityManager.find(DiscountPolicyEntity.class, discountPolicy.id());
        existingPolicy.setDiscountValue(discountPolicy.discountValue());
        existingPolicy.setMinimumProductAmount(discountPolicy.minimumProductAmount());
        existingPolicy.setProduct(entityManager.getReference(ProductEntity.class, discountPolicy.applicableProductId()));
        return mapper.mapToDomain(existingPolicy);
    }

    @Override
    public Optional<DiscountPolicy> findById(Long id) {
        return Optional.ofNullable(entityManager.find(DiscountPolicyEntity.class, id)).map(mapper::mapToDomain);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(entityManager.getReference(DiscountPolicyEntity.class, id));
    }

    @Override
    public List<DiscountPolicy> findPoliciesForProduct(String productId) {
        List<DiscountPolicyEntity> policiesForProduct = entityManager
                .createQuery("select p from DiscountPolicyEntity p where p.productId = :pid", DiscountPolicyEntity.class)
                .setParameter("pid", productId)
                .getResultList();
        return policiesForProduct.stream().map(mapper::mapToDomain).collect(Collectors.toList());
    }
}
