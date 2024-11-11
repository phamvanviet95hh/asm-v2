package com.example.thanh_toan_asm.webControllers;

import com.example.thanh_toan_asm.dtos.BaseResponseList;
import com.example.thanh_toan_asm.dtos.OrederConvert;
import com.example.thanh_toan_asm.dtos.address.ResponseProvince;
import com.example.thanh_toan_asm.entitys.*;
import com.example.thanh_toan_asm.repositorys.ProvincesRepository;
import com.example.thanh_toan_asm.services.HoaDonService;
import com.example.thanh_toan_asm.services.OrderService;
import com.example.thanh_toan_asm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("web/cart")
    public String cart(Model model){

        List<Provinces> responseProvince = provincesRepository.findAll();
        List<ResponseProvince> responseProvinces = new ArrayList<>();
        for(Provinces item : responseProvince){
            responseProvinces.add(item.getVo());
        }
        model.addAttribute("listProvince", responseProvinces);
        return "carts/cart";
    }

    @GetMapping("web/finsh")
    public String finsh(Model model, @RequestParam("code") String code){
        System.out.println(code);
        HoaDon hoaDon = hoaDonService.getHoaDon(code);

        model.addAttribute("address", hoaDon.getAddressReceive());
        return "carts/finsh";
    }

    @GetMapping("web/header")
    public String header(){
        return "mcv/header";
    }

    @GetMapping("web/register")
    public String register(Model model){
        List<Provinces> responseProvincee = provincesRepository.findAll();
        List<ResponseProvince> responseProvinces = new ArrayList<>();
        for(Provinces item : responseProvincee){
            responseProvinces.add(item.getVo());
        }
        model.addAttribute("listProvince", responseProvinces);
        return "registers/register";
    }
    @GetMapping(value = "web/get/productList")
    public ResponseEntity<BaseResponseList<Product>> productList(@RequestParam("status") String id) {

        return productService.getProductStatus(id);
    }

    @GetMapping("web/login")
    public String login(){
        return "web/login";
    }

}
