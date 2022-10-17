<%--
  Created by IntelliJ IDEA.
  User: lisam
  Date: 15/10/2022
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="WEB-INF/views/_header_client.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="alert alert-warning" id="alert" role="alert">
</div>

<div id="content">
</div>
<%
    jsList.add("client.js");
%>
<%@ include file="WEB-INF/views/_footer.jsp" %>

