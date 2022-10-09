<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eservices.drive.model.Article" %>

<pre>
    Article
    <c:if test="${perishable == null}">
        <p>Produit consommable inconnu</p>
    </c:if>
    <c:if test="${perishable != null}">
        <p><c:out value="${perishable.article.name}"/>
        <p><c:out value="${perishable.bestBefore}"/>
        <p><c:out value="${perishable.lot}"/>
    </c:if>


</pre>
<!-- TODO: IF NEEDED
<a class="btn btn-primary" href="">Commander</a>
-->