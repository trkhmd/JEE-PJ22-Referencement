<%--
  Created by IntelliJ IDEA.
  User: frkoz
  Date: 09/10/2022
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Produits périmés</title>
</head>
<body>
<c:if test="${perishables == null || perishables.size() == 0}">
    <p>Aucun produit périmé</p>
</c:if>
<c:if test="${perishables != null && perishables.size() > 0}">
    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th scope="col">EAN13</th>
            <th scope="col">Nom du produit</th>
            <th scope="col">Date de péremption (DLC)</th>
            <th scope="col">N° lot</th>
            <th scope="col">Supprimer le lot</th>
        </tr>
    <c:forEach items="${perishables}" var="perished">
        <tr>
            <td><c:out value="${perished.article.ean13}"/></td>
            <td><c:out value="${perished.article.name}"/></td>
            <td><c:out value="${perished.bestBefore}"/></td>
            <td><c:out value="${perished.lot}"/></td>
            <td>
                <button type="button" class="btn btn-danger btn-sm removeStock" data-ref="${perished.id}">
                    <span class="glyphicon glyphicon-minus-sign"></span>
                </button>
            </td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</body>
</html>
