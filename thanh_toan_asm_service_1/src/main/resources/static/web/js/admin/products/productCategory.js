$(document).ready(function () {
    $(document).ready(function () {
        $(".btn-addProductCategory").click(function () {
            $("#content_box").load("/admin/addCategoryProduct");
        });
        $(".productCategory-form-group input").click(function () {
            $(this).parent().find("label").addClass("hien")
            $(this).attr("placeholder", "")
        });
        $(".viewProductCategory").click(function (e) {
            e.preventDefault();
            let idProductCategory = $(this).attr("data-id");
            $("#content_box").load(`/admin/viewProductCategory?id=${idProductCategory}`);
        });
    })
    $(document).ready(function () {

        const imageInput = document.getElementById('imageInput');
        const imagePreview = document.getElementById('content-productCategoryImg-span');
        let selectedFile = null;
        $(".btn-selectImg").click(function () {
            $("#imageInput").click();
        });
        imageInput.addEventListener('change', function (event) {
            const file = event.target.files[0]; // Lấy file được chọn
            if (file) {
                const reader = new FileReader();
                selectedFile = file;
                reader.addEventListener('load', function () {
                    imagePreview.innerHTML = `<img src="${reader.result}" id="imgProductCategoryUpload" alt="Ảnh xem trước">`;
                });

                reader.readAsDataURL(file); // Đọc file dưới dạng URL

            } else {
                imagePreview.innerHTML = '<span>Chưa có ảnh</span>';
            }

        });



        $("#productCategory-input-productCategoryName").keyup(function () {
            let tesstAlias = toSlug($(this).val());
            $("#productCategory-input-productCategoryAlias").attr("value", tesstAlias);
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

        $(".btn-addProductCategorySubmit").click(function () {
            let productCategoryName = $("#productCategory-input-productCategoryName").val();
            let productCategoryAlias = $("#productCategory-input-productCategoryAlias").val();
            let productCategoryDescription = $("#productCategory-input-productCategoryDescription").val();
            let productCategoryParentId = $("#productCategory-form-group").val();
            let userIdR = userId;
            const formData = new FormData();
            if (!selectedFile) {
                alert('Vui lòng chọn ảnh trước!');
                return;
            }
            var bodyData = JSON.stringify({
                productCategoryName: productCategoryName,
                productCategoryAlias: productCategoryAlias,
                productCategoryDescription: productCategoryDescription,
                productCategoryParentId: productCategoryParentId,
                userId: userIdR
            });
            formData.append("data", bodyData.toString());
            formData.append("file", selectedFile); // Thay bằng file thực tế

            fetch(`${localdomain}/admin/addCategoryProduct`, {
                method: 'POST',
                body: formData // Dữ liệu gửi đi
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }).then(data => {
                if (data.success) {
                    alert("Bạn vừa thêm mới 1 Danh mục sản phẩm");
                    $("#content_box").load("/admin/categoryProduct");
                }
            })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
        });
    });




});