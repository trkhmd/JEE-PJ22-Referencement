<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h1>Stock</h1>

<div style="margin:0px; margin-bottom:10px;display:flex;justify-content:space-between;">
  <a href="perishable/add.html" class="btn btn-primary">Create perishable</a>
</div>

<%-- Display all products --%>
<table class="table table-bordered table-striped table-hover">
  <tr>
    <th>EAN</th>
    <th>Article name</th>
    <th>Lot</th>
    <th>DLC</th>
    <th>Quantity</th>
    <th>Modifier le stock</th>
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
      <td>${stock.article.name}</td>
      <td colspan="2"></td>
      <td>${stock.quantity}</td>
      <td>
        <button type="button" class="btn btn-primary btn-sm addStock" data-ref="${stock.id}" data-quantity="${stock.quantity}">
          <span class="glyphicon glyphicon-plus-sign"></span>
        </button>
        <button type="button" class="btn btn-danger btn-sm removeStock" data-ref="${stock.id}" data-quantity="${stock.quantity}">
          <span class="glyphicon glyphicon-minus-sign"></span>
        </button>
      </td>
    </tr>
  </c:forEach>
  <c:forEach items="${perishables}" var="stock">
    <tr>
      <td><a href="articles/edit/${stock.article.ean13}.html">${stock.article.ean13}</a></td>
      <td>${stock.article.name}</td>
      <td>${stock.lot}</td>
      <td>${stock.bestBefore}</td>
      <td>${stock.quantity}</td>
      <td>
        <button type="button" class="btn btn-primary btn-sm addStock" data-ref="${stock.id}" data-quantity="${stock.quantity}">
          <span class="glyphicon glyphicon-plus-sign"></span>
        </button>
        <button type="button" class="btn btn-danger btn-sm removeStock" data-ref="${stock.id}" data-quantity="${stock.quantity}">
          <span class="glyphicon glyphicon-minus-sign"></span>
        </button>
      </td>
    </tr>
  </c:forEach>
</table>

