<%@ include file="_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>All articles</h1>

<%-- Display all articles --%>
<table class="table table-bordered table-striped table-hover">
  <tr>
	<th>#</th>
	<th>Name</th>
	<th>EAN</th>
	<th>Price</th>
	<th>VAT</th>
  </tr>
  <c:forEach items="${articles}" var="art">
  <tr>
	<td><a href="article/${art.getId()}.html">${art.getId()}</a></td>
	<td>${art.getName()}</td>
	<td>${art.getEan13()}</td>
	<td>${art.getPrice()}</td>
	<td>${art.getVat()}</td>
  </tr>
  </c:forEach>
  
</table>

<%@ include file="_footer.jsp" %>