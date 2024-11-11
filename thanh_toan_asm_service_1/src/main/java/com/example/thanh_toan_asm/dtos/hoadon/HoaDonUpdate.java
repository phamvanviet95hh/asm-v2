package com.example.thanh_toan_asm.dtos.hoadon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonUpdate {
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

}
