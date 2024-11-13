package com.example.thanh_toan_asm.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GloableResponse<T> {
    private Boolean success;
    private String message;
    private T data;
}
