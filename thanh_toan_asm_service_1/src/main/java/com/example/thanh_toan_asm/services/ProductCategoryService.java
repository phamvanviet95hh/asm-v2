package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.example.thanh_toan_asm.dtos.admins.products.RequestProductCategoryDto;
import com.example.thanh_toan_asm.dtos.admins.products.ResponProductGloableDto;
import com.example.thanh_toan_asm.dtos.product.ProductCategoryRq;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.repositorys.productCategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProductCategoryService {
    String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String pathImg = GlobalValue.pathImageProduct + dateNow + "/";

    int statusCode=0;

    private Boolean success;
    private String message;

    @Autowired
    private productCategoryRepository categoryRepository;

    public List<ProductCategory> getAllProductCategory() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<ResponProductGloableDto> addProductCategory(String data, MultipartFile file)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestProductCategoryDto requestProductCategoryDto = objectMapper.readValue(data,
                RequestProductCategoryDto.class);
        System.out.println(requestProductCategoryDto.getProductCategoryName());
        if (file.isEmpty()) {
            success = false;
            message = "Không có file";
            return new ResponseEntity<>(ResponProductGloableDto.builder()
                    .success(success)
                    .message(message)
                    .build(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        try {

            byte[] bytes = file.getBytes();
            String base64Image=Base64.getEncoder().encodeToString(bytes);
            ProductCategory productCategory = ProductCategory.builder()
                    .alias(requestProductCategoryDto.getProductCategoryAlias())
                    .createAt(LocalDateTime.now())
                    .nameCategory(requestProductCategoryDto.getProductCategoryName())
                    .description(requestProductCategoryDto.getProductCategoryDescription())
                    .home("1")
                    .status("1")
                    .images(base64Image)
                    .updateAt(LocalDateTime.now())
                    .userEntity(new UserUntity(requestProductCategoryDto.getUserId()))
                    .parentId(String.valueOf(requestProductCategoryDto.getProductCategoryParentId()))
                    .build();
            categoryRepository.save(productCategory);
            success = true;
            message = "Add Product Category Success!!!";
        } catch (Exception ex) {
            success = false;
            message = "Add Product Category Fail!!!";
            ex.printStackTrace();
        }
        return new ResponseEntity<>(ResponProductGloableDto.builder()
                .success(success)
                .message(message)
                .build(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public ProductCategory getProductCategory(Long id) {

        return categoryRepository.findById(id).get();

    }

    public ResponseEntity<ResponProductGloableDto> deleteCategoryProduct(String id) {
        try {
            success = true;
            message = "Delete Product Category Success!!!";
            categoryRepository.deleteById(Long.valueOf(id));
        }catch (Exception ex) {
            success = false;
            message = "Delete Product Category Fail!!!";
            ex.printStackTrace();
        }

        return new ResponseEntity<>(ResponProductGloableDto.builder()
                .success(success)
                .message(message)
                .build(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public ResponseEntity<BaseResponse<ProductCategory>> updateProductCategory(ProductCategoryRq request) {



        System.out.println("hasdhasd" + request.getParentId());
        ProductCategory newProduct = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No order in table"));
        try {
            BeanUtils.copyProperties(request , newProduct, getNullPropertyNames(request));
            newProduct.setUpdateAt(LocalDateTime.now());
            newProduct.setParentId(request.getParentId());
            ProductCategory productNew =  categoryRepository.save(newProduct);
            success = true;
            message = "update Product success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (Exception e) {
            success = false;
            message = "update Product Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, newProduct), HttpStatusCode.valueOf(statusCode));
    }
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public ResponseEntity<BaseResponse<Product>> uploadImageProductCaategory(Long productId, MultipartFile file) {
        ProductCategoryRq request = new ProductCategoryRq();
        request.setId(productId);

        if (file.isEmpty()) {
            success = false;
            message = "Không có file";
            return new ResponseEntity<>(new BaseResponse(success, message, null), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        ProductCategory product = categoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No product in table"));
        try {
            byte[] bytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            request.setImages(base64Image);
            BeanUtils.copyProperties(request , product, getNullPropertyNames(request));
            product.setUpdateAt(LocalDateTime.now());
            ProductCategory productNew =  categoryRepository.save(product);
            success = true;
            message = "uploadImageProduct Success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (Exception e) {
            success = false;
            message = "uploadImageProduct Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, product), HttpStatusCode.valueOf(statusCode));

    }
}
