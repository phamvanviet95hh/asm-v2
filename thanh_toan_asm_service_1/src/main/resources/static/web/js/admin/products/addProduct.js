$(document).ready(function () {
    let selectedFile = null;
    $(".product-form-group input").click(function () {
        $(this).parent().find("label").addClass("hien")
        $(this).attr("placeholder", "")
    });
    $(".btn-backProduct").click(function () {
        $("#content_box").load("/admin/product");
    });
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
    $(document).ready(function () {

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

        $(".btn-addProductSubmit").click(function () {
            let productName = $("#product-input-productName").val();
            let productAlias = $("#product-input-productAlias").val();
            let productDescription = $("#product-input-productDescription").val();
            let productPrice = $("#product-input-productPrice").val();
            let productCategoryId = $("#product-form-groupCategoryId").val();
            let userIdR = userId;

            const formData = new FormData();
            if (!selectedFile) {
                alert('Vui lòng chọn ảnh trước!');
                return;
            } var bodyData = JSON.stringify({
                productName: productName,
                productDescription: productDescription,
                productPrice: productPrice,
                link: productAlias,
                status: "1",
                productCategoryId: productCategoryId,
                userId: userIdR
            });
            formData.append("data", bodyData.toString());
            formData.append("file", selectedFile); // Thay bằng file thực tế
            fetch(`${localdomain}/api/asm/v1/create/product`, {
                method: 'POST',
                body: formData // Dữ liệu gửi đi
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }).then(data => {
                console.log(data)
                if (data.success) {
                    alert("Bạn vừa thêm mới 1 sản phẩm");
                    $("#content_box").load("/admin/product");
                }
            })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
        });
    });
});