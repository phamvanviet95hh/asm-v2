$(document).ready(function () {

    if (!token) {
        window.location.href = `${localdomain}/admin/login`;
    } else {
        $("#fullName").html(fullName);
        $(".menuListNew").click(function () {
            alert("Trang đang được xây dựng chỉ ADMIN mới được vào xem");
        });
        $(".menuListCategoryProduct").click(function () {
            $("#content_box").load("/admin/categoryProduct");
        });
        $(".menu").click(function () {
            alert("Trang đang được xây dựng chỉ ADMIN mới được vào xem");
        });
        $(".menuListProduct").click(function () {
            $("#content_box").load("/admin/product");
        });
        $(".menuListHoaDon").click(function () {
            $("#content_box").load(`/admin/billing?startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&size=${10}&page=${0}&codeHd=&statusHd=`);
        });
    }
    $(document).ready(function () {
        $(".nav-user").click(function () {
            $(".box-infoUser").toggleClass("hien")
        });
        $(".btn-adminLogout").click(function () {
            clearInfo();
        });
    });
    if(role === "ADMIN"){
    
    }else if(role === "PARTNER"){
        $(".btn-backHome").addClass("an");
        $(".dashboard").addClass("an");
        $(".menu").addClass("an");
        $(".menuListCategoryProduct").addClass("an");
        $(".menuListProduct").addClass("an");
        $(".menuListNew").addClass("an");
        $("#content_box").load(`/admin/billing?startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&size=${10}&page=${0}&codeHd=&statusHd=`);
    }
});