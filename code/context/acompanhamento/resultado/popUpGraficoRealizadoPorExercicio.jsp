<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.servlet.grafico.GeradorGrafico" %>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<script type="text/JavaScript"  src="overlib.js"></script>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<body class="corpo_popup">
<%
try{
%>
<H1>Gráfico de Valores Totais Realizado por Exercício</H1>

<form name="form" method="post" action="">
	<table class="form">
		<tr> 
			<td>
				<img src='<%=_pathEcar%>/GraficoRealizadoPorExercicio?codAri=<%=Pagina.getParamStr(request, "codAri")%>&codExe=<%=Pagina.getParamStr(request, "codExe")%>&codIettir=<%=Pagina.getParamStr(request, "codIettir")%>' usemap="#chart" >
			</td>
		</tr>
	</table>
</form>
<%
//renderiza o mapa da gráfico
GeradorGrafico g = new GeradorGrafico();
g.gerarGraficoPorExercicio(request);
out.println(g.getMap());

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}


%>



</body>

</html>

