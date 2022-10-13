<%@ include file="_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div style="margin:20px">
    <h2>Create article</h2>

    <c:if test="${error_alert != null}">
        <div class="alert alert-danger" role="alert">
            <strong>${error_alert}</strong>
        </div>
    </c:if>

    <form:form method="POST" modelAttribute="article">
    <div class="row">
        <div class="form-group col-md-6">
            <label for="fEan">EAN</label>
            <form:input path="ean13" type="text" class="form-control" id="fEan" placeholder="EAN"/>
            <form:errors path="ean13" cssClass="text-danger"/>
        </div>
        <div class="form-group col-md-6">
            <label for="fName">Name</label>
            <form:input path="name" type="text" class="form-control" id="fName" placeholder="Name"/>
            <form:errors path="name" cssClass="text-danger"/>
        </div>
    </div>
    <div class="row">
        <div class="form-group col-md-6">
            <label for="fPrice">Price</label>
            <form:input path="price" type="number" step=".01" class="form-control" id="fPrice" placeholder="Price"/>
            <form:errors path="price" cssClass="text-danger"/>
        </div>
        <div class="form-group col-md-6">
            <label for="fVat">Vat</label>
            <form:input path="vat" type="number" class="form-control" id="fVat" placeholder="Vat"/> 
            <form:errors path="vat" cssClass="text-danger"/>
        </div>
    </div>
    <div class="form-group">
        <label for="fImg">Image</label>
        <form:input path="img" type="text" class="form-control" id="fImg" placeholder="Image"/>
    </div>
    <div class="form-group">
        <label for="fCat">Categories</label>
        <ul style="list-style:none;">
            <c:forEach items="${categories}" var="category">
                <li><form:checkbox path="categories" value="${category.id}" label="${category.name}"/></li>
            </c:forEach>
        </ul>
    </div>

    <form:button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        Ok
    </form:button>
    
    </form:form>
</div>
<%@ include file="_footer.jsp" %>