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
<c:if test="${perishedList == null || perishedList.size() == 0}">
    <p>Aucun produit périmié</p>
</c:if>
<c:if test="${perishedList != null}">
    <c:forEach items="${perishedList}" var="perished">
        <p><c:out value="${perished.name}"/>
    </c:forEach>
</c:if>
</body>
</html>
