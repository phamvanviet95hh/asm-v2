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
@Table(name = "hoadon")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String address;

    private String phone;

    private String email;

    private String note;

    private String status;
    private String qty;

    private String code;

    private String totalPrice;

    private String addressReceive;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "hoadon")
    private Set<HoadonToProduct> hoadonToProducts;


}
