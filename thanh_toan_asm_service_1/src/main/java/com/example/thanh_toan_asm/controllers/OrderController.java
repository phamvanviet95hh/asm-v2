package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.orders.CheckOrderDto;
import com.example.thanh_toan_asm.dtos.request.OrderCreationRequest;
import com.example.thanh_toan_asm.dtos.request.OrderUpdateRequest;
import com.example.thanh_toan_asm.dtos.request.ProductCreationRequest;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Order;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/asm/v1")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value = "/create/order")
    public ResponseEntity<BaseResponse<OrederConvert>> createOrder(@RequestBody OrderCreationRequest request) {

        return orderService.createOrder(request);
    }

    @PostMapping(value = "/checkOrder")
    public ResponseEntity<BaseResponse<OrederConvert>> checkOrder(@RequestBody CheckOrderDto checkOrderDto) {
        return orderService.checkOrder(checkOrderDto);
    }

    @GetMapping(value = "/get/order")
    public ResponseEntity<BaseResponse<String>> getOrder(@RequestParam("id") Long id) {

        return orderService.getOrder(id);
    }

    @PostMapping(value = "/update/order")
    public ResponseEntity<BaseResponse<Order>> updateOrder(@RequestBody OrderUpdateRequest request) {

        return orderService.updateOrder(request);
    }

    @PostMapping(value = "/delete/order")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@RequestParam("id") Long id){

        return orderService.deleteOrder(id);
    }
}
