package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.confignations.SystemBe;
import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.hoadon.*;
import com.example.thanh_toan_asm.entitys.HoaDon;
import com.example.thanh_toan_asm.entitys.HoadonToProduct;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.repositorys.HoaDonRepository;
import com.example.thanh_toan_asm.repositorys.HoaDonToProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HoaDonService {

    Boolean success  = null;
    String message = "";
    int statusCode = HttpStatus.OK.value();

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonToProductRepository hoaDonToProductRepository;

    public ResponseEntity<BaseResponse<ResponseHdCreated>> createHoaDon(HoaDonRq hoaDonRq) {
        ResponseHdCreated responseHdCreated = new ResponseHdCreated();
       try {
           if (hoaDonRq.getProductId().isEmpty()) {
               success = false;
               message = "Không tìm thấy sản phẩm trong hoá đơn đẩy lên";
               return new ResponseEntity<>(
                       new BaseResponse(success, message,null) , HttpStatusCode.valueOf(statusCode));
           }else {
               HoaDon hoaDon = HoaDon.builder()
                       .createAt(LocalDateTime.now())
                       .note(hoaDonRq.getNote())
                       .phone(hoaDonRq.getPhone())
                       .address(hoaDonRq.getAddressReceive())
                       .email(hoaDonRq.getEmail())
                       .status(hoaDonRq.getStatus())
                       .updateAt(LocalDateTime.now())
                       .fullName(hoaDonRq.getFull_name())
                       .totalPrice(String.valueOf(hoaDonRq.getTotal_price()))
                       .code(hoaDonRq.getCode())
                       .qty(hoaDonRq.getQty())
                       .build();
              HoaDon hoadonnew = hoaDonRepository.save(hoaDon);
               for (Long item : hoaDonRq.getProductId()) {
                   HoadonToProduct hoadonToProduct = HoadonToProduct.builder()
                           .createdAt(LocalDateTime.now())
                           .updatedAt(LocalDateTime.now())
                           .hoadon(hoadonnew)
                           .product(new Product(item))
                           .build();
                   hoaDonToProductRepository.save(hoadonToProduct);
               }
               responseHdCreated = ResponseHdCreated.builder()
                       .idHd(hoadonnew.getId())
                       .status(hoadonnew.getStatus())
                       .build();
               success = true;
               message = "Thêm mới hoá đơn thành công";
           }


       }catch (Exception ex){
           success = false;
           message = "Lỗi tạo mới hoá đơn";
           ex.printStackTrace();
       }

       return new ResponseEntity<>(
                new BaseResponse(success, message,responseHdCreated) , HttpStatusCode.valueOf(statusCode));
    }

    public HoaDon getHoaDon(String hoaDonRq) {
        return hoaDonRepository.getByCode(hoaDonRq);
     }

    public Page<AdminCustomHoadonDto> getAllHoaDon(LocalDateTime startDate, LocalDateTime endDate,String codeHd, String statusHd, Pageable pageable) {
      return  hoaDonRepository.findAllCreatedAt(startDate, endDate, codeHd, statusHd, pageable);
    }

    public ResponseEntity<BaseResponse<OrederConvert>> updateHoadon(HoaDonUpdate hoaDonUpdate) {

        if (hoaDonUpdate == null) {
            success = false;
            message = "Không tìm thấy thông tin";
            return new ResponseEntity<>(new BaseResponse<>(
                    success, message, null
            ), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        try{
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonUpdate.getId()).get();
            BeanUtils.copyProperties(hoaDonUpdate, hoaDon, SystemBe.getNullPropertyNames(hoaDonUpdate));
            hoaDon.setUpdateAt(LocalDateTime.now());
            hoaDonRepository.save(hoaDon);
            success = true;
            message = "Đơn hàng thay đổi trạng thái thành công";
        }catch (Exception e) {
            success = false;
            message = "Đơn hàng thay đổi trạng thái không thành công";
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponse<>(
                success, message, null
        ), HttpStatusCode.valueOf(HttpStatus.OK.value()));

    }

    public AdminCustomHoaDonDetailDto getHoaDonDetail(Long idHd) {

        return hoaDonRepository.findByCustomDetail(idHd);

    }

    public List<AdminCustomHoadonDto> getProductHoaDonDetail(Long idHd) {
        return hoaDonRepository.findByCustomDetailProduct(idHd);
    }

    public ResponseEntity<BaseResponse<OrederConvert>> deleteHoadon(Long id) {
        if(id == 0L){
            success = false;
            message = "Không tìm thấy Id đơn hàng";
            return new ResponseEntity<>(new BaseResponse<>(
                    success, message, null
            ), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        try {
            System.out.println("idDd" + id);
            hoaDonRepository.customDeleteHdToProduct(id);
            hoaDonRepository.customDeleteHd(id);
            success = true;
            message = "Xóa thành công";
        }catch (Exception e) {
            success = false;
            message = "Xóa không thành công";
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponse<>(
                success, message, null
        ), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
