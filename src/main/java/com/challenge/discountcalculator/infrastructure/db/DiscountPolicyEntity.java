package com.challenge.discountcalculator.infrastructure.db;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@Entity
@Table(name = "discount_policies")
public class DiscountPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "product_id", updatable = false, insertable = false)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private int minimumProductAmount;

    protected DiscountPolicyEntity() {
    }

    public DiscountPolicyEntity(ProductEntity product, DiscountType discountType, BigDecimal discountValue, int minimumProductAmount) {
        this.product = product;
        this.productId = product.getId();
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minimumProductAmount = minimumProductAmount;
    }

    public enum DiscountType {
        PERCENTAGE,
        ABSOLUTE
    }

    public Long getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public int getMinimumProductAmount() {
        return minimumProductAmount;
    }

    public String getProductId() {
        return productId;
    }

    void setProduct(ProductEntity product) {
        this.product = product;
    }

    void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    void setMinimumProductAmount(int minimumProductAmount) {
        this.minimumProductAmount = minimumProductAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        return id != null && id.equals(((DiscountPolicyEntity) obj).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
