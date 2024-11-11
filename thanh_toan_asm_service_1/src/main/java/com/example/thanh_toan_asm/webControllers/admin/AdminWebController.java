package com.example.thanh_toan_asm.webControllers.admin;

import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.example.thanh_toan_asm.dtos.admins.products.ConvertProductDto;
import com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoadonDto;
import com.example.thanh_toan_asm.entitys.HoaDon;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.services.HoaDonService;
import com.example.thanh_toan_asm.services.ProductCategoryService;
import com.example.thanh_toan_asm.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/")
public class AdminWebController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("login")
    public String adminLogin(Model model) {

        return "admin/login";
    }

    @GetMapping("dashboard")
    public String dashboardLogin(Model model) {
        return "admin/dashboard/dashboard";
    }

    @GetMapping("addCategoryProduct")
    public String addGetCategoryProduct(Model model) {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();

        model.addAttribute("listCategoryProduct", productCategories);

        return "admin/products/addProductCategory";
    }

    @GetMapping("categoryProduct")
    public String categoryProduct(Model model) {

        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();

        model.addAttribute("listCategoryProduct", productCategories);

        return "admin/products/category";
    }

    @GetMapping("product")
    public String product(Model model) {

        List<Product> products = productService.getAllProduct();
        List<ConvertProductDto> convertProductDtos = new ArrayList<>();
        for (Product item : products) {
            convertProductDtos.add(item.getVo());
        }
        model.addAttribute("listProduct", convertProductDtos);
        return "admin/products/product";
    }

    @GetMapping("billing")
    public String Billing(Model model,
                          @RequestParam String size,
                          @RequestParam String page,
                          @RequestParam("startDate") String startDate,
                          @RequestParam("endDate") String endDate,
                          @RequestParam("codeHd") String codeHd,
                          @RequestParam("statusHd") String statusHd
                          ) {
        LocalDateTime localDateTimeStart = LocalDateTime.parse(startDate);
        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endDate);
        Page<AdminCustomHoadonDto> hoaDons = hoaDonService.getAllHoaDon(localDateTimeStart, localDateTimeEnd, codeHd, statusHd, GlobalValue.pageAndId(size, page));
        System.out.println(hoaDons);
        System.out.println(hoaDons);
        model.addAttribute("hoaDons", hoaDons);
        return "admin/hoadon/listBill";
    }

    @GetMapping("viewProductCategory")
    public String viewProductCategory(Model model, @RequestParam("id") Long id) {

        ProductCategory productCategory = productCategoryService.getProductCategory(id);
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();
        model.addAttribute("productCategory", productCategory);
        model.addAttribute("listProductCategory", productCategories);
        return "admin/products/viewProduct";
    }

    @GetMapping("addProduct")
    public String addProduct(Model model) {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();
        model.addAttribute("listProductCategory", productCategories);
        return "admin/products/addProduct";
    }

    @GetMapping("editProduct")
    public String editProduct(Model model, @RequestParam("id") Long id) {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();
        model.addAttribute("listProductCategory", productCategories);
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "admin/products/editProduct";
    }

}
