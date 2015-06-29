<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
 
<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<body class="corpo_popup">
<%
try{
%>
<H1>Gráfico - Valores realizados por &Oacute;rg&atilde;o - <%=request.getParameter("tipoGrafico")%></H1>

<form name="form" method="post" action="">
	<table class="form">
		<tr> 
			<td>
				<img src="<%=_pathEcar%>/images/evPosicoesOrgaoTemp.png">
			</td>
		</tr>
	</table>
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

