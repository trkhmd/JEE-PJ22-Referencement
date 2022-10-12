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
    <form:form action="addPerishable" modelAttribute="PerishableEntry" method="POST" >




   <div class="form-group">
            <label for="ean13">Nom</label>
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

            <input type="submit" value="Submit" name="submit" class="btn btn-primary" />

</form:form>


</body>

</html>
<%@ include file="_footer.jsp"%>