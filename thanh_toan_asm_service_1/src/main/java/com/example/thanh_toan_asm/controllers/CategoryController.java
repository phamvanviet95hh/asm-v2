package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.request.CategoryCreationRequest;
import com.example.thanh_toan_asm.dtos.request.CategoryUpdateRequest;
import com.example.thanh_toan_asm.entitys.NewCategory;
import com.example.thanh_toan_asm.services.AsmService;
import com.example.thanh_toan_asm.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/search/category")
    public ResponseEntity<BaseResponseList<NewCategory>> searchCategory(@RequestParam("categoryName") String categoryName){
        return service.searchCategory(categoryName);
    }

    @GetMapping(value = "/get/category")
    public ResponseEntity<BaseResponse<NewCategory>> getCategory(@RequestParam("id") Long id){
        return service.getCategory(id);
    }

    @GetMapping(value = "/get/all/category")
    public ResponseEntity<BaseResponseList<NewCategory>> getAllCategory(){
        return service.getAllCategory();
    }

    @PostMapping(value = "/delete/category")
    public ResponseEntity<BaseResponseList<String>> deleteCategory(@RequestParam("id") Long id){
        return service.deleteCategory(id);
    }

    @PostMapping(value = "/edit/category")
    public ResponseEntity<BaseResponseList<String>> editCategory(@RequestParam("id")CategoryUpdateRequest categoryUpdateRequest, Long id){
        return service.editCategory(categoryUpdateRequest,id);
    }

    @PostMapping(value = "/add/category")
    public ResponseEntity<BaseResponseList<String>> addCategory(@RequestBody CategoryCreationRequest request){
        return service.addCategory(request);
    }
}
