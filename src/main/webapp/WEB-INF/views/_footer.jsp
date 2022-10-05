</div>
<script src="<%= ctxPath %>/js/jquery.min.js" type="text/javascript"></script>
<script src="<%= ctxPath %>/js/bootstrap.min.js" type="text/javascript"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="<%= ctxPath %>/js/ie10-viewport-bug-workaround.js"></script>
<%  for ( String js : jsList ) {
%><script src="<%= ctxPath %>/js/<%= js %>"></script>
<%  }  %>
</body>
</html>