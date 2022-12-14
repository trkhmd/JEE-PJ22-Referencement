
$(function() {
    var link  = document.createElement('link');
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = 'css/cart.css';
    link.media = 'all';
    document.getElementsByTagName('head')[0].appendChild(link);
    loadCart();
    loadTable();
});

function loadTable() {
    $.ajax({
        url: "client.html",
    }).done(function (data) {
        JSON.stringify( $('#content').html(data) )
        loadOnClick();
    });
}

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
            if(data['status'] === 'OK') {
                loadCart();
                loadTable();
            }
        });
    });


}

function loadCart() {
    $.ajax({
        url: "cart.html"
    }).done(function(data){
        JSON.stringify( $('#cartInHeader').html(data) )
        validate();
    });
}

function validate() {
    $(".validate").click( function(e) {
        $.ajax({
            method: 'GET',
            url: "cart/validate.json",
            contentType: 'application/json',
        }).done(function (data) {
            JSON.stringify( $('#alert').html(data['message']) )
            loadTable();
            loadCart();
        });
    });
    $(".clear").click( function(e) {
        $.ajax({
            method: 'POST',
            url: "cart/clear.json",
            contentType: 'application/json',
        }).done(function (data) {
            loadCart();
            loadTable();
        });
    });
}
