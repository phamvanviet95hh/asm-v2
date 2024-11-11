package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.address.ResponseDistrict;
import com.example.thanh_toan_asm.entitys.Districts;
import com.example.thanh_toan_asm.entitys.Provinces;
import com.example.thanh_toan_asm.repositorys.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictService {

    Boolean success;
    String message;

    @Autowired
    private DistrictRepository districtRepository;

    public ResponseEntity<BaseResponseList<ResponseDistrict>> getDistrict(String provincesId) {
        success = true;
        message = "Get Success";
        List<Districts> districts = districtRepository.findByProvinces(new Provinces(provincesId));
        List<ResponseDistrict> responseDistricts = new ArrayList<>();
        for (Districts item : districts){
            responseDistricts.add(item.getVo());
        }

        return new ResponseEntity<>(
                new BaseResponseList<>(success, message, responseDistricts), HttpStatusCode.valueOf(HttpStatus.OK.value())
        );
    }
}
