<%--
  Created by IntelliJ IDEA.
  User: frkoz
  Date: 09/10/2022
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Produits périmés</title>
</head>
<body>
<c:if test="${order == null}">
    <h3>Aucune commande avec ce numéro</h3>
</c:if>
<c:if test="${order != null}">

    <h2>Commande : <c:out value="${order.id}"/></h2>
    <h3>
        <fmt:setLocale value="fr_FR"/>
        Date de la commande : <fmt:formatDate value="${order.deliveredOn}" pattern="d-M-YY"/>
    </h3>

    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th scope="col">EAN13</th>
            <th scope="col">Nom du produit</th>
            <th scope="col">Quantité</th>
            <th scope="col">Status</th>
            <th scope="col">Produit frais</th>
            <th scope="col">Retour au stock</th>
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
                        <td>
                            <c:if test="${articleOrder.canReturn(order.deliveredOn)}">
                            <button type="button" class="btn btn-primary btn-sm addStock" data-ref="${articleOrder.article.ean13}" data-qty="${articleOrder.quantity}" data-order="${order.id}">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            </button>
                            </c:if>
                        </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
