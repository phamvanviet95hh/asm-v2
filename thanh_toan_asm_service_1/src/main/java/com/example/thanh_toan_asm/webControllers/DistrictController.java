package com.example.thanh_toan_asm.webControllers;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.address.ResponseDistrict;
import com.example.thanh_toan_asm.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/district/")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("getDistrict")
    public ResponseEntity<BaseResponseList<ResponseDistrict>> getDistrict(@RequestParam("provincesId") String provincesId){
        return districtService.getDistrict(provincesId);
    }

}
