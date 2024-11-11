package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.request.CategoryCreationRequest;
import com.example.thanh_toan_asm.dtos.request.CategoryUpdateRequest;
import com.example.thanh_toan_asm.entitys.NewCategory;
import com.example.thanh_toan_asm.repositorys.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private Boolean success;
    private String message;
    int statusCode = 0;
    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<BaseResponseList<NewCategory>> searchCategory(String categoryName){
        List<NewCategory> categoryList=new ArrayList<>();
        try {
            categoryList=categoryRepository.searchCategory(categoryName);
            success = true;
            message = "searchCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e) {
            success = false;
            message = "searchCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponseList(success, message,categoryList) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<NewCategory>> getCategory(Long id){
        NewCategory newCategory = null;
        try {
            newCategory=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            success = true;
            message = "getCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e) {
            success = false;
            message = "getCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message,newCategory) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponseList<NewCategory>> getAllCategory(){
        List<NewCategory> categoryList= new ArrayList<>();
        try {
            categoryList= categoryRepository.findAll();
            success = true;
            message = "getAllCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e) {
            e.printStackTrace();
            success = false;
            message = "getAllCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponseList(success, message,categoryList) , HttpStatusCode.valueOf(statusCode));
    }



    public ResponseEntity<BaseResponseList<String>> deleteCategory(Long id){
        try {
            NewCategory newCategory=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            categoryRepository.delete(newCategory);
            success = true;
            message = "deleteCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e) {
            success = false;
            message = "deleteCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponseList(success, message,null) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponseList<String>> editCategory(CategoryUpdateRequest categoryUpdateRequest, Long id){
        try {
            NewCategory newCategory=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("No user in table"));
            newCategory.setAlias(categoryUpdateRequest.getAlias());
            newCategory.setCreateAt(categoryUpdateRequest.getCreateAt());
            newCategory.setDeleteAt(categoryUpdateRequest.getDeleteAt());
            newCategory.setUpdateAt(categoryUpdateRequest.getUpdateAt());
            newCategory.setDescription(categoryUpdateRequest.getDescription());
            newCategory.setHome(categoryUpdateRequest.getHome());
            newCategory.setImages(categoryUpdateRequest.getImages());
            newCategory.setParentId(categoryUpdateRequest.getParentId());
            newCategory.setSort(categoryUpdateRequest.getSort());
            newCategory.setName(categoryUpdateRequest.getName());
            categoryRepository.save(newCategory);
            success = true;
            message = "editCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e) {
            e.printStackTrace();
            success = false;
            message = "editCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponseList(success, message,null) , HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponseList<String>> addCategory(CategoryCreationRequest request){
        if(categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("User existed!");
        }
        NewCategory category=new NewCategory();
        category.setAlias(request.getAlias());
        category.setCreateAt(request.getCreateAt());
        category.setDeleteAt(request.getDeleteAt());
        category.setDescription(request.getDescription());
        category.setHome(request.getHome());
        category.setImages(request.getImages());
        category.setParentId(request.getParentId());
        category.setUpdateAt(request.getUpdateAt());
        category.setSort(request.getSort());
        category.setName(request.getName());
//        category.setNews(new New(request.getId()));
        try {
            categoryRepository.save(category);
            success = true;
            message = "addCategory success!!!";
            statusCode = HttpStatus.OK.value();
        }catch (Exception e){
            success = false;
            message = "addCategory Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponseList(success, message,null) , HttpStatusCode.valueOf(statusCode));
    }
}
