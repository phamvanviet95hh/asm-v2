package com.example.thanh_toan_asm.dtos.hoadon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonRq {

    private String address;
    private String addressReceive;
    private String code;
    private String email;
    private String full_name;
    private String note;
    private String phone;
    private String status;
    private Long total_price;
    private String qty;
    private ArrayList<Long> productId;

}
