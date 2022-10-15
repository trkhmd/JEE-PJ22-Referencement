$(function() {
    var link  = document.createElement('link');
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = 'css/cart.css';
    link.media = 'all';
    document.getElementsByTagName('head')[0].appendChild(link);

    manageSearch();
});
function manageSearch() {
    $("#searchOrder").on('click', () => loadTable($("#orderNumber")[0].value));
    $("#orderNumber").on('keyup', function (e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            loadTable($("#orderNumber")[0].value);
        }
    });

}

function loadTable(id) {
    $.ajax({
        url: "order/"+ id + ".html",
    }).done(function (data) {
        JSON.stringify( $('#orderTable').html(data));
        loadOnClick();
    });
}

function loadOnClick() {
    $(".addStock").click( function(e) {
        const ref = $(this).data("ref");
        const orderId = $(this).data("order");
        $.ajax({
            method: 'PUT',
            url: "order/"+ orderId + "/" + ref + ".json",
            dataType: "json"
        }).done(function (data) {
            if(data['status'] === 'OK') {
                loadTable(orderId);
            }
        });
    });
}