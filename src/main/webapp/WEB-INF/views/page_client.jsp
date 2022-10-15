<%--
  Created by IntelliJ IDEA.
  User: lisam
  Date: 15/10/2022
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="_header.jsp" %>
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
    <%--
       <div style="margin:0px; margin-bottom:10px;display:flex;justify-content:space-between;">
           <form class="form-inline">
               <input type="hidden" value="${articels.size}" name="size" />
               <div class="form-group">
                   <label for="category">Category</label>
                   <select class="form-control" id="category" name="cat">
                       <option value="">TOUS</option>
                       <c:forEach items="${categories}" var="category">
                           <option value="${category.id}" ${category.id == param.cat ? "selected=selected" : ""} >${category.name}</option>
                       </c:forEach>
                   </select>
               </div>
               <div class="form-group">
                   <label for="name">Name</label>
                   <input type="text" class="form-control" id="name" name="name" value="${param.name}" placeholder="Enter the name...">
               </div>
               <div class="form-group">
                   <label for="ref">Reference</label>
                   <input type="text" class="form-control" id="ref" name="ref" value="${param.ref}" placeholder="Enter the reference...">
               </div>

       <button type="submit" class="btn btn-primary">
           <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
           Filter
       </button>
       </form>

       </div>--%>

      <%-- Display all available articles --%>
<table class="table table-bordered table-striped table-hover">
    <tr>
        <th>EAN</th>
        <th>Product Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Category</th>
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
            <td>${stock.article.ean13}</td>
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
        </tr>
    </c:forEach>
    <c:forEach items="${perishables}" var="stock">
        <fmt:setLocale value="en_US" />
        <jsp:useBean id="now" class="java.util.Date" />
        <c:set var="maintenant" value="${now}" />
        <c:set target="${maintenant}" property="time" value="${ maintenant.time + 86400000 * 5}" />

        <c:if test="${stock.bestBefore gt maintenant}">

        <c:if test = "${stock.quantity > 0}">
        <tr>
            <td>${stock.article.ean13}</td>
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
            <td>${stock.bestBefore}</td>
        </tr>
        </c:if>
        </c:if>
    </c:forEach>
</table>

</div>

    <nav aria-label="Page navigation" style="display: flex;justify-content: space-between;">
        <ul class="pagination">
            <li>
                <a href="?page=0&size=${articles.size}&cat=${param.cat}&name=${param.name}&ref=${param.ref}" aria-label="Begin">
                    <span aria-hidden="true">|&laquo;</span>
                </a>
            </li>
            <c:forEach var="i"
                       begin="${articles.number-10 > 0 ? articles.number-10 : 1}"
                       end="${articles.number+10 < articles.totalPages ? articles.number+10 : articles.totalPages}">
                <li class="${ i == articles.number+1 ? 'active' : ''}"><a href="?page=${i-1}&size=${articles.size}&cat=${param.cat}&name=${param.name}&ref=${param.ref}">${i}</a></li>
            </c:forEach>
            <li>
                <a href="?page=${articles.totalPages-1}&size=${articles.size}&cat=${param.cat}&name=${param.name}&ref=${param.ref}" aria-label="End">
                    <span aria-hidden="true">&raquo;|</span>
                </a>
            </li>
        </ul>
        <ul class="pagination">
            <li class="${ 10 == articles.size ? 'active' : ''}">
                <a href="?page=0&size=10&cat=${param.cat}&name=${param.name}&ref=${param.ref}">10</a></li>
            <li class="${ 50 == articles.size ? 'active' : ''}">
                <a href="?page=0&size=50&cat=${param.cat}&name=${param.name}&ref=${param.ref}">50</a></li>
            <li class="${ 200 == articles.size ? 'active' : ''}">
                <a href="?page=0&size=200&cat=${param.cat}&name=${param.name}&ref=${param.ref}">200</a></li>
        </ul>
    </nav>

</div>

<%@ include file="_footer.jsp" %>

