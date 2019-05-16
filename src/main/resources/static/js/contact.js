$(document).ready(function() {

    $("#myForm").submit(function () {
        var formData = new FormData();
        NProgress.start();

        axios.post(formData).then(function(res){
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
                'Some error when send contact',
                'error'
            );
        });
    });
});