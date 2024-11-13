$(".btn-changePassword").click(function (){
    $("#box-changePasswordNew").addClass("hien");
})
$(".box-icon-close").click(function (){
    $("#box-changePasswordNew").removeClass("hien");
});
function checkPass(password, rePassword){
    if(password === rePassword){
        return true;
    }else {
        $("#errorRepass").html("Mật khẩu không khớp")
        return false;
    }
}

function validatePassword(password) {
    let errors = [];
    
    if (password.length < 8) {
        errors.push("Mật khẩu phải có tối thiểu 8 ký tự.");
    }
    if (!/[a-z]/.test(password)) {
        errors.push("Mật khẩu phải chứa ít nhất một chữ thường.");
    }
    if (!/[A-Z]/.test(password)) {
        errors.push("Mật khẩu phải chứa ít nhất một chữ in hoa.");
    }
    if (!/\d/.test(password)) {
        errors.push("Mật khẩu phải chứa ít nhất một chữ số.");
    }
    if (!/[@$!%*?&]/.test(password)) {
        errors.push("Mật khẩu phải chứa ít nhất một ký tự đặc biệt (ví dụ: @$!%*?&).");
    }
    
    if (errors.length > 0) {
        console.log("Mật khẩu không hợp lệ:");
        $(".box-errorChangeParent").addClass("hien");
        const errorElement = document.getElementById("content-error");
        errorElement.innerHTML = errors.map(error => `<li>${error}</li>`).join("");
        return false;
    } else {
        console.log("Mật khẩu hợp lệ.");
        return true;
    }
}
$(".btn-sendChangePassword").click(function (){
    let checkEmpty = true;
    let idPart = $(this).attr("data-value");
    let passwordOld = $("#passwordOld").val();
    let passwordNew = $("#passwordNew").val();
    let passwordNewRe = $("#passwordNewReq").val();
    checkEmpty = checkPass(passwordNew, passwordNewRe);
    let checkValidate = validatePassword(passwordNew);
    if (checkValidate && checkEmpty){
        fetch(`${localdomain}/user/v1/checkPassword`,{
            method: "POST",
            headers:{
                "Content-Type": "application/json"
            },
            body : JSON.stringify({
                userId : idPart,
                password : passwordOld,
                newPassword : passwordNew
            })
        }).then((res) => {
            if (!res.ok) {
                if (res.status === 500) {
                    customLogout();
                }
                throw new Error('Network response was not ok');
            }
            return res.json();
        }).then(data =>{
            if(data.success){
                alertGloable("Cập nhật mật khẩu thành công.", "success");
                $("#content_box").load(`/admin/user/inforUser?idUser=${userId}`);
            }else {
                alertGloable("Cập nhật mật khẩu thất bại.", "false");
               
            }
        }).catch(err => {
            console.log(err)
        })
    }
    
});
$(".btn-exitChangePassword").click(function (){
    $("#box-changePasswordNew").removeClass("hien");
});

$(".btn-editInFor").click(function () {
    let idPartner = $(this).attr("data-idPart");
    $("#content_box").load(`/admin/user/editUser?id=${idPartner}`);
})