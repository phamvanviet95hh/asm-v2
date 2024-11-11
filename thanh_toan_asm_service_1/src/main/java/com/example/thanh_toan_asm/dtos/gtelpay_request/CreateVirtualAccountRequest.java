package com.example.thanh_toan_asm.dtos.gtelpay_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVirtualAccountRequest {
    private String accountName;
    private String mapId;
    private String mapType;
    private String accountType;
    private String bankCode;
    private Long maxAmount;
    private Long minAmount;
    private Long equalAmount;
    private String description;
}
