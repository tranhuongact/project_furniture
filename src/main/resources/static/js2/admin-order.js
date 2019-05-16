$(document).ready(function() {
});
    //
//     var dataOrder = {};
//
//     // $("#new-order").on("click", function () {
//     //     dataOrder = {};
//     //     $('#input-user-name').val("");
//     //     $('#input-address').val("");
//     //     $('#input-email').val("");
//     //     $('#input-phone-number').val("");
//     //     $('#input-order-price').val("");
//     // });
//
//
//     $(".edit-order").on("click", function () {
//         var orderInfo = $(this).data("order");
//         console.log(orderInfo);
//         NProgress.start();
//         axios.get("/api/order/history" + orderInfo).then(function(res){
//             NProgress.done();
//             if(res.data.success) {
//                 dataOrder.id = res.data.data.id;
//                 $('#input-user-name').val(res.data.data.username);
//                 $('#input-address').val(res.data.data.address);
//                 $('#input-email').val(res.data.data.email);
//                 $('#input-phone-number').val(res.data.data.phoneNumber);
//                 $('#input-order-price').val(res.data.data.price);
//             }else {
//                 console.log("ahihi");
//             }
//         }, function(err){
//             NProgress.done();
//         })
//     });
//
//     $(".btn-save-history").on("click", function () {
//         if($('#input-address').val() === "" || $('#input-email').val() === "" || $('#input-phone-number').val() === ""|| $('#input-order-price').val() === "") {
//             swal(
//                 'Error',
//                 'You need to fill all values',
//                 'error'
//             );
//             return;
//         }
//
//         dataOrder.username = $('#input-user-name').val();
//         dataOrder.address = $('#input-address').val();
//         dataOrder.email = $('#input-email').val();
//         dataOrder.phoneNumber = $('#input-phone-number').val();
//         dataOrder.price = $('#input-order-price').val();
//         NProgress.start();
//         console.log(dataOrder.id);
//         var linkPost = "/api/category/create";
//         if(dataCategory.id) {
//             linkPost = "/api/category/update/" + dataCategory.id;
//         }
//
//         axios.post(linkPost, dataCategory).then(function(res){
//             NProgress.done();
//             if(res.data.success) {
//                 swal(
//                     'Good job!',
//                     res.data.message,
//                     'success'
//                 ).then(function() {
//                     location.reload();
//                 });
//             } else {
//                 swal(
//                     'Error',
//                     res.data.message,
//                     'error'
//                 );
//             }
//         }, function(err){
//             NProgress.done();
//             swal(
//                 'Error',
//                 'Some error when saving product',
//                 'error'
//             );
//         })
//     });
//
//
//
// });