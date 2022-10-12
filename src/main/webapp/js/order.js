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
    $("#searchOrder").click( function(e) {
        const ref = $("#orderNumber")[0].value;
        console.log(ref);
        $.ajax({
            url: "order/"+ ref + ".html",
        }).done(function (data) {
            JSON.stringify( $('#container').html(data));
        });
    });
}

function loadOnClick() {
    $("#searchOrder").click( function(e) {
        const ref = $("#orderNumber").data("ref");
        $.ajax({
            method: 'GET',
            url: "order/"+ ref + ".html",
            dataType: "html"
        }).done(function (data) {
            if(data['status'] === 'OK') {
                loadTable();
            }
        });
    });
}