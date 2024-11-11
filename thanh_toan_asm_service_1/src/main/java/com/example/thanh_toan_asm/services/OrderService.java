package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.orders.CheckOrderDto;
import com.example.thanh_toan_asm.dtos.request.OrderCreationRequest;
import com.example.thanh_toan_asm.dtos.request.OrderUpdateRequest;
import com.example.thanh_toan_asm.entitys.Order;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.repositorys.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    private Boolean success;
    private String message;
    int statusCode = 0;

    @Autowired
    OrderRepository orderRepository;

    public ResponseEntity<BaseResponse<OrederConvert>> createOrder(OrderCreationRequest request){
        OrederConvert orederConvert = null;
        statusCode = HttpStatus.OK.value();
        Order order=new Order();
        try {
            order.setAddress(request.getAddress());
            order.setStatus(request.getStatus());
            order.setCode(request.getCode());
            order.setTypeOfTransport(request.getTypeOfTransport());
            order.setPrice(request.getPrice());
            order.setInfoTransactions(request.getInfoTransactions());
            order.setQty(request.getQty());
            order.setUserEntity( new UserUntity(request.getUser_id()));
            order.setProduct(new Product(request.getProduct_id()));
            order.setNameProduct(request.getNameProduct());
            order.setTotalPrice(request.getQty() * request.getPrice());
            orderRepository.save(order);
            orederConvert = order.getVo();
            success = true;
            message = "createOrder success!!!";
        }catch (Exception e){
            orederConvert = null;
            success = false;
            message = "createOrder Fail!!!";
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,orederConvert) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> getOrder(Long id){
        Order order=null;
        try {
            order=orderRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            success = true;
            message = "getOrder success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "getOrder Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,order) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<Order>> updateOrder(OrderUpdateRequest request){
        Order newOrder=orderRepository.findById(request.getOrderId()).orElseThrow(()->new RuntimeException("No order in table"));
        if(request.getStatus() != null){

        }else {
            Long price = 0L;
            Long qty = 0L;

            qty = newOrder.getQty() + request.getQty();
            System.out.println(qty);
            price = newOrder.getPrice() * qty;
            try {
                newOrder.setQty(Math.toIntExact(qty));
                newOrder.setTotalPrice(price);
                orderRepository.save(newOrder);
                success = true;
                message = "updateOrder success!!!";
                statusCode = HttpStatus.OK.value();
            }catch (Exception e){
                success = false;
                message = "updateOrder Fail!!!";
                statusCode = HttpStatus.BAD_REQUEST.value();
            }
        }

        return new ResponseEntity<>(
                new BaseResponse(success, message,newOrder) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> deleteOrder(Long id){
        try {
            Order order=orderRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            orderRepository.delete(order);
            success = true;
            message = "deleteOrder success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "deleteOrder Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,null) , HttpStatusCode.valueOf(statusCode));
    }

    public List<Order> getListOrder(String userId) {
        Long userIdLong = Long.valueOf(userId);
        return orderRepository.findByUserEntity(new UserUntity(userIdLong));
    }

    public ResponseEntity<BaseResponse<OrederConvert>> checkOrder(CheckOrderDto checkOrderDto) {
        statusCode = HttpStatus.OK.value();
        OrederConvert orederConvert = null;
        UserUntity userUntity = new UserUntity(checkOrderDto.getUserId());
        Product product =new Product(checkOrderDto.getProductId());
        try{
            Order checkOrder = orderRepository.getByStatusAndUserEntityAndProductAndNameProduct(checkOrderDto.getStatus(), userUntity, product, checkOrderDto.getNameProduct());
            orederConvert = checkOrder.getVo();
            if (checkOrder.getId() != null){
                success = true;
                message = "Đã tồn tại đơn hàng";
            }else {
                success = false;
                message = "Đơn hàng chưa có trong giỏ hàng";
            }
        }catch (Exception ex){
            success = false;
            message = "Gặp lỗi truy vấn";
            ex.printStackTrace();
        }


        return new ResponseEntity<>(
                new BaseResponse(success, message,orederConvert) , HttpStatusCode.valueOf(statusCode));
    }
}
