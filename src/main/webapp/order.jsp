<%--
  Created by IntelliJ IDEA.
  User: Kozlov
  Date: 11/10/2022
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/views/_header_employee.jsp" %>

<input id="orderNumber" type="text" placeholder="Numéro de commande" />
<button id="searchOrder">Chercher</button>
<div id="orderTable"></div>
<%
    jsList.add("order.js");
%>
<%@include file="/WEB-INF/views/_footer.jsp" %>