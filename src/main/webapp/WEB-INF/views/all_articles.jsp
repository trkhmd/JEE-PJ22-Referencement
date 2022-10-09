<%@ include file="_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>All articles</h1>

    <div class="row" style="margin:0px; margin-bottom:10px;">
        <form class="form-inline">
            <div class="form-group">
                <label for="category">Category</label>
                <select class="form-control" id="category" name="cat">
                <option value="">TOUS</option>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter the name...">
            </div>
            <div class="form-group">
                <label for="ref">Referance</label>
                <input type="text" class="form-control" id="ref" name="ref" placeholder="Enter the reference...">
            </div>
            <button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                Filter
            </button>
        </form>
    </div>
    <%-- Display all articles --%>
    <table class="table table-bordered table-striped table-hover">
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>EAN</th>
        <th>Price</th>
        <th>VAT</th>
        <th>Category</th>
    </tr>
    <c:forEach items="${articles}" var="art">
    <tr>
        <td><a href="article/${art.getId()}.html">${art.getId()}</a></td>
        <td>${art.getName()}</td>
        <td>${art.getEan13()}</td>
        <td>${art.getPrice()}</td>
        <td>${art.getVat()}</td>
        <td>
            <c:choose>
                <c:when test="${empty art.categories}">
                    <span class="text-muted">No category</span>
                </c:when>
                <c:otherwise>
                    <ul>
                        <c:forEach items="${art.categories}" var="cat">
                            <li>${cat.getName()}</li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    </c:forEach>  
    </table>
</div>

<%@ include file="_footer.jsp" %>