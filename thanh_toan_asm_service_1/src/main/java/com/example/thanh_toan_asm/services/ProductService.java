package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.example.thanh_toan_asm.dtos.admins.products.RequestProductCategoryDto;
import com.example.thanh_toan_asm.dtos.admins.products.ResponProductGloableDto;
import com.example.thanh_toan_asm.dtos.product.UploadImageRequest;
import com.example.thanh_toan_asm.dtos.request.ProductCreationRequest;
import com.example.thanh_toan_asm.dtos.request.ProductUpdateRequest;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.repositorys.AliasRepository;
import com.example.thanh_toan_asm.repositorys.ProductRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class ProductService {
    private Boolean success;
    private String message;
    int statusCode = 0;

    boolean checkProduct = true;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AliasRepository aliasRepository;

    public ResponseEntity<ResponProductGloableDto> createProduct(String request, MultipartFile file)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCreationRequest requestProductCategoryDto = objectMapper.readValue(request,
                ProductCreationRequest.class);
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
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            Product product = Product.builder()
                    .productName(requestProductCategoryDto.getProductName())
                    .status(requestProductCategoryDto.getStatus())
                    .productDescription(requestProductCategoryDto.getProductDescription())
                    .productPrice(requestProductCategoryDto.getProductPrice())
                    .link(requestProductCategoryDto.getLink())
                    .updateAt(LocalDateTime.now())
                    .createAt(LocalDateTime.now())
                    .userEntity(new UserUntity(requestProductCategoryDto.getUserId()))
                    .productCategory(new ProductCategory(requestProductCategoryDto.getProductCategoryId()))
                    .productImage(base64Image)
                    .build();
            productRepository.save(product);
            success = true;
            message = "create product success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (Exception e) {
            success = false;
            message = "create product Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(ResponProductGloableDto.builder()
                .success(success)
                .message(message)
                .build(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public ResponseEntity<BaseResponse<Product>> uploadImageProduct(Long productId, MultipartFile file) throws IOException {
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductId(productId);

        if (file.isEmpty()) {
            success = false;
            message = "Không có file";
            return new ResponseEntity<>(new BaseResponse(success, message, null), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No product in table"));
        try {
            byte[] bytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            request.setProductImage(base64Image);
            BeanUtils.copyProperties(request , product, getNullPropertyNames(request));
            product.setUpdateAt(LocalDateTime.now());
            Product productNew =  productRepository.save(product);
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

    public Product getProduct(Long id) {
        Product product = null;
        try {
            product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No product in table"));
            success = true;
            message = "getOrder success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (Exception e) {
            success = false;
            message = "getOrder Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return product;
    }

    public ResponseEntity<BaseResponse<Product>> updateProduct(ProductUpdateRequest request) {
        Long ProductCategoryID = 0L;

        System.out.println(request.getProductCategory());
        Product newProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("No order in table"));
        if (Long.parseLong(request.getProductCategory()) != newProduct.getProductCategory().getId() ){
            System.out.println("co ProductCategoryId");
            ProductCategoryID += Long.parseLong(request.getProductCategory());
        }else {
            System.out.println("ProductCategoryID đã tồn tại");
            ProductCategoryID += Long.parseLong(request.getProductCategory());
        }

        try {
            BeanUtils.copyProperties(request , newProduct, getNullPropertyNames(request));
            newProduct.setUpdateAt(LocalDateTime.now());
            newProduct.setProductCategory(new ProductCategory(ProductCategoryID));
            Product productNew =  productRepository.save(newProduct);
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

    public ResponseEntity<BaseResponse<Product>> deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No user in table"));
            productRepository.delete(product);
            success = true;
            message = "deleteOrder success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (Exception e) {
            success = false;
            message = "deleteOrder Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, null), HttpStatusCode.valueOf(statusCode));
    }

    public String saveImage(MultipartFile file) throws IOException {
        String folderPath = GlobalValue.pathImageProduct
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/";
        if (!Files.exists(Path.of(folderPath))) {
            try {
                Files.createDirectories(Path.of(folderPath));
                log.info(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info(folderPath);
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(folderPath).resolve(originalFilename).normalize();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return folderPath + originalFilename;
    }

    public ResponseEntity<BaseResponseList<Product>> getProductStatus(String i) {
        List<Product> productList;
        statusCode = HttpStatus.OK.value();
        try {
            success = true;
            message = "Get data Success";
            productList = productRepository.findByStatus(i, Limit.of(2));
            if (productList.isEmpty()) {
                success = false;
                message = "Can't find Data";
            }
        } catch (Exception ex) {
            success = false;
            message = "Get Fail";

            productList = null;
            ex.printStackTrace();
        }

        return new ResponseEntity<>(
                new BaseResponseList<>(success, message, productList), HttpStatusCode.valueOf(statusCode));
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

}