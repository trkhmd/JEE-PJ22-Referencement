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
      <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-barcode"></span>
       My Web Drive</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          	<span class="glyphicon glyphicon-user"></span> Mon compte <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href=""><span class="glyphicon glyphicon-log-in"></span> Inscription</a></li>
            <li><a href=""><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          	<span class="glyphicon glyphicon-shopping-cart"></span> Panier <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li id="cartInHeader">
              <!-- This part is replaced with an ajax call -->
            </li>
          </ul>
        </li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>

<div class="container">
