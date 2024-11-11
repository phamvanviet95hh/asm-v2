package com.example.thanh_toan_asm.dtos.hoadon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCustomHoadonDto {

    private Long idBill;
    private String addressBill;
    private String codeBill;
    private String emailBill;
    private String phoneBill;
    private String namePersionBill;
    private String noteBill;
    private String statusBill;
    private String totalPriceBill;
    private String qtyBill;
    private String nameProduct;
    private String imgProduct;
    private LocalDateTime timeCreatedAt;


}
