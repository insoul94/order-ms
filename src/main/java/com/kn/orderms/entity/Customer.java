package com.kn.orderms.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "email_idx", columnList = "email")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Customer {

    @NotBlank
    @Id
    @Column(name = "reg_code", nullable = false, unique = true)
    private String regCode;

    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders;

    public Customer() {
    }
}
