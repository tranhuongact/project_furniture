$(document).ready(function() {

    $("#checkout-form").on('submit', function () {
        if($(".customer-name").val() ==="" || $(".email-name").val() ==="" || $('.address-name').val() ==="" || $('.phone-num').val() ==="" ){
            swal(
                'Error',
                'You need to fill all values',
                'error'
            ).then(function () {

                // location.reload();
            });

        }
    });

});