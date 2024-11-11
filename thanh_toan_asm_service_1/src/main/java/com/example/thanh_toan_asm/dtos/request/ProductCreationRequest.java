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
public class ProductCreationRequest {
    private String productName;
    private String productDescription;
    private Long productPrice;
    private String status;
    private String link;
    private Long userId;
    private Long productCategoryId;
}
