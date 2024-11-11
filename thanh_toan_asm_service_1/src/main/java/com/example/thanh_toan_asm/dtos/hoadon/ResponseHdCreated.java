package com.example.thanh_toan_asm.dtos.hoadon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHdCreated {

    private Long idHd;
    private String status;

}
