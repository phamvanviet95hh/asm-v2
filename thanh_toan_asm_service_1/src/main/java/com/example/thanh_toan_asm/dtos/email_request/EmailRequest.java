package com.example.thanh_toan_asm.dtos.email_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
    private String customerEmail;
    private String ccEmail;
    private String orderCode;
    private String dateOfPurchase;
    private String product;
    private String totalMoney;
    private String address;
    private String status;
    private String nameKh;
    private String phoneKh;
}
