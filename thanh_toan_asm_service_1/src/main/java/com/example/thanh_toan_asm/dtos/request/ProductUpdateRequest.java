package com.example.thanh_toan_asm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
    private String productName;
    private String productDescription;
    private Long productPrice;
    private Long productOldPrice;
    private Long productSalePrice;
    private String productImage;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    private String link;
    private Long productId;
    private String productCategory;
}
