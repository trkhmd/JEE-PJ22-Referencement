<%--
  Created by IntelliJ IDEA.
  User: lisam
  Date: 15/10/2022
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="_header_client.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container">
    <h1>Available articles</h1>
    
    <c:if test="${not empty warning}">
        <div class="alert alert-warning" role="alert">
                ${warning}
        </div>
    </c:if>

      <%-- Display all available articles --%>
<table class="table table-bordered table-striped table-hover">
    <tr>
        <th>Product Name</th>
        <th>Price</th>
        <th>Category</th>
        <th>Quantity</th>
        <th>Action</th>
    </tr>
    <c:choose>
        <c:when test="${empty stocks}">
            <tr>
                <td colspan="6">No articles found</td>
            </tr>
        </c:when>
    </c:choose>
    <c:forEach items="${stocks}" var="stock">
        <tr>
            <td>${stock.article.getName()}</td>
            <td>${stock.article.price}</td>
            <td>
                <c:choose>
                    <c:when test="${empty stock.article.categories}">
                        <span class="text-muted">No category</span>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach items="${stock.article.categories}" var="cat">
                                <li>${cat.getName()}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </td>
        <td>${stock.quantity}</td>
        <td class="text-center">
        <button class="glyphicon glyphicon-plus-sign ajouter" data-ref="${stock.id}" type="button"><i class="far fa-plus-square"></i></button>
        </td>

        </tr>
    </c:forEach>
</table>

    <button class="valider btn btn-sucess" type="button">See cart<i class="fas fa-shopping-cart"></i></button>


</div>




<%@ include file="_footer.jsp" %>
