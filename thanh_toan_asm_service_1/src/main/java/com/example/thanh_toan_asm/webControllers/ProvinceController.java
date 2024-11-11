package com.example.thanh_toan_asm.webControllers;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.address.ResponseProvince;
import com.example.thanh_toan_asm.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/provinces/")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("get/province")
    public ResponseEntity<BaseResponseList<ResponseProvince>> getListProvince() {
        return provinceService.getListProvince();
    }
}
