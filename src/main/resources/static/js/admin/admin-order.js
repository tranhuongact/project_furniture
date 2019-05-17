$(document).ready(function() {

    var dataOrder = {};


    $(".edit-order").on("click", function () {
        var orderInfo = $(this).data("order");
        console.log(orderInfo);
        NProgress.start();
        axios.get("/api/order/detail/" + orderInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataOrder.id = res.data.data.id;
                $("#input-customer-name").val(res.data.data.customerName);
                $("#input-email").val(res.data.data.email);
                $("#input-address").val(res.data.data.address);
                $("#input-phone-number").val(res.data.data.phoneNumber);
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        })
    });



    $(".btn-save-order").on("click", function () {
        if($("#input-customer-name").val() === "" || $("#input-email").val() === "" || $("#input-address").val()==="" || $('#input-phone-number').val()==="") {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        // dataOrder.or = $('#input-product-name').val();
        // dataOrder.shortDesc = $('#input-product-desc').val();
        // dataProduct.categoryId = $("#input-product-category").val();
        // dataProduct.mainImage = $('.product-main-image').attr('src');
        // dataProduct.price = $("#input-product-price").val();
        dataOrder.customerName = $('#input-customer-name').val();
        dataOrder.email = $('#input-email').val();
        dataOrder.address = $("#input-address").val();
        dataOrder.phoneNumber = $('#input-phone-number').val();

        NProgress.start();
        console.log(dataOrder.id);
        var linkPost = "/api/order/create";
        if(dataOrder.id) {
            linkPost = "/api/order/update/" + dataOrder.id;
        }

        axios.post(linkPost, dataOrder).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when saving order',
                'error'
            );
        });
    });

    $(".delete-order").on("click", function () {
        var orderInfo = $(this).data("order");
        console.log(orderInfo);
        NProgress.start();
        var result = confirm("Do you want to delete?");
        // var linkPost = "/api/product-image/delete/" + pdImgInfo;
        axios.get("/api/order/delete/" + orderInfo).then(function(res){
            NProgress.done();
            if(result == true && res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when delete order',
                'error'
            );
        });
    });



});