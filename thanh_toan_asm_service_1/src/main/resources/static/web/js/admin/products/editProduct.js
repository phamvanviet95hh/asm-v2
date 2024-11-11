$(document).ready(function () {
    $(".btn-backProduct").click(function () {
        $("#content_box").load("/admin/product");
    });

    $(".editUserInfo").click(function () {
        let productId = $("#productId").val();
        let checkClass = $(this).find("i").hasClass("fa-pencil-square-o");
        $("#product-input-productName").keyup(function () {
            let tesstAlias = toSlug($(this).val());
            $("#product-input-productAlias").attr("value", tesstAlias);
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
            let productCategoryId = $("#product-form-groupCategoryId").val();
            let dataValueAlias = $("#product-input-productAlias").val();
            let dataProductNameInput = $("#product-input-productName").val();
            console.log(dataValue)
            var bodyData = JSON.stringify({ productId: productId, [dataKey]: dataValue, link: dataValueAlias, productCategory: productCategoryId });
            console.log(bodyData);
            fetch(`${localdomain}/api/asm/v1/update/product`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Thiết lập header
                    'Authorization': `Bearer ${token}`
                },
                body: bodyData // Dữ liệu gửi đi
            }).then(response => {
                if (!response.ok) {
                    
                    alert("Hết phiên đăng nhập");
                    clearInfo();
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }).then(data => {
                if (data.success) {
                    alertGloable(data.message);
                    $("#ttSp").html(dataProductNameInput)
                }
            })
        }

    });
    let selectedFile = null;
    $(document).ready(function () {

        const imageInputProduct = document.getElementById('imageInputAddProduct');
        const imagePreviewProduct = document.getElementById('content-productImg-span');
        $(".btn-selectImg").click(function () {
            $("#imageInputAddProduct").click();
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
    $(".btn-editImgProduct").click(function () {
        const formData = new FormData();
        let productId = $("#productId").val();
        if (!selectedFile) {
            alert('Vui lòng chọn ảnh trước!');
            return;
        }
        formData.append("file", selectedFile);
        formData.append("productId", productId);
        fetch(`${localdomain}/api/asm/v1/upload/image`, {
            method: 'POST',
            body: formData // Dữ liệu gửi đi
        }).then(response => {
            if (!response.ok) {
                alert("Hết phiên đăng nhập");
                clearInfo();
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
    })
    $(".btn-removeProduct").click(function () {
        let checkConfirm = confirm(" Bạn có chắc chắn muốn xoá sản phẩm này ");
        if (checkConfirm) {
            xoa(`${localdomain}/api/asm/v1/delete/product`, $("#productId").val(), "/admin/product");
        }

    });
});