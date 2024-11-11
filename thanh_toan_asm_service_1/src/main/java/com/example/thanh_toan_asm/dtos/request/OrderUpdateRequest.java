package com.example.thanh_toan_asm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUpdateRequest {
    private Long qty;
    private Long orderId;
    private String status;
}
