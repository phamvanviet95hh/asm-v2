// let localdomain = "http://" + window.location.hostname + ":" + window.location.port;
let localdomain = "https://" + window.location.hostname + ":" + window.location.port;
let token = localStorage.getItem("token") !== null ? localStorage.getItem("token") : sessionStorage.getItem("token");
let fullName = localStorage.getItem("fullName") !== null ? localStorage.getItem("fullName") : sessionStorage.getItem("fullName");
let username = localStorage.getItem("username") !== null ? localStorage.getItem("username") : sessionStorage.getItem("username");
let userId = localStorage.getItem("userId") !== null ? localStorage.getItem("userId") : sessionStorage.getItem("userId");
let role = localStorage.getItem("role") !== null ? localStorage.getItem("role") : sessionStorage.getItem("role");
let phone = localStorage.getItem("phone") !== null ? localStorage.getItem("phone") : sessionStorage.getItem("phone");
let email = localStorage.getItem("email") !== null ? localStorage.getItem("email") : sessionStorage.getItem("email");
let address = localStorage.getItem("address") !== null ? localStorage.getItem("address") : sessionStorage.getItem("address");

function clearInfo() {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("fullName");
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("phone");
    sessionStorage.removeItem("email");
    sessionStorage.removeItem("address");
    window.location.href = `${localdomain}/admin/login`;
}

function alertGloable(message) {
    $('#myAlert').fadeIn();
    $("#contentAlert").html(message);
    $(".alert-success").addClass("opa");
    setTimeout(function () {
        $('#myAlert').fadeOut();
        $(".alert-success").removeClass("opa");
    }, 3000);
}

function post(url, header = {}, bodyData = {}) {
    fetch(url, {
        method: "POST",
        headers: header,
        body: bodyData // Dữ liệu gửi đi
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        console.log(data)
        if (data.success) {
            alertGloable(data.success);
        } else {
            alert(data.message);
        }
    })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

function xoa(url, id, back) {
    fetch(`${url}?id=${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (!res.ok) {
            throw new Error('Network response was not ok')
        }
        return res.json();
    }).then(data => {
        if (data.success) {
            alertGloable("Bạn vừa xoá danh mục thành công");
            $("#content_box").load(back);
        }
    }).catch(err => {
        console.error('There has been a problem with your fetch operation:', err);
    })
}

function getCurrentDate() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0 nên cần +1
    const day = String(now.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}
function getFirstDayOfMonth() {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    
    
    const firstDay = new Date(year, month, 1);
    
    
    const yearString = firstDay.getFullYear();
    const monthString = String(firstDay.getMonth() + 1).padStart(2, '0');
    const dayString = String(firstDay.getDate()).padStart(2, '0');
    const hoursString = String(firstDay.getHours()).padStart(2, '0');
    const minutesString = String(firstDay.getMinutes()).padStart(2, '0');
    const secondsString = String(firstDay.getSeconds()).padStart(2, '0');
    
    
    const formattedDate = `${yearString}-${monthString}-${dayString}T${hoursString}:${minutesString}:${secondsString}`;
    
    return formattedDate;
}

function getLastDayOfMonth() {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    
    
    const firstDayNextMonth = new Date(year, month + 1, 1);
    
    
    const lastDay = new Date(firstDayNextMonth - 1);
    
    const yearString = lastDay.getFullYear();
    const monthString = String(lastDay.getMonth() + 1).padStart(2, '0');
    const dayString = String(lastDay.getDate()).padStart(2, '0');
    const hoursString = String(lastDay.getHours()).padStart(2, '0');
    const minutesString = String(lastDay.getMinutes()).padStart(2, '0');
    const secondsString = String(lastDay.getSeconds()).padStart(2, '0');
    
    const formattedDate = `${yearString}-${monthString}-${dayString}T${hoursString}:${minutesString}:${secondsString}`;
    
    return formattedDate;
}

function customLoadPage(url, idElement){
    $.ajax(url, {
        method: "GET",
        dataType: "html",
        success: function (data) {
            $(`#${idElement}`).html(data)
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });
}

function getNowDateTimeTypeLocalDateTime(){
    const localDate = new Date();
    const year = localDate.getFullYear();
    const month = String(localDate.getMonth() + 1).padStart(2, '0');
    const day = String(localDate.getDate()).padStart(2, '0');
    const hours = String(localDate.getHours()).padStart(2, '0');
    const minutes = String(localDate.getMinutes()).padStart(2, '0');
    const seconds = String(localDate.getSeconds()).padStart(2, '0');

    const localDateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    return localDateTime;
}
let checkDelete ;
function customDelete(url, id) {
    fetch(`${url}?id=${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (!res.ok) {
            if (res.status === 500) {
                alertGloable("Time out", "false");
            }
            throw new Error('Network response was not ok')
        }
        return res.json();
    }).then(data => {
        console.log(data)
        if (data.success) {
            checkDelete = true;
            alertGloable(data.message, "success");
        }else {
            checkDelete = false;
            alertGloable(data.message, "false");
        }
    }).catch(err => {
        console.error('There has been a problem with your fetch operation:', err);
    })
}
$(document).ready(function (){
    function updateClock() {
        const now = new Date();
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');
        document.getElementById('clock').textContent = `${hours}:${minutes}:${seconds}`;
    }

    setInterval(updateClock, 1000);
    updateClock(); // Cập nhật ngay khi trang vừa tải
    const today = new Date();

    const day = String(today.getDate()).padStart(2, '0'); // Lấy ngày, đảm bảo có 2 chữ số
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Lấy tháng, cần cộng thêm 1 vì tháng bắt đầu từ 0
    const year = today.getFullYear(); // Lấy năm

    const currentDate = `${day}/${month}/${year}`;
    document.getElementById('dateTime').textContent = `${currentDate}`;
    const daysOfWeek = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];

    const currentDay = daysOfWeek[today.getDay()]; // Lấy thứ hiện tại
    document.getElementById('dateThu').textContent = `${currentDay}`;
});