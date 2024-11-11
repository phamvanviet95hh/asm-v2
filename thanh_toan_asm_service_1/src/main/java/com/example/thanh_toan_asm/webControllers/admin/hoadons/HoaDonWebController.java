package com.example.thanh_toan_asm.webControllers.admin.hoadons;

import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoaDonDetailDto;
import com.example.thanh_toan_asm.dtos.hoadon.AdminCustomHoadonDto;
import com.example.thanh_toan_asm.entitys.Product;
import com.example.thanh_toan_asm.entitys.ProductCategory;
import com.example.thanh_toan_asm.services.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("admin/hoadons/")
public class HoaDonWebController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("viewDetail")
    public String editProduct(Model model, @RequestParam("id") String id) {
        Long idHd = Long.valueOf(id);
        Long totalMoney = 0L;
        AdminCustomHoaDonDetailDto adminCustomHoaDonDetailDto = hoaDonService.getHoaDonDetail(idHd);
        List<AdminCustomHoadonDto> hoaDons = hoaDonService.getProductHoaDonDetail(idHd);

        for (AdminCustomHoadonDto hoaDon : hoaDons) {
            totalMoney += Long.valueOf(hoaDon.getTotalPriceBill());
        }
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("hoaDons", hoaDons);
        model.addAttribute("adminCustomHoaDonDetailDto", adminCustomHoaDonDetailDto);
        return "admin/hoadon/loadDetailHoaDon";
    }

    @GetMapping("loadListHoaDon")
    public String loadListHoaDon(Model model,
                          @RequestParam String size,
                          @RequestParam String page,
                          @RequestParam("startDate") String startDate,
                          @RequestParam("endDate") String endDate,
                                 @RequestParam("codeHd") String codeHd,
                                 @RequestParam("statusHd") String statusHd
    ) {
        LocalDateTime localDateTimeStart = LocalDateTime.parse(startDate);
        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endDate);
        Page<AdminCustomHoadonDto> hoaDons = hoaDonService.getAllHoaDon(localDateTimeStart, localDateTimeEnd, codeHd , statusHd, GlobalValue.pageAndId(size, page));
        model.addAttribute("hoaDons", hoaDons);
        return "admin/hoadon/loadListHoaDon";
    }

}
