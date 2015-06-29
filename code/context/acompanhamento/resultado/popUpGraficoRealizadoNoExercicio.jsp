<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<body class="corpo_popup">
<%
try{
%>
<H1>Gráfico de Valores Totais Realizado no Exercício</H1>

<form name="form" method="post" action="">
	<table class="form">
		<tr> 
				<img src='<%=_pathEcar%>/GraficoRealizadoNoExercicio?codAri=<%=Pagina.getParamStr(request, "codAri")%>&codExe=<%=Pagina.getParamStr(request, "codExe")%>&codIettir=<%=Pagina.getParamStr(request, "codIettir")%>'>
		</tr>
	</table>
</form>
<%

if(request.getParameter("voltar") != null ){
%>
	<a href="javascript:window.history.go(-1)">Voltar</a>
<%
}
%>
<%

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

