$(document).ready(function() {

    var dataProduct = {};

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('.product-main-image').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }


    $("#change-product-mainImage").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-product-mainImage")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function(res){
            NProgress.done();
            if(res.data.success) {
                $('.product-main-image').attr('src', res.data.link);
            }
        }, function(err){
            NProgress.done();
        });
    });



    $("#new-product").on("click", function () {
        dataProduct = {};
        $("#change-product-mainImage").val("");
        $('#input-product-name').val("");
        $("#input-product-category").val("");
        $("#input-product-brand").val("");
        $("#input-product-user").val("");
        $("#input-product-price").val("");
        $("#input-product-amount").val("");
        $("#input-product-shortDesc").val("");
        $("#input-product-desc").val("");
        $('.product-main-image').attr('src', 'https://www.vietnamprintpack.com/images/default.jpg');

    });


    $(".edit-product").on("click", function () {
        var pdInfo = $(this).data("product");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/product/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataProduct.id = res.data.data.id;
                $("#input-product-name").val(res.data.data.name);
                $("#input-product-category").val(res.data.data.categoryId);
                $("#input-product-brand").val(res.data.data.brandId);
                $("#input-product-user").val(res.data.data.userId);
                $("#input-product-amount").val(res.data.data.amount);
                $("#input-product-price").val(res.data.data.price);
                CKEDITOR.instances['input-product-shortDesc'].setData(res.data.data.shortDesc);
                CKEDITOR.instances['input-product-desc'].setData(res.data.data.description);
                if(res.data.data.mainImage != null) {
                    $('.product-main-image').attr('src', res.data.data.mainImage);
                }
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        });
    });



    $(".btn-save-product").on("click", function () {
        if($("#input-product-name").val() === "" || $("#input-product-amount").val() === "" || $("#input-product-price").val()==="" ||
            $("#input-product-category").val() == null || $("#input-product-brand").val() == null || $("#input-product-user").val() == null) {

            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        dataProduct.name = $('#input-product-name').val();
        dataProduct.categoryId = $("#input-product-category").val();
        dataProduct.brandId = $("#input-product-brand").val();
        dataProduct.userId = $("#input-product-user").val();
        dataProduct.mainImage = $('.product-main-image').attr('src');
        dataProduct.price = $("#input-product-price").val();
        dataProduct.amount = $("#input-product-amount").val();
        dataProduct.shortDesc = CKEDITOR.instances['input-product-shortDesc'].getData();
        dataProduct.description = CKEDITOR.instances['input-product-desc'].getData();
        NProgress.start();
        console.log(dataProduct.id);
        var linkPost = "/api/product/create";
        if(dataProduct.id) {
            linkPost = "/api/product/update/" + dataProduct.id;
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
                'Some error when saving product',
                'error'
            );
        });
    });



});