$(document).ready(function () {
    $(".btn-addProduct").click(function () {
        $("#content_box").load("/admin/addProduct");
    });
    $(".productViewLink").click(function () {
        let productId = $(this).attr("data-id")
        $("#content_box").load("/admin/editProduct?id=" + productId);
    });
});