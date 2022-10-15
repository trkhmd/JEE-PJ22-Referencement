
$(function() {
    var link  = document.createElement('link');
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = 'css/cart.css';
    link.media = 'all';
    document.getElementsByTagName('head')[0].appendChild(link);

    loadTable();

});
function loadTable() {
    $.ajax({
        url: "stocks.html"
    }).done(function(data){
        JSON.stringify( $('#container').html(data));
        loadOnClick();
    });
}

function loadOnClick() {
    $(".removeStock").click( function(e) {
        var ref = $(this).data("ref");
        const qty = $(this).data("quantity");
        const quantity = parseInt(qty) - 1;
        if(quantity > 0) {
            modifyQuantity(ref, quantity);
        } else {
            deleteStock(ref);
        }
    });

    $(".addStock").click( function(e) {
        var ref = $(this).data("ref");
        const qty = $(this).data("quantity");
        const quantity = parseInt(qty) + 1;
        modifyQuantity(ref, quantity);
    });
}

function deleteStock(id) {
    $.ajax({
        method: 'DELETE',
        url: "stocks/"+ id + ".json",
        dataType: "json"
    }).done(function (data) {
        if(data['status'] === 'OK') {
            loadTable();
        }
    });
}

function modifyQuantity(ref, quantity) {
    $.ajax({
        method: 'PUT',
        url: "stocks/"+ ref + ".json",
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify( {quantity: quantity} )
    }).done(function (data) {
        if(data['status'] === 'OK') {
            loadTable();
        }
    });
}