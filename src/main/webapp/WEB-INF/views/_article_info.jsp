<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eservices.drive.model.Article" %>

<pre>
    Article
    <c:if test="${article == null}">
        <p>Article inconnu</p>
    </c:if>
    <c:if test="${article != null}">
        <p><c:out value="${article.name}"/>
        <p><c:out value="${article.ean13}"/>
        <p><c:out value="${article.price}"/>
    </c:if>


</pre>
<!-- TODO: IF NEEDED
<a class="btn btn-primary" href="">Commander</a>
-->