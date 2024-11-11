
const randomNumber = Math.floor(Math.random() * 900000) + 1;
let codeHd = "Gtel" + randomNumber;
let totalPrice = 0;
let arrPro = []
let checkFromSendMail = null;
let customMail = null;
let orderCode = null;
let dateOfPurchase = null;
let productRq = null;
let checkThanhToan = false;
let idHd =0;

// Thông tin dùng để gửi mail
let mailDich = "phamvanviet.95hh@gmail.com";
let mailCc = "nhung.nt@gtel.org.vn";
let addressMail = "";
let nameKh = "";
let phoneKh = "";
function funTT() {
    let qty = 0;
    if (localStorage.getItem('cart') !== null ) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        let totalQuantity = cart.reduce((total, item) => total + item.quantity, 0);
        qty = totalQuantity;
    }
    let formHovaten = $("#form_hovaten").val();

    let formSdt = $("#form_phone").val();
    let formDc1 = $("#form_address1").val();
    let formDc2 = $("#form_address2").val();
    let formEmail = $("#form_email").val();
    let nhanHang = $("#form_tinh").val() + " - " + $("#form_quan").val() + " - " + $("#form_xa").val() + " - " + $("#form_duong").val();
    let notes = $("#floatingTextarea2").val();
    addressMail = nhanHang;
    nameKh = formHovaten;
    phoneKh = formSdt;


    var bodyDataTT = JSON.stringify({ address: formDc1, addressReceive: nhanHang, code: codeHd, email: formEmail, full_name: formHovaten, note: notes, phone: formSdt, status: "1", qty:qty, total_price: totalPrice, productId: arrPro });
    console.log(bodyDataTT);
    fetch(`${localdomain}/api/asm/v1/create/hoadon`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Thiết lập header
        },
        body: bodyDataTT // Dữ liệu gửi đi
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        if (data.success) {
            idHd = data.data.idHd;
        } else {
            alert(data.message);
            window.location.reload();
        }
    })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
    $(".loadTT").removeClass("hien");
}
function sendMail(customMail, orderCode, dateOfPurchase, product) {
    var headersData = {
        'Content-Type': 'application/json'
    }
    var bodyData = {
        customMail: customMail,
        orderCode: orderCode,
        dateOfPurchase: dateOfPurchase,
        product: product
    }
    post(`${localdomain}/api/asm/v1/send-email`, headersData, bodyData);
}
$(document).ready(function () {

    let domainSocket = window.location.hostname;
    let portSocket = window.location.port;
    let potocoSocket = window.location.protocol;
    let itemThongBao = document.getElementById("thongBaoTT");
    // Kết nối với WebSocket
    
    connectWebSocket();
    function connectWebSocket() {

        const socket = new WebSocket(`wss://${domainSocket}:${portSocket}/asmSocket`);

        socket.onmessage = function (event) {
            const cleanedString = event.data.replace(/[{}]/g, "");
            const pairs = cleanedString.split(", ");
            const obj = {};
            pairs.forEach(pair => {
                const [key, value] = pair.split("=");
                obj[key] = isNaN(value) ? value : Number(value);
            });

            if (obj.action === "CHANGE_BALANCE") {
                let thongbao = `<div>
                        <i class="fa fa-check-circle-o thanhtoanTTi" aria-hidden="true"></i>
                    </div>
                    <div class="mb-5 thanhtoanTTTitle">Thanh toán thành công</div>`;
                itemThongBao.innerHTML = thongbao;
                checkThanhToan = true;
                localStorage.removeItem('cart');
                var bodyDataTT = JSON.stringify({ id : idHd, status: "2"});
                fetch(`${localdomain}/api/asm/v1/create/updateHoadon`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // Thiết lập header
                    },
                    body: bodyDataTT // Dữ liệu gửi đi
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                }).then(data => {
                    if (data.success) {
                    
                    } else {
                        alert(data.message);
                        window.location.reload();
                    }
                })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });
                var bodyDataTT2 = JSON.stringify({ customerEmail : mailDich, ccEmail: mailCc, orderCode: codeHd,dateOfPurchase : getNowDateTimeTypeLocalDateTime(),product: productRq, totalMoney: totalPrice, address:addressMail,status:"Đã thanh toán", nameKh:nameKh, phoneKh: phoneKh});
                fetch(`${localdomain}/api/asm/v1/send-email`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // Thiết lập header
                    },
                    body: bodyDataTT2 // Dữ liệu gửi đi
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                }).then(data => {
                    if (data.success) {

                    } else {
                        alert(data.message);
                        window.location.reload();
                    }
                })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });

            } else if (Object.keys(obj).length === 0) {
                let thongbao2 = `<div>
                        <i class="fa fa-times-circle-o thanhtoanTBi" aria-hidden="true"></i>
                    </div>
                    <div class="mb-5 thanhtoanTBTitle">Thanh toán không thành công</div>`;
                itemThongBao.innerHTML = thongbao2;
            }
            // document.getElementById('webhookData').innerText = obj.orderId;
        };
        socket.onopen = function () {
            console.log("WebSocket connection established.");
        };

        socket.onclose = function () {
            console.log("WebSocket connection closed.");
            setTimeout(connectWebSocket, 3000);
        };
    }



});
$(document).ready(function () {

    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let cartItemss = document.getElementById('qty-cart');

    if (localStorage.getItem('cart') !== null && checkCart > 0) {
        $(document).ready(function () {
            function displayCart() {

                let cart = JSON.parse(localStorage.getItem('cart')) || [];

                let cartItems = document.getElementById('table-carts-tbody');
                let content = '';

                cart.forEach(item => {
                    arrPro.push(item.idProductCart);
                    let money = (item.priceProductCart * 1).toLocaleString('vi-VN');
                    productRq = item.nameProductCart;
                    content += `
                    
                    <tr >
                            <input type="hidden" value="${item.idProductCart}" class="productId">
                            <td class="cart-info-product">
                                <div class="content-item">
                                    <div class="content-item-box-img">
                                        <img src="/web/imgs/products/g3-2.png" alt=""
                                            class="content-item-img">
                                    </div>
                                    <div class="content-item-info">
                                        <p class="content-item-info-title" >${item.nameProductCart}</p>
                                        <b class="content-item-info-price" >${money} VNĐ</b>- <i>(Giá đã bao gồm thuế
                                            VAT)</i>
                                        <a href="#" class="content-item-info-close" data-id="${item.id}">Huỷ</a>
                                    </div>
                                </div>
    
                            </td>
                            <td class="cart-sl-product">
                                <div class="content-item">
                                    <a class="box-icon-down" data-id="${item.id}" data-price="${item.priceProductCart}">
                                        <i class="fa fa-long-arrow-down icon-down" aria-hidden="true"></i>
                                    </a>
                                    <div class="box-input">
                                        <input type="text" id="input-cart" readonly class="input-cart" value="${item.quantity}">
                                    </div>
                                    <a class="box-icon-up"  data-id="${item.id}" data-price="${item.priceProductCart}">
                                        <i class="fa fa-long-arrow-up icon-up" aria-hidden="true"></i>
                                    </a>
                                </div>
                            </td>
                            <td class="cart-price-product">
                                <div class="content-item-price">
                                    <b>${(item.priceProductCart * item.quantity).toLocaleString("vi-VN")} VNĐ</b>
                                </div>
                            </td>
                        </tr>
                `;
                });
                content += `<tr>
                            <td colspan="2">
                                <div class="table-box-total-price">
                                    <b class="title-total-price">Tổng tiền</b><label>(Bao gồm thuế VAT)</label>
                                </div> 
                            </td>
                            <td class="cart-box-total-price">
                                <div class="content-item-price">
                                    <b id="totalPrice"></b>
                                </div>
                            </td>
                        </tr>` ;
                cartItems.innerHTML = content;
                $(document).ready(function () {
                    const input = document.getElementById("input-cart");
                    input.addEventListener("input", function (event) {
                        this.value = this.value.replace(/[^0-9]/g, ''); // Loại bỏ ký tự không phải số
                    });
                })

                $(".box-icon-up").click(function () {
                    let id = $(this).attr("data-id");
                    let product = cart.find(item => item.id === id);
                    if (product) {
                        product.quantity += 1;
                        localStorage.setItem('cart', JSON.stringify(cart));
                        $(this).parent().find(".box-input input").attr("value", product.quantity)

                        $(this).parent().parent().parent().find("td.cart-price-product .content-item-price b").html((product.quantity * Number(product.priceProductCart)).toLocaleString("vi-VN") + " VNĐ");
                    }
                    let totalQuantity = cart.reduce((total, item) => total + item.quantity, 0);
                    checkGH += totalQuantity;
                    checkCart += totalQuantity
                    cartItemss.innerHTML = totalQuantity;
                    let totalAmount = cart.reduce((total, item) => total + (item.priceProductCart * item.quantity), 0);
                    $("#totalPrice").html(totalAmount.toLocaleString('vi-VN') + " VNĐ");
                    totalPrice = totalAmount;
                })
                $(".box-icon-down").click(function () {
                    let id = $(this).attr("data-id");
                    let product = cart.find(item => item.id === id);
                    if (product && product.quantity > 1) {
                        product.quantity -= 1;
                        localStorage.setItem('cart', JSON.stringify(cart));

                        $(this).parent().find(".box-input input").attr("value", product.quantity)
                        $(this).parent().parent().parent().find("td.cart-price-product .content-item-price b").html((product.quantity * Number(product.priceProductCart)).toLocaleString("vi-VN") + " VNĐ");
                    } else if (product && product.quantity === 1) {
                        let checkConfirm = confirm("Số lượng chỉ còn 1 bạn có thực sự muốn xoá sản phẩm này");
                        if (checkConfirm) {
                            cart = cart.filter(item => item.id !== id);
                            localStorage.setItem('cart', JSON.stringify(cart));
                            displayCart();
                            window.location.href = `${localdomain}/web/cart`;
                        }
                    }
                    let totalQuantity = cart.reduce((total, item) => total + item.quantity, 0);
                    checkGH += totalQuantity;
                    checkCart += totalQuantity
                    cartItemss.innerHTML = totalQuantity;
                    let totalAmount = cart.reduce((total, item) => total + (item.priceProductCart * item.quantity), 0);
                    $("#totalPrice").html(totalAmount.toLocaleString('vi-VN') + " VNĐ");
                    totalPrice = (product.quantity * Number(product.priceProductCart))
                })
                function updateTotalAmount() {
                    let cart = JSON.parse(localStorage.getItem('cart')) || [];
                    let totalAmount = cart.reduce((total, item) => total + (item.priceProductCart * item.quantity), 0);
                    $("#totalPrice").html(totalAmount.toLocaleString('vi-VN') + " VNĐ"); // làm tròn đến 2 chữ số sau dấu phẩy
                    totalPrice = totalAmount;
                }
                updateTotalAmount();

                $(".content-item-info-close").click(function () {
                    let id = $(this).attr("data-id");
                    let cart = JSON.parse(localStorage.getItem('cart')) || [];
                    cart = cart.filter(item => item.id !== id);
                    localStorage.setItem('cart', JSON.stringify(cart));
                    displayCart();
                    updateTotalAmount();
                    window.location.href = `${localdomain}/web/cart`;
                })
            }
            displayCart();
        })
    } else {
        let coin = `
            <div class="d-flex justify-content-center ">
                <div class="box-cart-shopping">
                    <i class="fa fa-shopping-cart link-cart mb-3" aria-hidden="true"></i>
                    <p class="mb-3"> Không có sản phẩm nào trong giỏ hàng</p>
                    <a href="/index" class="cart-back-home mb-3"> Về trang chủ </a>
                    <span class="mb-3 box-cart-shopping-detail">Thông tin liên hệ: <a href="tel:0559461679" style="color :#288ad6">0559461679</a> hoặc <a style="color :#288ad6" href="tel:02499990251">02499990251</a> để được hỗ trợ</span>
                </div>
            </div>
            `;
        let cartItems = document.getElementById('container-box-cart');
        cartItems.innerHTML = coin;
        $(".cart-back-home").click(function () {
            window.location.href = `${localdomain}/index`;
        })
    }
    $(document).ready(function () {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        let checkLenghtPhone = 0;
        const input = document.getElementById("form_phone");
        input.addEventListener("input", function (event) {
            this.value = this.value.replace(/[^0-9]/g, ''); // Loại bỏ ký tự không phải số
            checkLenghtPhone = this.value.length;
        });
        function startCountdown(duration, status) {
            if (status === "on") {
                let timer = duration, minutes, seconds;
                const countdownElement = document.getElementById('timer');
                interval = setInterval(function () {
                    minutes = Math.floor(timer / 60);
                    seconds = timer % 60;
                    minutes = minutes < 10 ? '0' + minutes : minutes;
                    seconds = seconds < 10 ? '0' + seconds : seconds;
                    countdownElement.innerHTML = ` <span class="fontCountDown">${minutes}</span> : <span class="fontCountDown">${seconds}</span>`;
                    if (--timer < 0) {
                        clearInterval(interval);
                        countdownElement.innerHTML = ` <span class="fontCountDown">00</span> : <span class="fontCountDown">00</span>`;
                        alert("Đã hết hạn thời gian hiệu lực của QR");
                    }
                }, 1000);
            }
            if (status === "off") {
                clearInterval(interval);
            }
        }
        $(".btn-orderBill").click(function () {
            setTimeout(funTT(), 5000);
            let checkFrom = true;
            let arrProduct = [];
            let fullName = $("#form_hovaten").val();
            let phone = $("#form_phone").val();
            let form_address1 = $("#form_address1").val();
            let form_address2 = $("#form_address2").val();
            let email = $("#form_email").val();
            let addressReceive = $("#form_duong").val() + "," + $("#form_xa").val() + "," + $("#form_quan").val() + "," + $("#form_tinh").val()
            let note = $("#floatingTextarea2").val();
            let check = document.getElementsByClassName("productId");
            checkFromSendMail = document.getElementById("formCheckBill");

            let diachinhan = "";
            let tinhNhan = $("#form_tinh").val();
            let msg = "Không được để trống trường này";


            if (fullName === "" || fullName === null) {
                checkFrom = false;
                $("#fullNameError").html(msg)
            }
            if (phone === "" || phone === null) {
                checkFrom = false;
                $("#phoneNumberError").html(msg)
            } else if (checkLenghtPhone < 9) {
                checkFrom = false;
                $("#phoneNumberError").html("Số điện thoại không được nhỏ hơn 9 ký tự")
            }

            if (form_address1 === "" || form_address1 === null) {
                checkFrom = false;
                $("#formAddress1Error").html(msg)
            }
            if (form_address2 === "" || form_address2 === null) {
                checkFrom = false;
                $("#formAddress2Error").html(msg)
            }
            // if (tinhNhan === "" || tinhNhan === null) {
            //     checkFrom = false;
            //     $("#formDiaChiNhan").html(msg)
            // }
            $("#form_hovaten").keyup(function () {
                $("#fullNameError").html("")
            });
            $("#form_phone").keyup(function () {
                $("#phoneNumberError").html("")
            });
            $("#form_address1").keyup(function () {
                $("#formAddress1Error").html("")
            });
            $("#form_address2").keyup(function () {
                $("#formAddress2Error").html("")
            });
            $("#form_tinh").change(function () {
                $("#formDiaChiNhan").html("")
            })
            const checkbox = document.getElementById("formCheckvb");


            if (checkFrom === false) {
                alert("Cần nhập đủ trường thông tin yêu cầu");
            } else if (!checkbox.checked) {
                alert("Bạn cần đồng ý với điều khoản trước khi đặt hàng");
            } else {

                if (checkFromSendMail.checked) {
                    customMail = $("#form_email").val();
                    orderCode = codeHd;
                    dateOfPurchase = getCurrentDate();
                    product = productRq;

                    // sendMail(customMail, orderCode, dateOfPurchase, product);
                }

                $(".loadTT").addClass("hien");
                const fiveMinutes = 5 * 60;
                startCountdown(fiveMinutes, "on");

                let token = "";
                let endPointGetToken = `${localdomain}/api/asm/v1/create/virtual/acc`;
                var bodyDataImg = JSON.stringify({
                    accountName: "Tổng công ty công nghệ viễn thông toàn cầu",
                    mapId: "001200009180", mapType: "CCCD", accountType: "O", bankCode: "BIDV", maxAmount: 0, minAmount: 0, equalAmount: totalPrice,
                    description: codeHd,
                    token: token
                });
                fetch(endPointGetToken, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // Thiết lập header
                    },
                    body: bodyDataImg
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                }).then(datax => {
                    console.log(datax);
                    const imgElement = document.getElementById('img_qr');
                    let imgRes = datax.qrQuickLink;
                    imgElement.src = 'data:image/png;base64,'+imgRes;
                }).catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
                $(document).ready(function () {
                    let totalMoneyBill = document.getElementById("totalMoneyBill");
                    totalMoneyBill.innerHTML = totalPrice.toLocaleString('vi-VN') + " VNĐ";
                    let totalMoneyBill1 = document.getElementById("totalMoneyBill1");
                    totalMoneyBill1.innerHTML = totalPrice.toLocaleString('vi-VN') + " VNĐ";
                    let codeBill = document.getElementById("codeBill");
                    codeBill.innerHTML = codeHd;
                    let codeBill1 = document.getElementById("codeBill1");
                    codeBill1.innerHTML = `Thanh toán đơn hàng ${codeHd}`;

                })
                $(".loadTT").click(function () {
                    $(this).removeClass("hien");
                    startCountdown(null, "off");
                })
                $(".box-loadTT").click(function (e) {
                    e.stopPropagation();
                })
                $(".close-from-gtelPay-tt").click(function () {
                    $(".loadTT").removeClass("hien");
                    if (checkThanhToan){
                        location.reload(true);
                    }
                    startCountdown(null, "off");
                })
            }



            // for (let i = 0; i < check.length; i++) {
            //     arrProduct.push(check[i].getAttribute("value"))
            // }
            // const randomNumber = Math.floor(Math.random() * 100000) + 1;
            // let codeBody = "SP_" + randomNumber;
            // if (chekdk === true) {
            //     let endpoint = `${localdomain}/api/asm/v1/create/hoadon`;
            //     var bodyRqUd = JSON.stringify({
            //         address: form_address1,
            //         code: codeBody,
            //         email: email,
            //         full_name: fullName,
            //         status: "1",
            //         total_price: 12000,
            //         productId: arrProduct
            //     }); fetch(endpoint, {
            //         method: "POST",
            //         headers: {
            //             'Content-Type': 'application/json'
            //         },
            //         body: bodyRqUd
            //     }).then(response => {
            //         if (!response.ok) {
            //             throw new Error('Network response was not ok');
            //         }
            //         return response.json();
            //     }).then(data => {

            //         if (data.success) {
            //             localStorage.removeItem('cart')
            //             window.location.href = `${localdomain}/web/finsh?code=${codeBody}`;
            //         }
            //     }).catch(error => {
            //         console.error('There has been a problem with your fetch operation:', error);
            //     })
            // } else {
            //     alert("Bạn cần đồng ý với điều khoản trước khi đặt hàng");
            // }

        })
        $(".btn-cart-add").click(function () {
            window.location.href = `${localdomain}/index`;
        })
    })
    $(document).ready(function () {
        let district = $("#form_quan");
        let ward = $("#form_xa");
        $("#form_tinh").change(function () {
            const selectedOption = this.options[this.selectedIndex];
            let idProvince = selectedOption.getAttribute("data-code");
            fetch(`${localdomain}/web/district/getDistrict?provincesId=${idProvince}`, {
                method: "GET",
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
        district.change(function () {
            const selectedOption = this.options[this.selectedIndex];
            let idProvince = selectedOption.getAttribute("data-code");
            fetch(`${localdomain}/web/ward/getList?districtId=${idProvince}`, {
                method: "GET",
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
    })
    $(document).ready(function () {
        if (username) {
            $("#form_hovaten").attr("value", fullName);
            $("#form_phone").attr("value", phone);
            $("#form_address1").attr("value", address);
            $("#form_address2").attr("value", address);
            $("#form_email").attr("value", email);
        }
    })
    $(document).ready(function () {
        $(".nav-item").click(function () {
            let idMenu = $(this).find("button").attr("data-item");
            $(this).addClass("hien").siblings().removeClass("hien");
            if (idMenu === "item1") {
                $("." + idMenu).addClass("show")
                $(".item2").removeClass("show");

            } else {
                $("." + idMenu).addClass("show")
                $(".item1").removeClass("show");
            }
        })


    })
})
$(document).ready(function () {
    const checkbox = document.getElementById('formCheckBill');
    checkbox.addEventListener('click', function () {
        if (checkbox.checked) {
            $(".box-send-bill").addClass("hien")
        } else {
            $(".box-send-bill").removeClass("hien")
        }
    });
})
