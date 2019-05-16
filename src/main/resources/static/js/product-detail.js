$(document).ready(function() {

    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }

    var delete_cookie = function(name) {
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    };

    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }

    function checkCookie() {
        var user = getCookie("username");
        if (user != "") {
            alert("Welcome again " + user);
        } else {
            user = prompt("Please enter your name:", "");
            if (user != "" && user != null) {
                setCookie("username", user, 365);
            }
        }
    }


    $(".list-product-image").on("click",function () {
        $("#test1").attr("src",$(this).data("image"));
    });


    $(".add-to-cart").on("click", function () {
        var dataCart = {};
        var pdInfo = $(this).data("product");
        dataCart.amount = document.getElementById('getAmount').value;
        dataCart.productId = pdInfo;
        dataCart.guid = getCookie("guid");
        var pdAmountInStore = $(this).val();
        var pdAmountInCartProduct = $('#getAmountInCartProduct').val();
        //console.log(pdAmountInCartProduct);

        var dataProduct = {};
        var sum = parseInt(dataCart.amount) + parseInt(pdAmountInCartProduct);


        if (sum > parseInt(pdAmountInStore)) {
            swal(
                'Fail',
                'Not enough product in store'
            );
        }
        else{
        NProgress.start();

        var linkPost = "/api/cart-product/add";

        axios.post(linkPost, dataCart).then(function (res) {
            NProgress.done();
            if (res.data.success) {
                swal(
                    'Success',
                    res.data.message,
                    'success'
                ).then(function () {
                    //up date amount of product was bought
                    // NProgress.start();
                    // dataProduct.amount = pdAmountInStore - dataCart.amount;
                    // var linkUpdateProductAmountInDB = "/api/product/updateAmount/"+pdInfo;
                    // axios.post(linkUpdateProductAmountInDB, dataProduct);
                    // NProgress.done();
                    location.reload();
                });
            } else {
                swal(
                    'Fail',
                    res.data.message,
                    'error'
                );
            }
        }, function (err) {
            NProgress.done();
            swal(
                'Error',
                'Fail',
                'error'
            );
        });
    }
    });

    $(".btn-review-submit").on("click", function () {
        if($("#review-title").val() ==="" || $("#review-comment").val() ==="" || $('#starScore').val() ==="" ){
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }
        var dataReview = {};
        dataReview.title = $('#review-title').val();
        dataReview.star = $('#starScore').val();
        dataReview.review = $('#review-comment').val();
        dataReview.productId = $(this).data("product");
        NProgress.start();
        var linkPost = "/api/review/create";
        axios.post(linkPost, dataReview).then(function (res) {
            NProgress.done();
            if (res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function () {
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
                    'Some error when saving review',
                    'error'
                );
        });
    });


});