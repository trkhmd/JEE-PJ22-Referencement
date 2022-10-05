<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eservices.drive.model.Cart" %>
<%@ page import="fr.eservices.drive.model.Article" %>
<pre>
Panier
    <c:if test="${cart.articles.size() == 0}">
        <p>Aucun article</p>
    </c:if>
    <c:forEach items="${cart.articles}" var="article">
        <p><c:out value="${article.name}"/> <fmt:formatNumber value = "${article.price / 100}" type = "currency" currencySymbol="$"/></p>
    </c:forEach>

</pre>
<a class="btn btn-primary" href="cart/1/validate.html">Commander</a>