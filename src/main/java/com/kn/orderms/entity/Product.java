package com.kn.orderms.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Product {

    @NotBlank
    @Id
    @Column(name = "sku_code", nullable = false, unique = true, length = 32)
    private String skuCode;

    @Column(nullable = false, length = 200)
    private String name;

    @Positive
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    public Product() {
    }
}
