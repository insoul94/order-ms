package com.kn.orderms.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
// Cannot use name 'order' for table as it is reserved word in SQL.
@Table(name = "order_table", indexes = {
        // Give all foreign key based indexes readable name instead of default generated one.
        @Index(name = "fk_customer_id_idx", columnList = "fk_customer_id"),
        @Index(name = "date_idx", columnList = "date")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderLine> orderLines;

    public Order() {
    }
}
