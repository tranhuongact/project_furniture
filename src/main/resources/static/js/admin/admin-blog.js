$(document).ready(function() {

    var dataBlog = {};

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('.blog-main-image').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }


    $("#change-blog-image").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-blog-image")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function(res){
            NProgress.done();
            if(res.data.success) {
                $('.blog-main-image').attr('src', res.data.link);
            }
        }, function(err){
            NProgress.done();
        });
    });



    $("#new-blog").on("click", function () {
        dataBlog = {};
        $("#change-blog-image").val("");
        $('#input-title').val("");
        $('#input-username').val("");
        $("#input-short-desc").val("");
        $("#input-content").val("");
        $('.blog-main-image').attr('src', 'https://www.vietnamprintpack.com/images/default.jpg');
    });


    $(".edit-blog").on("click", function () {
        var blogInfo = $(this).data("blog");
        console.log(blogInfo);
        NProgress.start();
        axios.get("/api/blog/detail/" + blogInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataBlog.id = res.data.data.id;
                $("#input-title").val(res.data.data.title);
                $("#input-short-desc").val(res.data.data.shortDesc);
                $("#input-username").val(res.data.data.userId);
                // $("#input-content").val(res.data.data.content);
                CKEDITOR.instances['input-content'].setData(res.data.data.content);
                if(res.data.data.mainImage != null) {
                    $('.blog-main-image').attr('src', res.data.data.mainImage);
                }
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        });
    });


    $(".btn-save-blog").on("click", function () {
        if($("#input-title").val() === "" || $("#input-short-desc").val() === "") {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        dataBlog.title = $('#input-title').val();
        dataBlog.shortDesc = $('#input-short-desc').val();
        dataBlog.content = CKEDITOR.instances['input-content'].getData();
        dataBlog.userId = $("#input-username").val();
        dataBlog.mainImage = $('.blog-main-image').attr('src');

        var slug = function(str) {
            var $slug = '';
            var trimmed = $.trim(str);
            $slug = trimmed.replace(/[^a-z0-9-]/gi, '-').
            replace(/-+/g, '-').
            replace(/^-|-$/g, '');
            return $slug.toLowerCase();
        }
        dataBlog.slug = slug($('#input-title').val());

        NProgress.start();
        console.log(dataBlog.id);
        var linkPost = "/api/blog/create";
        if(dataBlog.id) {
            linkPost = "/api/blog/update/" + dataBlog.id;
        }

        axios.post(linkPost, dataBlog).then(function(res){
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
                'Some error when saving blog',
                'error'
            );
        });
    });



});