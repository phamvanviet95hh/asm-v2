$(document).ready(function () {
    $(".btn-addProductCategoryDelete").click(function () {
        let id = $(this).attr("data-id");
        let checkDelete = confirm("Bạn có chắc chắn sẽ xoá danh mục này");
        if (checkDelete) {
            xoa(`${localdomain}/admin/deleteCategoryProduct`, id, "/admin/categoryProduct");
        } else {

        }
    });
    $(".btn-backProductCategory").click(function () {
        $("#content_box").load("/admin/categoryProduct");
    });
    $(".editUserInfo").click(function () {
        let productCategoryId = $("#productCategoryId").val();
        let checkClass = $(this).find("i").hasClass("fa-pencil-square-o");
        $("#productCategory-input-viewProductCategoryName").keyup(function () {
            let tesstAlias = toSlug($(this).val());
            $("#productCategory-input-viewProductCategoryAlias").attr("value", tesstAlias);
        });
        
        function toSlug(str) {
            // Chuyển sang chữ thường
            str = str.toLowerCase();
            
            // Bỏ dấu tiếng Việt
            str = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
            
            // Thay dấu cách và ký tự đặc biệt thành dấu '-'
            str = str.replace(/\s+/g, '-')     // Thay dấu cách bằng '-'
                .replace(/[^\w\-]+/g, '') // Loại bỏ ký tự đặc biệt
                .replace(/\-\-+/g, '-')   // Gộp nhiều dấu '-' liên tiếp thành 1
                .replace(/^-+/, '')       // Loại bỏ dấu '-' ở đầu
                .replace(/-+$/, '');      // Loại bỏ dấu '-' ở cuối
            
            return str;
        }
        if (checkClass) {
            $(this).find("i").removeClass("fa-pencil-square-o");
            $(this).find("i").addClass("fa-floppy-o");
            $(this).parent().find("input").removeAttr("readonly");
            $(this).parent().find("select").removeAttr("disabled");
        } else {
            $(this).find("i").addClass("fa-pencil-square-o");
            $(this).find("i").removeClass("fa-floppy-o");
            $(this).parent().find("input").attr("readonly", true);
            let dataKey = $(this).attr("data-key");
            let dataValue = $(this).parent().find("input").val();
            let productCategoryIdParent = $("#productCategory-form-group").val();
            let dataValueAlias = $("#productCategory-input-viewProductCategoryAlias").val();
            let dataProductNameInput = $("#productCategory-input-viewProductCategoryName").val();
            
            var bodyData = JSON.stringify({ id: productCategoryId, [dataKey]: dataValue, alias: dataValueAlias, productCategory: productCategoryId });
            var headers =  {
                'Content-Type': 'application/json', // Thiết lập header
                    'Authorization': `Bearer ${token}`
            }
            console.log(bodyData);
            post(`${localdomain}/admin/update/productCategory`, headers, bodyData);
        }
        
    });
    let selectedFile = null;
    $(document).ready(function () {
        
        const imageInputProduct = document.getElementById('imageInput');
        const imagePreviewProduct = document.getElementById('content-productCategoryImg-span');
        $(".btn-selectImg").click(function () {
            $("#imageInput").click();
        });
        imageInputProduct.addEventListener('change', function (event) {
            const file = event.target.files[0]; // Lấy file được chọn
            if (file) {
                const reader = new FileReader();
                selectedFile = file;
                reader.addEventListener('load', function () {
                    imagePreviewProduct.innerHTML = `<img src="${reader.result}" id="imgProductCategoryUpload" alt="Ảnh xem trước">`;
                });
                
                reader.readAsDataURL(file); // Đọc file dưới dạng URL
                
            } else {
                imagePreviewProduct.innerHTML = '<span>Chưa có ảnh</span>';
            }
            
        });
    })
    $(".btn-editImgProduct").click(function (){
        const formData = new FormData();
        let productId = $("#productCategoryId").val();
        if (!selectedFile) {
            alert('Vui lòng chọn ảnh trước!');
            return;
        }
        formData.append("file", selectedFile);
        formData.append("productCategoryId", productId);
        fetch(`${localdomain}/admin/upload/imageProductCategory`, {
            method: 'POST',
            body: formData // Dữ liệu gửi đi
        }).then(response => {
            if (!response.ok) {
                if(response.status === 500){
                    alert("Hết phiên đăng nhập");
                    clearInfo();
                }
                throw new Error('Network response was not ok');
                
            }
            return response.json();
        }).then(data => {
            console.log(data)
            if (data.success) {
                alertGloable("Bạn vừa thay đổi hình ảnh của sản phẩm thành công");
            } else {
                alert(data.message);
            }
        })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    });
});