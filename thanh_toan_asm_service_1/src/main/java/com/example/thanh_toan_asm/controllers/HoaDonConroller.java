package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.hoadon.HoaDonRq;
import com.example.thanh_toan_asm.dtos.hoadon.HoaDonUpdate;
import com.example.thanh_toan_asm.dtos.hoadon.ResponseHdCreated;
import com.example.thanh_toan_asm.dtos.request.OrderCreationRequest;
import com.example.thanh_toan_asm.repositorys.HoaDonRepository;
import com.example.thanh_toan_asm.services.HoaDonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class HoaDonConroller {

    @Autowired
    private HoaDonService hoaDonService;

    @PostMapping(value = "/create/hoadon")
    public ResponseEntity<BaseResponse<ResponseHdCreated>> createHoadon(@RequestBody HoaDonRq hoaDonRq) {
        return hoaDonService.createHoaDon(hoaDonRq);
    }

    @PostMapping(value = "/create/updateHoadon")
    public ResponseEntity<BaseResponse<OrederConvert>> updateHoadon(@RequestBody HoaDonUpdate hoaDonUpdate) {
        return hoaDonService.updateHoadon(hoaDonUpdate);
    }

    @DeleteMapping(value = "/hoadon/delete")
    public ResponseEntity<BaseResponse<OrederConvert>> deleteHoadon(@RequestParam("id") String id) {
        Long idHd = Long.parseLong(id);
        return hoaDonService.deleteHoadon(idHd);
    }


}
