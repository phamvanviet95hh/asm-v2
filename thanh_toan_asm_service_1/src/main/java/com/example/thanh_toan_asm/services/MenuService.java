package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.menu.MenuCreationRequest;
import com.example.thanh_toan_asm.dtos.menu.MenuUpdateRequest;
import com.example.thanh_toan_asm.dtos.request.ProductCreationRequest;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Menu;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.repositorys.AliasRepository;
import com.example.thanh_toan_asm.repositorys.MenuRepository;
import com.example.thanh_toan_asm.repositorys.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MenuService {
    private Boolean success;
    private String message;
    int statusCode = 0;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    AliasRepository aliasRepository;

    public ResponseEntity<BaseResponse<Menu>> createMenu(MenuCreationRequest request) throws IOException {
        if(aliasRepository.existsByAlias(request.getLink())){
            Menu menu=Menu.builder()
                    .link(request.getLink())
                    .createAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .nameMenu(request.getNameMenu())
                    .titleMenu(request.getTitleMenu())
                    .parentId(request.getParentId())
                    .userEntity(new UserUntity(request.getUserId()))
                    .status(request.getStatus())
                    .build();
            try {
                menuRepository.save(menu);
                success = true;
                message = "createMenu success!!!";
                statusCode = HttpStatus.OK.value();
            }catch (Exception e){
                success = false;
                message = "createMenu Fail!!!";
                statusCode = HttpStatus.BAD_REQUEST.value();
            }
            return new ResponseEntity<>(
                    new BaseResponse(success, message,menu) , HttpStatusCode.valueOf(statusCode));
        }
        else {
            success = false;
            message = "createMenu Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            return new ResponseEntity<>(
                    new BaseResponse(success, message,null) , HttpStatusCode.valueOf(statusCode));
        }
    }

    public ResponseEntity<BaseResponse<Menu>> getMenu(Long id){
        Menu menu=null;
        try {
            menu=menuRepository.findById(id).orElseThrow(()->new RuntimeException("No product in table"));
            success = true;
            message = "getMenu success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "getMenu Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,menu) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<Menu>> updateMenu(MenuUpdateRequest request){
        Menu newMenu=menuRepository.findById(request.getMenuId()).orElseThrow(()->new RuntimeException("No menu in table"));
        try {
            newMenu.setNameMenu(request.getNameMenu());
            newMenu.setUpdateAt(LocalDateTime.now());
            newMenu.setTitleMenu(request.getTitleMenu());
            newMenu.setLink(request.getLink());
            newMenu.setStatus(request.getStatus());
            newMenu.setParentId(request.getParentId());
            success = true;
            message = "updateMenu success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "updateMenu Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,newMenu) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<Menu>> deleteMenu(Long id){
        try {
            Menu menu=menuRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            menuRepository.delete(menu);
            success = true;
            message = "deleteMenu success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "deleteMenu Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,null) , HttpStatusCode.valueOf(statusCode));
    }
}
