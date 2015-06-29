<html lang="pt-br">
<body>
<form name="form" method="post">

<% 
String pagina = request.getContextPath() + "?SENTINELA=SENTINELA_REQUEST_LOGOFF";

if(request.getParameter("msgOperacao") != null) {
%>
	<script language="javascript">
		window.alert('<%=request.getParameter("msgOperacao")%>');
		document.form.action = "<%=pagina%>";
		document.form.submit();
	</script>
<%
}
else 
{
	response.sendRedirect(pagina);
}
%>
</form>
</body>
</html>