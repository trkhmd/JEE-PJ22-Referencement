<%--
  Created by IntelliJ IDEA.
  User: tarek
  Date: 10/10/2022
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="fr.eservices.drive.web.dto.PerishableEntry" %>
<html>
<head>
    <title>Ajouter un article perisable</title>
</head>

<body>
<%--@elvariable id="PerishableEntry" type="fr.eservices.drive.web.dto.PerishableEntry"--%>
<form:form action="addPerishable" modelAttribute="PerishableEntry" method="POST" >
   <table>
        <tr>
            <td><form:label path="ean13">Ean13</form:label></td>
            <td><form:input path="ean13"/></td>
        </tr>
        <tr>
            <td><form:label path="bestBefore">BestBefore</form:label></td>
            <td><form:input type="date" path="bestBefore"/></td>
        </tr>
        <tr>
            <td><form:label path="lot">lot</form:label></td>
            <td><form:input path="lot"/></td>
        </tr>
        <tr>
            <td><form:label  path="quantity">Quantity</form:label></td>
            <td><form:input  path="quantity" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"></td>
        </tr>

    </table>

</form:form>
</body>
</html>
