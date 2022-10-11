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
<c:if test="${perishables == null || perishables.size() == 0}">
    <p>Aucun produit périmié</p>
</c:if>
<c:if test="${perishables != null && perishables.size() > 0}">
    <c:forEach items="${perishables}" var="perishable">
<p><c:out value="${perishable.article.name}"/>
    </c:forEach>
</c:if>
</body>
</html>
