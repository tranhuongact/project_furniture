$(document).ready(function() {

    var dataCategory = {};

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('.category-main-image').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }


    $("#change-category-mainImage").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-category-mainImage")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function(res){
            NProgress.done();
            if(res.data.success) {
                $('.category-main-image').attr('src', res.data.link);
            }
        }, function(err){
            NProgress.done();
        });
    });


    $("#new-category").on("click", function () {
        dataCategory = {};
        $("#change-category-mainImage").val("");
        $('#input-category-name').val("");
        $('#input-category-desc').val("");
        $('.category-main-image').attr('src', 'https://www.vietnamprintpack.com/images/default.jpg');

    });


    $(".edit-category").on("click", function () {
        var cateInfo = $(this).data("category");
        console.log(cateInfo);
        NProgress.start();
        axios.get("/api/category/detail/" + cateInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataCategory.id = res.data.data.id;
                $("#input-category-name").val(res.data.data.name);
                $("#input-category-desc").val(res.data.data.shortDesc);
                if(res.data.data.mainImage != null) {
                    $('.category-main-image').attr('src', res.data.data.mainImage);
                }
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-category").on("click", function () {
        if($("#input-category-name").val() === ""  ) {
            swal(
                'Error',
                'You need to fill name ',
                'error'
            );
            return;
        }

        dataCategory.name = $('#input-category-name').val();
        dataCategory.shortDesc = $('#input-category-desc').val();
        dataCategory.mainImage = $('.category-main-image').attr('src');
        NProgress.start();
        console.log(dataCategory.id);
        var linkPost = "/api/category/create";
        if(dataCategory.id) {
            linkPost = "/api/category/update/" + dataCategory.id;
        }

        axios.post(linkPost, dataCategory).then(function(res){
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
                'Some error when saving category',
                'error'
            );
        })
    });



});