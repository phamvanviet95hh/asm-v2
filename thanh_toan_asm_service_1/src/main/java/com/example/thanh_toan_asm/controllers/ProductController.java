package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.admins.products.ResponProductGloableDto;
import com.example.thanh_toan_asm.dtos.product.UploadImageRequest;
import com.example.thanh_toan_asm.dtos.request.ProductCreationRequest;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping(value = "/create/product")
    public ResponseEntity<ResponProductGloableDto> createProduct(@RequestParam("data") String data,
                                                                 @RequestParam("file") MultipartFile file) throws IOException {
        return productService.createProduct(data, file);
    }

    @PostMapping(value = "/upload/image")
    public ResponseEntity<BaseResponse<Product>> uploadImageProduct(@RequestParam("productId") Long productId, @RequestParam("file") MultipartFile file) throws IOException {
        return productService.uploadImageProduct(productId, file);
    }

    @GetMapping(value = "/get/product")
    public Product getProduct(@RequestParam("id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping(value = "/update/product")
    public ResponseEntity<BaseResponse<Product>> updateProduct(@RequestBody ProductUpdateRequest request) {
        if(request.getProductId() == null || request.getProductId() == 0){
            return new ResponseEntity<>(new BaseResponse(false, "Id không được để trống", null), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        return productService.updateProduct(request);
    }

    @DeleteMapping(value = "/delete/product")
    public ResponseEntity<BaseResponse<Product>> deleteProduct(@RequestParam("id") Long id){
        System.out.println("id :" +id);
        return productService.deleteProduct(id);
    }
}
