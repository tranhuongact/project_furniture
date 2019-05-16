$(document).ready(function(){
    $('.img-product')
        .wrap('<span style="display:inline-block"></span>')
        .css('display', 'block')
        .parent()
        .zoom().onZoomIn();
});
