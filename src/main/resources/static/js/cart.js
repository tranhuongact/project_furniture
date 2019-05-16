$(document).ready(function() {
    $(".getAmount").change( function () {
        dataCartProduct = {};
        dataCartProduct.id = $(this).data("id");
        dataCartProduct.amount = $(this).val();
        var pdAmountInStore = $(this).parent().children("button").children("input").val();


        if (parseInt(dataCartProduct.amount) > parseInt(pdAmountInStore)) {
            swal(
                'Fail',
                'Không đủ sản phẩm trong kho'
            ).then(function() {
                location.reload();
            });
        }
        else{
            NProgress.start();

            var linkPost = "/api/cart-product/update";

            axios.post(linkPost, dataCartProduct).then(function(res){
                NProgress.done();
                if(res.data.success) {
                    location.reload();
                } else {
                    swal(
                        'Fail',
                        res.data.message,
                        'error'
                    ).then(function() {
                        location.reload();
                    });
                }
            }, function(err){
                NProgress.done();
                swal(
                    'Error',
                    'Fail',
                    'error'
                );
            });
        }
    });

    $(".change-product-amount").on("click", function () {
        dataCartProduct = {};
        dataCartProduct.id = $(this).parent().children("input").data("id");
        dataCartProduct.amount = $(this).parent().children("input").val();
        var pdAmountInStore = $(this).children("input").val();

        if (parseInt(dataCartProduct.amount) > parseInt(pdAmountInStore)) {
            swal(
                'Fail',
                'Không đủ sản phẩm trong kho'
            ).then(function() {
                location.reload();
            });
        }
        else{
            NProgress.start();

            var linkPost = "/api/cart-product/update";

            axios.post(linkPost, dataCartProduct).then(function(res){
                NProgress.done();
                if(res.data.success) {
                    location.reload();
                } else {
                    swal(
                        'Fail',
                        res.data.message,
                        'error'
                    ).then(function() {
                        location.reload();
                    });
                }
            }, function(err){
                NProgress.done();
                swal(
                    'Error',
                    'Fail',
                    'error'
                );
            });
        }



    });
    $(".delete-cart-product").on("click",function(){
        var pdInfo = $(this).data("id");

        NProgress.start();
        var linkGet = "/api/cart-product/delete/"+pdInfo;
        axios.get(linkGet).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Success',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Fail',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Fail',
                'error'
            );
        });
    });


});