package com.example.thanh_toan_asm.webControllers;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.address.ResponseProvince;
import com.example.thanh_toan_asm.dtos.address.ResponseWard;
import com.example.thanh_toan_asm.services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/ward/")
public class WardController {

    @Autowired
    private WardService wardService;

    @GetMapping("getList")
    public ResponseEntity<BaseResponseList<ResponseWard>> getList(@RequestParam("districtId") String districtId) {
        return wardService.getListWard(districtId);
    }

}
