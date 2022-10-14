
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
        url: "perishable/perished.html"
    }).done(function(data){
        JSON.stringify( $('#container').html(data));
        loadOnClick();
    });
}

function loadOnClick() {
    $(".removeStock").click( function(e) {
        var ref = $(this).data("ref");
        $.ajax({
            method: 'DELETE',
            url: "stock/"+ ref + ".json",
            dataType: "json"
        }).done(function (data) {
            if(data['status'] === 'OK') {
                loadTable();
            }
        });
    });
}