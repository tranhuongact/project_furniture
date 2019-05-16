$(document).ready(function() {

    var dataBrand = {};

    $("#new-brand").on("click", function () {
        dataBrand = {};
        $('#input-name').val("");
        $('#input-phone-number').val("");
        $('#input-email').val("");
        $('#input-address').val("");
    });


    $(".edit-brand").on("click", function () {
        var brandInfo = $(this).data("brand");
        console.log(brandInfo);
        NProgress.start();
        axios.get("/api/brand/detail/" + brandInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataBrand.id = res.data.data.id;
                $('#input-name').val(res.data.data.name);
                $('#input-phone-number').val(res.data.data.phoneNumber);
                $('#input-email').val(res.data.data.email);
                $('#input-address').val(res.data.data.address);
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-brand").on("click", function () {
        if($("#input-name").val() === "" || $("#input-phone-number").val() === "" || $('#input-email').val() || $('#input-address').val()) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        dataBrand.name = $('#input-name').val();
        dataBrand.phoneNumber = $('#input-phone-number').val();
        dataBrand.email = $('#input-email').val();
        dataBrand.address = $('#input-address').val();
        NProgress.start();
        console.log(dataBrand.id);
        var linkPost = "/api/brand/create";
        if(dataBrand.id) {
            linkPost = "/api/brand/update/" + dataBrand.id;
        }

        axios.post(linkPost, dataBrand).then(function(res){
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
                'Some error when saving brand',
                'error'
            );
        });
    });



});