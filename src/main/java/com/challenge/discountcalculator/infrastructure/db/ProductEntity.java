package com.challenge.discountcalculator.infrastructure.db;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity {
    protected ProductEntity() {

    }

    public ProductEntity(String id, String name, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        return id != null && id.equals(((ProductEntity) obj).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
