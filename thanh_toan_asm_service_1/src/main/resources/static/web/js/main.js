
let header = document.getElementById('header');
let header2 = document.getElementById('header2');
console.log(header2);
if (header2 !== null){
    function getHeaderOffsetFromTop2() {
        const rect = header2.getBoundingClientRect(); // Tọa độ của header so với cửa sổ
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop; // Vị trí cuộn trang
        const headerOffset = rect.top + scrollTop; // Tổng khoảng cách đến đỉnh của trang
        return headerOffset;
    }

// Gọi hàm và in kết quả
    if (getHeaderOffsetFromTop2() > 120){
        $("#header2").addClass("scroll");
    }
}else if(header !== null){
    function getHeaderOffsetFromTop() {
        const rect = header.getBoundingClientRect(); // Tọa độ của header so với cửa sổ
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop; // Vị trí cuộn trang
        const headerOffset = rect.top + scrollTop; // Tổng khoảng cách đến đỉnh của trang
        return headerOffset;
    }

// Gọi hàm và in kết quả
    if (getHeaderOffsetFromTop() > 120){
        $("#header").addClass("scroll");
    }
}

if (window.location.hash) {
    const hash = window.location.hash;
    console.log(hash)
    // Cuộn đến phần tử tương ứng với hash
    const targetElement = document.querySelector(hash);
    if (targetElement) {
        targetElement.scrollIntoView({ behavior: 'smooth' });
    }
    // Xóa hash khỏi URL mà không tải lại trang
    history.replaceState(null, null, window.location.pathname);
}


$(document).ready(function () {
    $('.validate ').validationEngine();
    setTimeout(function () {
        $('#show_success_mss').fadeOut().empty();
    }, 9000);
});
$(document).ready(function () {
    jQuery('.list_phi').on('click', function () {
        jQuery(this).toggleClass('tab_open');
    });
})
$(document).ready(function () {
    $(function () {
        toastr.options.escapeHtml = false;
        toastr.options.closeButton = true;
        toastr.options.positionClass = "toast-bottom-right";
        toastr.options.timeOut = 5000;
        toastr.options.showMethod = 'fadeIn';
        toastr.options.hideMethod = 'fadeOut';
    });
    
})
$(document).ready(function () {
    $('.owl-wrapper').owlCarousel({
        loop: true,
        margin: 30,
        nav: true,
        dots: false,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 3
            }
        }
    })
    $('.slide_news').owlCarousel({
        loop: true,
        margin: 30,
        nav: false,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 3
            }
        }
    })
    
})
$(document).ready(function () {
    
    $("#signupuser").click(function () {
        dangky();
    });
    function dangky() {
        var valid = jQuery("#form_contact").validationEngine('validate');
        jQuery('#form_contact').validationEngine({
            focusFirstField: true
        });
        
    }
});
$(document).ready(function () {
    function addToCart(id, nameProductCart, priceProductCart, idProductCart, imageCart) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        let product = { id, nameProductCart, priceProductCart, idProductCart, imageCart, quantity: 1 };
        
        // Kiểm tra sản phẩm có trong giỏ hàng chưa
        let existingProduct = cart.find(item => item.id === id);
        if (existingProduct) {
            existingProduct.quantity += 1;
        } else {
            cart.push(product);
        }
        localStorage.setItem('cart', JSON.stringify(cart));
    }
    $(".btn-order").click(function (e) {
        e.preventDefault();
        let checkOrder = true;
        const randomNumber = Math.floor(Math.random() * 100000) + 1;
        let nameProduct = $(this).attr("data-name");
        let idCart = $(this).attr("data-cart");
        let priceProduct = $(this).attr("data-price");
        let idProduct = $(this).attr("data-id");
        addToCart(idCart, nameProduct, priceProduct, idProduct, "anh1.png");
        window.location.href = `${localdomain}/web/cart`;
        
        // let endpointCheck = `http://${domain}:${port}/api/asm/v1/checkOrder`
        // var bodyRqCheck = JSON.stringify({
        //     status : "1",
        //     nameProduct : nameProduct,
        //     userId : userId,
        //     productId : idProduct
        // });
        // fetch(endpointCheck, {
        //     method : "POST",
        //     headers : {
        //         "Authorization" : `Bearer ${token}`,
        //         'Content-Type': 'application/json'
        //     },
        //     body : bodyRqCheck
        // }).then(response => {
        //     if(!response.ok){
        //         throw new Error('Network response was not ok');
        //     }
        //     return response.json();
        // }).then(data => {
        //     if (data.success){
        //         let endpoint = `http://${domain}:${port}/api/asm/v1/update/order`;
        //         var bodyRqUd = JSON.stringify({
        //             qty : 1,
        //             orderId : data.data.id
        //         });fetch(endpoint, {
        //             method : "POST",
        //             headers : {
        //                 "Authorization" : `Bearer ${token}`,
        //                 'Content-Type': 'application/json'
        //             },
        //             body : bodyRqUd
        //         }).then(response => {
        //             if(!response.ok){
        //                 throw new Error('Network response was not ok');
        //             }
        //             return response.json();
        //         }).then(data => {
        //
        //             if(data.success){
        //                 window.location.href = `http://${domain}:${port}/web/cart?id=${userId}`;
        //             }
        //         }).catch(error => {
        //             console.error('There has been a problem with your fetch operation:', error);
        //         })
        
        //     }else {
        //         let endpoint = `http://${domain}:${port}/api/asm/v1/create/order`
        //         var bodyRq = JSON.stringify({
        //             status : "1",
        //             code: "SP_"+randomNumber,
        //             nameProduct : nameProduct,
        //             qty: 1,
        //             totalPrice : priceProduct,
        //             user_id : userId,
        //             product_id : idProduct
        //         });
        //         fetch(endpoint, {
        //             method : "POST",
        //             headers : {
        //                 "Authorization" : `Bearer ${token}`,
        //                 'Content-Type': 'application/json'
        //             },
        //             body : bodyRq
        //         }).then(response => {
        //             if(!response.ok){
        //                 throw new Error('Network response was not ok');
        //             }
        //             return response.json();
        //         }).then(data => {
        //             if(data.success){
        //                 window.location.href = `http://${domain}:${port}/web/cart?id=${userId}`;
        //             }
        //         }).catch(error => {
        //             console.error('There has been a problem with your fetch operation:', error);
        //         })
        //     }
        // }).catch(error => {
        //     console.error('There has been a problem with your fetch operation:', error);
        // })
        
        
    })
    
})
$(document).ready(function () {
    let endpoint1 = `${localdomain}/web/get/productList?status=1`
    fetch(endpoint1, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(
        response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        }
    ).then(data => {
        data.data.map(value => console.log(value.link))
        $("#order-sp1").attr("data-id", data.data[0].id).attr("data-price", data.data[0].productPrice).attr("data-name", data.data[0].productName).attr("data-cart", "SP_" + data.data[0].id)
    }).catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    })
})
$(document).ready(function () {
    
    if (fullName !== "undefined" && fullName !== null) {
        $("#box_login").html(`
            <div class="d-flex flex-column">
            <b><i class="fa fa-user" aria-hidden="true" style="padding-right: 6px;"></i>${fullName}</b>
            <a class="btn-logout" style="margin: 0; padding: 0; font-size: 12px; cursor: pointer;">Đăng xuất <i class="fa fa-sign-out" aria-hidden="true"></i></a>
            </div>
        `);
        $("#box_login-phone").html(`
            <div class="d-flex flex-column">
            <b><i class="fa fa-user" aria-hidden="true" style="padding-right: 6px;"></i>${fullName}</b>
            <a class="btn-logout" style="margin: 0; padding: 0; font-size: 12px; cursor: pointer;">Đăng xuất <i class="fa fa-sign-out" aria-hidden="true"></i></a>
            </div>
        `);
        $(".btn-logout").click(function () {
            localStorage.removeItem("username");
            localStorage.removeItem("userId");
            localStorage.removeItem("fullName");
            localStorage.removeItem("role");
            sessionStorage.removeItem("username");
            sessionStorage.removeItem("userId");
            sessionStorage.removeItem("fullName");
            sessionStorage.removeItem("role");
            window.location.reload();
        })
    }
    
})
$(document).ready(function () {
    
    $(".open-nav-mb").click(function () {
        if ($(this).hasClass("fa-bars")) {
            $(this).removeClass("fa-bars");
            $(this).addClass("fa-times-circle-o");
            $(".mobi-nav").addClass("hien");
        } else if ($(this).hasClass("fa-times-circle-o")) {
            $(this).removeClass("fa-times-circle-o");
            $(this).addClass("fa-bars");
            $(".mobi-nav").removeClass("hien");
        }
    })
})
window.addEventListener("scroll", function () {
    var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    var scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
    if (scrollTop == 120) {
        $(".header2-scoll").addClass("scrollTest")
    } else if (scrollTop < 120) {
        $(".header2-scoll").removeClass("scrollTest")
    }
});
$(document).ready(function () {
    
    let msg = "Không được để trống trường này";
    $(document).ready(function () {
        const input = document.getElementById("home-form-phone");
        input.addEventListener("input", function (event) {
            this.value = this.value.replace(/[^0-9]/g, ''); // Loại bỏ ký tự không phải số
            checkLenghtPhone = this.value.length;
        });
    })
    
    $("#guiFrom").click(function () {
        let checkFormSend = true;
        let fullName = $("#home-form-fullName").val();
        let phone = $("#home-form-phone").val();
        let email = $("#home-form-email").val();
        let address = $("#home-form-address").val();
        
        if (fullName === "" || fullName === null) {
            checkFormSend = false;
            $("#errorHomeFullName").html(msg)
        }
        if (phone === "" || phone === null) {
            checkFormSend = false;
            $("#errorHomePhone").html(msg)
        } else if (phone.length > 10) {
            console.log(phone.length)
            checkFormSend = false;
            $("#errorHomePhone").html("Số điện thoại không được lớn hơn 10 ký tự")
        }
        if (address === "" || address === null) {
            checkFormSend = false;
            $("#errorHomeAddress").html(msg)
        }
        if (email === "" || email === null) {
            checkFormSend = false;
            $("#errorHomeEmail").html(msg)
        }
        $("#home-form-address").keyup(function () {
            $("#errorHomeAddress").html("")
        });
        $("#home-form-phone").keyup(function () {
            $("#errorHomePhone").html("")
        });
        $("#home-form-fullName").keyup(function () {
            $("#errorHomeFullName").html("")
        });
        $("#home-form-email").keyup(function () {
            $("#errorHomeEmail").html("")
        });
        
        console.log(checkFormSend)
        
        if (checkFormSend) {
            alert("Gửi Form thành công");
        }
        
    })
})