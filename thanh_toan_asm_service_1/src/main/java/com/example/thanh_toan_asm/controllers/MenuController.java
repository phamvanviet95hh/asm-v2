package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.menu.MenuCreationRequest;
import com.example.thanh_toan_asm.dtos.menu.MenuUpdateRequest;
import com.example.thanh_toan_asm.dtos.request.ProductCreationRequest;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Menu;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.services.MenuService;
import com.example.thanh_toan_asm.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping(value = "/create/menu")
    public ResponseEntity<BaseResponse<Menu>> createMenu(@RequestBody MenuCreationRequest request) throws IOException {
        return menuService.createMenu(request);
    }

    @GetMapping(value = "/get/menu")
    public ResponseEntity<BaseResponse<Menu>> getMenu(@RequestParam("id") Long id) {

        return menuService.getMenu(id);
    }

    @PostMapping(value = "/update/menu")
    public ResponseEntity<BaseResponse<Menu>> updateMenu(@RequestBody MenuUpdateRequest request) {

        return menuService.updateMenu(request);
    }

    @PostMapping(value = "/delete/menu")
    public ResponseEntity<BaseResponse<Menu>> deleteMenu(@RequestParam("id") Long id){

        return menuService.deleteMenu(id);
    }
}
