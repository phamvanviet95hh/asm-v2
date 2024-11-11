package com.example.thanh_toan_asm.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

}
