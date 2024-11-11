package com.example.thanh_toan_asm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreationRequest {
    private String address;
    private String status;
    private String code;
    private String nameProduct;
    private int qty;
    private String typeOfTransport;
    private Long price;
    private String infoTransactions;
    private Long user_id;
    private Long product_id;
}
