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
<c:if test="${order == null}">
    <p>Aucune commande avec ce numéro</p>
</c:if>
<c:if test="${order != null}">

    <h2><c:out value="${order.deliveredOn}"/></h2>
<%--    faire le fmt de date--%>

    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th scope="col">EAN13</th>
            <th scope="col">Nom du produit</th>
            <th scope="col">Quantité</th>
            <th scope="col">Status</th>
            <th scope="col">Produit frais</th>
            <th scope="col">Action</th>
        </tr>
        <c:forEach items="${order.articlesOrder}" var="articleOrder">
            <tr>
                <td><c:out value="${articleOrder.article.ean13}"/></td>
                <td><c:out value="${articleOrder.article.name}"/></td>
                <td><c:out value="${articleOrder.quantity}"/></td>
                <td><c:out value="${articleOrder.articleStatus}"/></td>
                <c:choose>
                    <c:when test="${articleOrder.article.perishable}">
                        <td>Oui</td>
                        <td></td>
                    </c:when>
                    <c:otherwise>
                        <td>Non</td>
                        <td><span class="glyphicon glyphicon-plus-sign addStock" data-ref="${articleOrder.article.ean13}" data-qty="${articleOrder.quantity}"></span></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
