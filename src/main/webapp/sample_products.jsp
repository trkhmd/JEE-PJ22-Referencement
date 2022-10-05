<%@include file="/WEB-INF/views/_header.jsp" %>
<%-- import required classes --%>

<h1>Les promos de la semaine !</h1>

<script language="javascript">

</script>

<ul class="articles">
<%-- Iterate through articles ... --%>
<li>
	<a href="#">
		<span class="price">
			4,50 &euro;</span>
		<img src="https://static1.chronodrive.com/img/PM/P/0/20/0P_195420.gif"/><br/>
		420g Fromage à raclette <br/>
	</a>
	<span class="glyphicon glyphicon-plus-sign addToCart" data-ref="195420"></span>
</li>

<li>
	<a href="#">
		<span class="price">
			1,74 &euro;</span>
		<img src="https://static1.chronodrive.com/img/PM/P/0/09/0P_165609.gif"/><br/>
		6 tranches Jambon Serrano <br/>
	</a>
	<span class="glyphicon glyphicon-plus-sign addToCart" data-ref="165609"></span>
</li>

<li>
	<a href="#">
		<span class="price">
			1,69 &euro;</span>
		<img src="https://static1.chronodrive.com/img/PM/P/0/74/0P_120574.gif"/><br/>
		2,5 kg Pomme de terre Cat 1 <br/>
	</a>
	<span class="glyphicon glyphicon-plus-sign addToCart" data-ref="120574"></span>
</li>

</ul>

<% 
jsList.add("cart.js");
%>
<%@include file="/WEB-INF/views/_footer.jsp" %>