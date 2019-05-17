$(document).ready(function() {

    var dataProductImage = {};

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('.product-image').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }


    $("#change-product-image").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-product-image")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function(res){
            NProgress.done();
            if(res.data.success) {
                $('.product-image').attr('src', res.data.link);
            }
        }, function(err){
            NProgress.done();
        });
    });


    $("#new-product-image").on("click", function () {
        dataProductImage = {};
        $("#change-product-image").val("");
        $("#input-product").val("");
        $('.product-image').attr('src', 'https://www.vietnamprintpack.com/images/default.jpg');

    });


    $(".edit-product-image").on("click", function () {
        var pdImgInfo = $(this).data("productImage");
        console.log(pdImgInfo);
        NProgress.start();
        axios.get("/api/product-image/detail/" + pdImgInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataProductImage.id = res.data.data.id;
                $("#input-product").val(res.data.data.productId);
                if(res.data.data.link != null) {
                    $('.product-image').attr('src', res.data.data.link);
                }
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        })
    });



    $(".btn-save-product-image").on("click", function () {
        if($("#input-product").val()==="") {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        dataProductImage.productId = $("#input-product").val();
        dataProductImage.link = $('.product-image').attr('src');
        NProgress.start();
        console.log(dataProductImage.id);
        var linkPost = "/api/product-image/create";
        if(dataProductImage.id) {
            linkPost = "/api/product-image/update/" + dataProductImage.id;
        }

        axios.post(linkPost, dataProductImage).then(function(res){
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
                'Some error when saving product-image',
                'error'
            );
        });
    });

    $(".delete-product-image").on("click", function () {
        var pdImgInfo = $(this).data("productImage");
        console.log(pdImgInfo);
        NProgress.start();
        var result = confirm("Do you want to delete?");
        // var linkPost = "/api/product-image/delete/" + pdImgInfo;
        axios.get("/api/product-image/delete/" + pdImgInfo).then(function(res){
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
                'Some error when delete product-image',
                'error'
            );
        });
    });

});