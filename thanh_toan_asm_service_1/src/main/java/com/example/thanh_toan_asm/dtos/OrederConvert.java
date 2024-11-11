package com.example.thanh_toan_asm.dtos;

import com.example.thanh_toan_asm.entitys.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrederConvert {

    private Long id;

    private String address;
    private String status;
    private String code;
    private String nameProduct;
    private String typeOfTransport;
    private int qty;
    private Long    price;
    private String infoTransactions;
    private String link;
    private Long userEntity;
    private Long product;
}
