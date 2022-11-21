package com.orderms.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "fk_order_id", columnList = "fk_order_id"),
        @Index(name = "fk_product_sku_code_idx", columnList = "fk_product_sku_code")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "fk_product_sku_code")
    private Product product;

    @Column(nullable = false, length = 15)
    private Integer quantity;

    public OrderLine() {
    }
}
