$(document).ready(function (){
    let domain = window.location.hostname;
    let port = window.location.port;
    $("#form-login").on("submit",function (e){
        e.preventDefault();
        let username = $("#username").val();
        let password = $("#password").val();
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
                    sessionStorage.setItem("username", data.data.userName);
                    sessionStorage.setItem("userId", data.data.id);
                    sessionStorage.setItem("token", data.token);
                    sessionStorage.setItem("fullName", data.data.fullName);
                    sessionStorage.setItem("role", data.data.role);
                    sessionStorage.setItem("address", data.data.address);
                    sessionStorage.setItem("phone", data.data.phone);
                    sessionStorage.setItem("email", data.data.email);
                    window.location.href = `${localdomain}/admin/dashboard`;
                }else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    });
});