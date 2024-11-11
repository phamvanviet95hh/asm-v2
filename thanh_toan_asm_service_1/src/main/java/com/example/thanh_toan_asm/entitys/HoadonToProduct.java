package com.example.thanh_toan_asm.entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "hoadon_to_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoadonToProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hoadon_id", nullable = false)
    private HoaDon hoadon;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
