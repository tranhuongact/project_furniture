$(document).ready(function() {

    var dataContact = {};

    $(".contact-detail").on("click", function () {
        var contactInfo = $(this).data("contact");
        console.log(contactInfo);
        NProgress.start();
        axios.get("/api/contact/detail/" + contactInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataContact.id = res.data.data.id;
                $('#input-full-name').val(res.data.data.fullName);
                $('#input-phone-number').val(res.data.data.phoneNumber);
                $('#input-email').val(res.data.data.email);
                $('#input-message').val(res.data.data.message);
            }else {
                console.log("error");
            }
        }, function(err){
            NProgress.done();
        });
    });

});