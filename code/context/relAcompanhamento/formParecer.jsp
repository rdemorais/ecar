<jsp:directive.page import="comum.util.Pagina"/><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<script language="javascript">
function teste(){
	window.opener.atualizaLinkAri(<%=Pagina.getParamStr(request, "codAriFormParecer")%>);
	window.close();
}
</script>
</head>
<body>
Inclusão de Parecer do codAri <%=Pagina.getParamStr(request, "codAriFormParecer")%>:

<table>
	<tr>
		<td>Campo</td>
		<td><input type="text"></td>
	</tr>
	<tr>
		<td>Campo</td>
		<td><input type="text"></td>
	</tr>
	<tr>
		<td>Campo</td>
		<td><input type="text"></td>
	</tr>
	<tr>
		<td>Campo</td>
		<td><input type="text"></td>
	</tr>
</table>
<input type="button" name="btOk" value="OK" onclick="javascript:teste();">
</body>
</html>