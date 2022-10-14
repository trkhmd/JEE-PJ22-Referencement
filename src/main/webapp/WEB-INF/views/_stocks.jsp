<%@ include file="_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="container">
    <h1>Stock</h1>

    <%-- Display all products --%>
    <table class="table table-bordered table-striped table-hover">
    <tr>
        <th>EAN</th>
        <th>Stock ID</th>
        <th>Quantity</th>
        <th>lot</th>
        <th>DLC</th>
    </tr>
    <c:choose>
        <c:when test="${empty products and empty perishables}">
            <tr>
                <td colspan="6">No articles found</td>
            </tr>
        </c:when>
    </c:choose>
    <c:forEach items="${products}" var="stock">
        <tr>
            <td><a href="articles/edit/${stock.article.ean13}.html">${stock.article.ean13}</a></td>
            <td>${stock.id}</td>
            <td>${stock.quantity}</td>
            <td></td>
            <td></td>
        </tr>
    </c:forEach>
    <c:forEach items="${perishables}" var="stock">
        <tr>
            <td>${stock.article.ean13}</td>
            <td>${stock.id}</td>
            <td>${stock.quantity}</td>
            <td>${stock.lot}</td>
            <td>${stock.bestBefore}</td>
        </tr>
    </c:forEach>
    </table>

</div>

<%@ include file="_footer.jsp" %>