$(document).ready(function () {
    
    let selectedFile = null;
    const imageInput = document.getElementById('imageInput');
    const imagePreview = document.getElementById('content-productCategoryImg-span');
    
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
    
    $(".btn-updatePartnerRegister").click(function () {
        let idPart = $(this).attr("data-id");
        let checkFrom = true;
        let email = $("#email").val();
        let fullNameRegister = $("#fullNameRegister").val();
        let phoneRegister = $("#phoneRegister").val();
        let addressRegister = $("#addressRegister").val()
        let username;
        let password;
        var dataBody;
        if ($("#username").val() !== null) {
            username = $("#username").val();
            if (username === "") {
                checkFrom = false;
                $("#errorUsername").html("Không được để trống trường này");
            }
        }
        if (checkFrom) {
            $("#errorUsername").html("");
            $("#errorRePasswords").html("");
            $("#errorCodePartner").html("");
            const formData = new FormData();
            dataBody = JSON.stringify({
                id: idPart,
                userName: username,
                address: addressRegister,
                email: email,
                fullName: fullNameRegister,
                phone: phoneRegister
            });
            formData.append("data", dataBody.toString());
            formData.append("file", selectedFile); // Thay bằng file thực tế
            fetch(`${localdomain}/user/v1/updatePartner`, {
                method: "POST",
                body: formData // Dữ liệu gửi đi
            }).then(response => {
                if (!response.ok) {
                    if (response.status === 500) {
                        console.log("Connect to service timeout");
                    }
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }).then(data => {
                console.log(data)
                if (data.success) {
                    alertGloable(data.message, "success");
                    $("#logo-dv").attr("src", `data:image/png;base64,${data.data.avatar}`);
                    sessionStorage.setItem("logo", data.data.avatar);
                } else {
                    alertGloable(data.message, "false");
                }
            })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
            setTimeout(reloadListService, 1100);
        } else {
            alertGloable("Gặp lỗi kiểm tra lại những trường thông tin đã nhập", "false");
        }
        
    });
    $("#info-topLeft-prev").click(function () {
        let partnerId = $(this).attr("data-id");
        $("#content_box").load(`/admin/user/inforUser?idUser=${partnerId}`);
    });
    $(".btn-addPartnerBack").click(function () {
        let partnerId = $(this).attr("data-id");
        $("#content_box").load(`/admin/user/inforUser?idUser=${partnerId}`);
    })
    
})