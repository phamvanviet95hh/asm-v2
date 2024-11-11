$(document).ready(function () {
    let startDate = $("#startDate")
    let endDate = $("#endDate");
    startDate.attr("value", getFirstDayOfMonth());
    endDate.attr("value", getLastDayOfMonth());
    if(role==="PARTNER"){
        $(".box-icon-remove-detail").addClass("an");
    }
    $(".icon-view-detail").click(function () {
        $("#modalViewHoadon").addClass("active");
    })
    $(".box-icon-remove-detail").click(function () {
        let checkConfirm = confirm("Bạn có thực sự muốn xoá hoá đơn này!!!");
        if (checkConfirm) {
            let idHd = $(this).attr("data-id");

            customDelete(`${localdomain}/api/asm/v1/hoadon/delete`, idHd);
            $("#content_box").load(`/admin/billing?startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&size=${10}&page=${0}`);
        }
    })
    $(".box-icon-view-detail").click(function () {
        const id = $(this).attr("data-id");
        customLoadPage(`${localdomain}/admin/hoadons/viewDetail?id=${id}`, "modalViewHoadon");
    })
    $(".nextPageTransaction").click(function(){
        let size = $(this).attr("data-size");
        let page = $(this).attr("data-page");
        // let startDate = $("#startdate").val();
        // let endDate = $("#enddate").val();
        customLoadPage(`${localdomain}/admin/hoadons/loadListHoaDon?page=${page}&size=${size}&startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&codeHd=&statusHd=`, "content-categoryProduct-body");
    });
    $(".prevPageTransaction").click(function(){
        let size = $(this).attr("data-size");
        let page = $(this).attr("data-page");
        // let startDate = $("#startdate").val();
        // let endDate = $("#enddate").val();
        customLoadPage(`${localdomain}/admin/hoadons/loadListHoaDon?page=${page}&size=${size}&startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&codeHd=&statusHd=`, "content-categoryProduct-body");
    });
    $(".inputPage").change(function (){
        let page = $(this).val();
        let size = "10";
        // let startDate = null;
        // let endDate = null;
        if(role === "ADMIN"){
            customLoadPage(`${localdomain}/admin/hoadons/loadListHoaDon?page=${page}&size=${size}&startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&codeHd=&statusHd=`, "content-categoryProduct-body");
        }else if (role === "PARTNER"){
            customLoadPage(`${localdomain}/admin/hoadons/loadListHoaDon?page=${page}&size=${size}&startDate=${getFirstDayOfMonth()}&endDate=${getLastDayOfMonth()}&codeHd=&statusHd=`, "content-categoryProduct-body");
        }
    });

    $(".btn-searchHoaDon").click(function (){
        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val();
        let codeHd = $("#codeHd").val();
        let statusHd = $("#statusHd").val();
        $("#content_box").load(`/admin/billing?startDate=${startDate}&endDate=${endDate}&size=${10}&page=${0}&codeHd=${codeHd}&statusHd=${statusHd}`, "content-categoryProduct-body");
    })
})