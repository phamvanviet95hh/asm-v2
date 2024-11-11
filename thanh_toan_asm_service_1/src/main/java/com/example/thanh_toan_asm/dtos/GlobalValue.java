package com.example.thanh_toan_asm.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GlobalValue {
    public static String apiCreateToken = "https://mpa-va.gtelpay.vn/api/v3/authentication/token/create";
    public static String apiCreateVirtualAcc = "https://mpa-va.gtelpay.vn/api/v3/virtual-account/create";
    public static String apiUpdateVirtualAcc = "https://mpa-va.gtelpay.vn/api/v3/virtual-account/update";
    public static String apiDetailVirtualAcc = "https://mpa-va.gtelpay.vn/api/v3/virtual-account/detail";
    public static String apiCloseVirtualAcc = "https://mpa-va.gtelpay.vn/api/v3/virtual-account/close";
    public static String apiReopenVirtualAcc = "https://mpa-va.gtelpay.vn/api/v3/virtual-account/re-open";

    public static String pathImageProduct = "imgs/";

     public static String pathSignatureKey = "signature_key/private_key.pem";
//    public static String pathSignatureKey = "C:\\Users\\ADMIN\\Desktop\\app\\asm-dev\\private_key\\private_key.pem";
    public static String emailText = "Kính gửi %s,\n" +
            "\n" +
            "Chúng tôi xin chân thành cảm ơn quý khách đã tin tưởng và lựa chọn sản phẩm/dịch vụ của Trung tâm Dịch vụ Chuyển đổi số - Chi nhánh Tổng công ty Công nghệ - Viễn thông Toàn Cầu (GTEL CDS). Sự ủng hộ của quý khách là niềm động viên lớn nhất cho chúng tôi trong việc mang đến những sản phẩm và dịch vụ tốt nhất.\n"
            +
            "\n" +
            "Chi tiết đơn hàng của quý khách:\n" +
            "- Mã đơn hàng: %s\n" +
            "- Họ tên KH: %s\n" +
            "- Số điện thoại: %s\n" +
            "- Địa chỉ: %s\n" +
            "- Ngày mua: %s\n" +
            "- Sản phẩm: %s\n" +
            "- Trạng thái: %s\n" +
            "- Tổng tiền: %s\n" +
            "\n" +
            "Chúng tôi hy vọng rằng quý khách sẽ hài lòng với sản phẩm/dịch vụ vừa mua. Nếu có bất kỳ thắc mắc hay cần hỗ trợ thêm, đừng ngần ngại liên hệ với chúng tôi qua email này.\n"
            +
            "\n" +
            "Một lần nữa, xin cảm ơn quý khách và chúng tôi rất mong được phục vụ quý khách trong tương lai.\n" +
            "\n" +
            "Trân trọng!!!";
    public static String urlCreateQr = "https://img.vietqr.io/image/%s-%s-%s.png?amount=%s&addInfo=%s&accountName=%s";
    public static Pageable pageAndId(String size, String page){
        int sizeInt = Integer.parseInt(size);
        int pageInt = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(pageInt, sizeInt);
        return pageable;
    }

    public static String pathImageLogoQr="/usr/asm/app/gtelpay_logo.png";
//    public static String pathImageLogoQr="C:\\Users\\ADMIN\\Desktop\\app\\asm-dev\\private_key\\gtelpay_logo.png";
}
