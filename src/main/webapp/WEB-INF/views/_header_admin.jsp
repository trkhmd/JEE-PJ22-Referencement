<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<% 
String ctxPath = request.getContextPath(); 
java.util.List<String> jsList = new java.util.ArrayList<>(); 
%>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>My Web Drive</title>

  <link href="<%= ctxPath %>/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="<%= ctxPath %>/css/ie10-viewport-bug-workaround.css" rel="stylesheet"/>
  <link href="<%= ctxPath %>/css/main.css" rel="stylesheet"/>
</head>
<body>

<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%= ctxPath %>/"><span class="glyphicon glyphicon-barcode"></span>
       My Web Drive</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="<%= ctxPath %>/articles.html">Articles</a></li>
        <li><a href="<%= ctxPath %>/stocks.jsp">Stock</a></li>
        <li><a href="<%= ctxPath %>/perished.jsp">Perished</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>

<div class="container" id="container">
