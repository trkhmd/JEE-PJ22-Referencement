<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String ctxPath = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="<%= ctxPath %>/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<%= ctxPath %>/css/ie10-viewport-bug-workaround.css" rel="stylesheet"/>
    <link href="<%= ctxPath %>/css/main.css" rel="stylesheet"/>
    <title>Title</title>
</head>

<body>
<div style=" width: 100%; height: 100%; display: flex ; align-items: center;  justify-content: center;">
    <a href="<%= ctxPath %>/articles.html">
        <button class="btn btn-primary" style=" margin: 5px;" >Admin</button>
    </a>
    <a href="<%= ctxPath %>/client.html">
        <button style="background-color: #d58512; color:white; margin: 5px;" class="btn" href=>Client</button>
    </a>
    <a href="<%= ctxPath %>/order.jsp">
        <button style="background-color: green; color:white; margin: 5px;" class="btn" href="<%= ctxPath %>/order.jsp">Employé</button>
    </a>
</div>

</body>
</html>