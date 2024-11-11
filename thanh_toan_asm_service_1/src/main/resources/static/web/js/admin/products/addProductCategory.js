$(document).ready(function(){
    $(".btn-backProductCategory").click(function () {
        $("#content_box").load("/admin/categoryProduct");
    });
})