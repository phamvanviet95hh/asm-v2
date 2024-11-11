package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.address.ResponseProvince;
import com.example.thanh_toan_asm.entitys.Provinces;
import com.example.thanh_toan_asm.repositorys.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceService {

    @Autowired
    private ProvincesRepository provincesRepository;

    Boolean success;
    String message;
    public ResponseEntity<BaseResponseList<ResponseProvince>> getListProvince() {
        success = true;
        message = "Get data Success";
        List<Provinces> responseProvince = provincesRepository.findAll();
        List<ResponseProvince> responseProvinces = new ArrayList<>();
        for(Provinces item : responseProvince){
            responseProvinces.add(item.getVo());
        }



       return  new ResponseEntity<>(new BaseResponseList<>(success, message, responseProvinces), HttpStatusCode.valueOf(HttpStatus.OK.value()));

    }
}
