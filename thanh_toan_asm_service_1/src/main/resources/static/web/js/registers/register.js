$(document).ready(function (){
    $('.alert').alert();
    $(".register-form-group input").click(function (){
        $(this).parent().find("label").addClass("hien")
        $(this).attr("placeholder","")
    });
    
    $(document).ready(function (){
        let checkFormSend = true;
        let msg = "Không được để trống trường này...";
        $(".btn-backToHome").click(function (){
            window.location.href = `${localdomain}/index`;
        });
        let district = $("#form_quan-register");
        let ward = $("#form_xa-register");
        $(".btn-registerSend").click(function (){
           
            let fullName = $("#register-input-fullName").val();
            let userName = $("#register-input-userName").val();
            let password = $("#register-input-password").val();
            let rePassword = $("#register-input-rePassword").val();
            let phoneNumber = $("#register-input-phone").val();
            let email = $("#register-input-email").val();
            let address = "";
            if ($("#form_duong-register").val() !== ""){
                address = $("#form_duong-register").val()+ "," + $("#form_xa-register").val() + "," + $("#form_quan-register").val()+","+$("#form_tinh-register").val();
            }else{
                address = $("#form_xa-register").val() + "," + $("#form_quan-register").val()+","+$("#form_tinh-register").val();
            }
            
            if(fullName === ""){
                checkFormSend=false;
                $("#form-error-register-fullName").html(msg);
            }else if(userName === ""){
                checkFormSend=false;
                $("#form-error-register-userName").html(msg);
            }else if(password === ""){
                checkFormSend=false;
                $("#form-error-register-password").html(msg);
            }else if(rePassword === ""){
                checkFormSend=false;
                $("#form-error-register-rePassword").html(msg);
            }else if(phoneNumber === ""){
                checkFormSend=false;
                $("#form-error-register-phone").html(msg);
            }else if($("#form_tinh-register").val() === ""){
                checkFormSend=false;
                $("#form-error-register-address").html(msg);
            }else {
                if (password !== rePassword){
                    checkFormSend=false;
                    $("#form-error-register-rePassword").html("Nhập mật khẩu không khớp...");
                }else {
                    checkFormSend=true;
                    $("#form-error-register-fullName").html("");
                    $("#form-error-register-userName").html("");
                    $("#form-error-register-password").html("");
                    $("#form-error-register-phone").html("");
                    $("#form-error-register-address").html("");
                    $("#form-error-register-rePassword").html("");
                }
            }

            if (checkFormSend){
                let itemThongBao = document.getElementById("thongbao");
                let endpointLogin = `${localdomain}/user/v1/add`
                var bodyData = JSON.stringify({ userName: userName , fullName : fullName, password : password, address : address, email : email, phone: phoneNumber, detail: "Partner", status: "1", role: "USER"});
                fetch(endpointLogin, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // Thiết lập header
                    },
                    body: bodyData // Dữ liệu gửi đi
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        if(data.success){
                            console.log(itemThongBao)
                            let thongbao = `<div>
                        <i class="fa fa-check-circle-o" aria-hidden="true"></i>
                    </div>
                    <div class="mb-5">Đăng ký tài khoản thành công</div>`;
                    itemThongBao.innerHTML = thongbao;
                    
                            
                        }
                    })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });
                    setTimeout(myStopFunction,3000);
                    function myStopFunction() {
                        itemThongBao.innerHTML = "";
                        window.location.href = `${localdomain}/web/login`;
                      }
            }
            
        });
        
        $("#form_tinh-register").change(function (){
            const selectedOption = this.options[this.selectedIndex];
            let idProvince = selectedOption.getAttribute("data-code");
            console.log(idProvince)
            fetch(`${localdomain}/web/district/getDistrict?provincesId=${idProvince}`, {
                method : "GET",
                headers : {
                    "Content-Type" : "application/json"
                }
            }).then(res => {
                if (!res.ok){
                    throw new Error('Network response was not ok')
                }
                return res.json();
            }).then(data => {
                if(data.success){
                    let contentDistrict = ""
                    
                    data.data.map(value => {
                        contentDistrict += `
                        <option data-code="${value.code}"> ${value.fullName} </option>
                    `;
                    })
                    district.html(contentDistrict);
                    
                }
            }).catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
            
        })
        district.change(function (){
            const selectedOption = this.options[this.selectedIndex];
            let idProvince = selectedOption.getAttribute("data-code");
            fetch(`${localdomain}/web/ward/getList?districtId=${idProvince}`, {
                method : "GET",
                headers : {
                    "Content-Type" : "application/json"
                }
            }).then(res => {
                if (!res.ok){
                    throw new Error('Network response was not ok')
                }
                return res.json();
            }).then(data => {
                if(data.success){
                    let contentWard = ""
                    data.data.map(value => {
                        contentWard += `
                        <option data-code="${value.code}"> ${value.fullName} </option>
                    `;
                    })
                    ward.html(contentWard);
                }
            }).catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
        })
    });
    
    
});