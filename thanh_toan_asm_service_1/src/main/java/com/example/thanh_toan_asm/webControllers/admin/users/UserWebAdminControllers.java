package com.example.thanh_toan_asm.webControllers.admin.users;


import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin/user/")
public class UserWebAdminControllers {

    @Autowired
    private UserService userService;

    @GetMapping("inforUser")
    public String editProduct(Model model, @RequestParam("idUser") Long id) {
        System.out.println(id);
        UserUntity us = userService.getInfoUser(id);
        model.addAttribute("partner", us);
        return "admin/users/infoUser";
    }

    @GetMapping("editUser")
    public String editUser(@RequestParam("id") String partnerId, Model model) {
        Long id = Long.valueOf(partnerId);
        UserUntity userUntity = userService.findById(id);
        model.addAttribute("userUntity", userUntity);
        return "admin/users/editUser";
    }

}
