<%--
  Created by IntelliJ IDEA.
  User: Kozlov
  Date: 17/10/2022
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <h1>Available articles</h1>
  <table class="table table-bordered table-striped table-hover">
    <tr>
      <th>Product Name</th>
      <th>Quantity</th>
      <th>Price</th>
      <th>Category</th>
      <th>DLC</th>
      <th>Action</th>
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
        <td>${stock.article.getName()}</td>
        <td>${stock.quantity}</td>
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
        <td></td>
        <td><span class="glyphicon glyphicon-plus-sign addArticle" data-ref="${stock.id}"></span></td>
      </tr>
    </c:forEach>

    <c:forEach items="${perishables}" var="stock">
      <tr>
        <td>${stock.article.getName()}</td>
        <td>${stock.quantity}</td>
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

        <td><c:out value="${stock.bestBefore}"/></td>
        <td><span class="glyphicon glyphicon-plus-sign addArticle" data-ref="${stock.id}"></span></td>
      </tr>
    </c:forEach>
  </table>