<%--
  Created by IntelliJ IDEA.
  User: tarek
  Date: 10/10/2022
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="fr.eservices.drive.web.dto.PerishableEntry" %>
<%@ include file="_header.jsp"%>

<html>
<head>
    <title>Ajouter un article perisable</title>

    <link href="<%= ctxPath %>/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<%= ctxPath %>/css/ie10-viewport-bug-workaround.css" rel="stylesheet"/>
    <link href="<%= ctxPath %>/css/main.css" rel="stylesheet"/>
</head>

<body>
<%--@elvariable id="PerishableEntry" type="fr.eservices.drive.web.dto.PerishableEntry"--%>
<h1>Ajouter un article perisable</h1>
    <form:form action="/projet/perishable/add.json" modelAttribute="PerishableEntry" method="POST" enctype='application/json' id="form">


   <div class="form-group">
            <label for="ean13">Ean13</label>
            <form:input path="ean13" id="ean13" class="form-control" />
        </div>
        <div class="form-group">
            <label for="bestBefore">Bestbefore</label>
            <form:input type="date" id="bestBefore" path="bestBefore" class="form-control" />
        </div>

    <div class="form-group">
            <label for="lot">Lot</label>
            <form:input path="lot" id="lot" class="form-control" />
        </div>
        <div class="form-group">
            <label for="quantity">Quantite</label>
            <form:input type="number" id="quantity" path="quantity" class="form-control" />
        </div>

        //button
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Ajouter</button>
        </div>


</form:form>
<script>
    var form = document.getElementById('form');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var data = new FormData(form);
        console.log(data);
        $.ajax(
            {
                method: 'POST',
                url: '/projet/perishable/add.json',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify({
                    ean13: data.get('ean13'),
                    bestBefore: data.get('bestBefore'),
                    lot: data.get('lot'),
                    quantity: data.get('quantity')
                }),
            }
        )
    });
</script>

</body>

</html>
<%@ include file="_footer.jsp"%>