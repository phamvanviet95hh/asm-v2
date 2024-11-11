package com.example.thanh_toan_asm.dtos.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckOrderDto {

    private String status;
    private String nameProduct;
    private Long userId;
    private Long productId;

}
