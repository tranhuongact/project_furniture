$(document).ready(function() {

    var dataOrder = {};


    $(".edit-order").on("click", function () {
        var orderInfo = $(this).data("order");
        console.log(orderInfo);
        NProgress.start();
        axios.get("/api/order/detail/" + orderInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataOrder.id = res.data.data.orderId;
                $("#input-customer-name").val(res.data.data.orderId);
                $("#input-product-name").val(res.data.data.productId);
                $("#input-amount").val(res.data.data.amount);
                $("#input-price").val(res.data.data.price);
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        })
    });



    $(".btn-save-order").on("click", function () {
        if($("#input-product-name").val() === "" || $("#input-customer-name").val() === "" || $("#input-price").val()==="" || $('#input-amount').val()==="") {
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
        NProgress.start();
        console.log(dataOrder.id);
        var linkPost = "/api/order/create";
        if(dataOrder.id) {
            linkPost = "/api/order/update/" + dataOrder.id;
        }

        axios.post(linkPost, dataProduct).then(function(res){
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
        })
    });



});