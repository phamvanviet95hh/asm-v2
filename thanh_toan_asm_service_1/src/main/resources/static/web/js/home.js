$(document).ready(function (){
    $(".btn-order").click(function (e){
        e.preventDefault();
        window.location.href = `http://${domain}:${port}/web/cart`;
    })
})