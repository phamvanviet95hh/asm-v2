package com.example.thanh_toan_asm.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponseList<T> {
    private Boolean success;
    private String message;
    private List<T> data;
}
