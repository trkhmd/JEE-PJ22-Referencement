
$(function() {
    var link  = document.createElement('link');
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = 'css/cart.css';
    link.media = 'all';
    document.getElementsByTagName('head')[0].appendChild(link);
    loadCart();
    loadOnClick();
});

function loadOnClick() {
    $(".addArticle").click( function(e) {
        var ref = $(this).data("ref");
        $.ajax({
            method: 'POST',
            url: "cart/add.json",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify( {stockId: ref, quantity: 1} )
        }).done(function (data) {
            console.log(data);
            loadCart();
        });
    });


}

function loadCart() {
    $.ajax({
        url: "cart.html"
    }).done(function(data){
        JSON.stringify( $('#cartInHeader').html(data) )
        $(".validate").click( function(e) {
            $.ajax({
                method: 'GET',
                url: "cart/validate.json",
                contentType: 'application/json',
            }).done(function (data) {
                console.log(data);
            });
        });
    });
}

