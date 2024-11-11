package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.admins.products.RequestProductCategoryDto;
import com.example.thanh_toan_asm.dtos.admins.products.ResponProductGloableDto;
import com.example.thanh_toan_asm.dtos.product.ProductCategoryRq;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.services.ProductCategoryService;
import com.example.thanh_toan_asm.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("admin/")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productService;

    @PostMapping("addCategoryProduct")
    public ResponseEntity<ResponProductGloableDto> addPostCategoryProduct(
            @RequestParam("data") String data,
            @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        return productService.addProductCategory(data , file);
    }
    @DeleteMapping("deleteCategoryProduct")
    public ResponseEntity<ResponProductGloableDto> deleteCategoryProduct(
            @RequestParam("id") String id) throws JsonProcessingException {
        return productService.deleteCategoryProduct(id);
    }

    @PostMapping(value = "/update/productCategory")
    public ResponseEntity<BaseResponse<ProductCategory>> updateProductCategory(@RequestBody ProductCategoryRq request) {
        if(request.getId() == null || request.getId() == 0){
            return new ResponseEntity<>(new BaseResponse(false, "Id không được để trống", null), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        return productService.updateProductCategory(request);
    }

    @PostMapping(value = "/upload/imageProductCategory")
    public ResponseEntity<BaseResponse<Product>> imageProductCategory(@RequestParam("productCategoryId") Long productId, @RequestParam("file") MultipartFile file) throws IOException {
        return productService.uploadImageProductCaategory(productId, file);
    }


}
