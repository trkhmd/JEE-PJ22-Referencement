<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<pre>
Panier
    <c:if test="${cart == null || cart.size() == 0}">
      <p>Aucun article</p>
    </c:if>
    <c:if test="${cart.size() > 0}">
      <c:forEach items="${cart}" var="articleCart">
        <p><c:out value="${articleCart.article.name}"/> x<c:out value="${articleCart.quantity}"/></p>
      </c:forEach>
    </c:if>
</pre>
<button class="btn btn-primary validate">Commander</button>
<button class="btn btn-primary clear">Clear</button>