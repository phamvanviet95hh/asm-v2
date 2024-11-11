$(document).ready(function (){
    console.log("Connect to Login");
    let domain = window.location.hostname;
    let port = window.location.port;
    let checkbox = $("#form_checkboxLogin").val()
    $(".btn-Login").click(function (){
        let username = $("#form_username").val();
        let password = $("#form_password").val();
        let endpointLogin = `https://${domain}:${port}/login`
        var bodyData = JSON.stringify({ username: username , password : password });
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
                    // Lưu thông tin đăng nhập vào local storage
                    if (checkbox){
                        localStorage.setItem("username", data.data.userName);
                        localStorage.setItem("userId", data.data.id);
                        localStorage.setItem("token", data.token);
                        localStorage.setItem("fullName", data.data.fullName);
                        localStorage.setItem("role", data.data.role);
                        localStorage.setItem("address", data.data.address);
                        localStorage.setItem("phone", data.data.phone);
                        localStorage.setItem("email", data.data.email);
                    }else {
                        sessionStorage.setItem("username", data.data.userName);
                        sessionStorage.setItem("userId", data.data.id);
                        sessionStorage.setItem("token", data.token);
                        sessionStorage.setItem("fullName", data.data.fullName);
                        sessionStorage.setItem("role", data.data.role);
                        sessionStorage.setItem("address", data.data.address);
                        sessionStorage.setItem("phone", data.data.phone);
                        sessionStorage.setItem("email", data.data.email);
                    }
                    
                    window.location.href = `https://${domain}:${port}/index`;
                }else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    });
})