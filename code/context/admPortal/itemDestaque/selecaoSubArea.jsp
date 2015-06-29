<%@ page import="ecar.pojo.DestaqueAreaDtqa" %>
<%@ page import="ecar.dao.DestaqueAreaDao" %>
<%@ page import="ecar.pojo.DestaqueSubAreaDtqsa" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function voltar(){
	window.location = "selecaoArea.jsp";
}
</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">

<!-- TITULO -->
<%@ include file="/titulo_tela.jsp"%>

<%
DestaqueAreaDtqa destaqueArea = (DestaqueAreaDtqa) new DestaqueAreaDao(request).buscar(
										DestaqueAreaDtqa.class, 
										Long.valueOf(Pagina.getParamStr(request, "destaqueAreaDtqa")));
Set subAreas = destaqueArea.getDestaqueSubAreaDtqsas();
int numColunas = 0;
if(destaqueArea.getQtdColunasDtqa() != null) {
	numColunas = destaqueArea.getQtdColunasDtqa().intValue();
}
int numLinhas = subAreas.size() / numColunas;
if(subAreas.size() % numColunas > 0){
	numLinhas++;
}
double larguraCol = 100 / numColunas;
Iterator it = subAreas.iterator();
%>

<table class="form"> 
	<tr>
		<td colspan="<%=numColunas%>"></td>
	</tr>
	<%
	if(numLinhas == 0){
		%>
		<tr>
			<td><b>Nenhuma sub-Área Cadastrada</b></td>
		</tr>		
		<%
	}
	%>
	<%
	for(int i = 1; i <= numLinhas; i++){
		%>
		<tr>
			<%
			for(int l = 1; l <= numColunas; l++){
				%>
				<td width="<%=larguraCol%>%" valign="top">
				<%
				if(it.hasNext()){
					DestaqueSubAreaDtqsa subArea = (DestaqueSubAreaDtqsa) it.next();
					%>					
					<b><a href="destaqueItem.jsp?codSubArea=<%=subArea.getCodDtqsa()%>"><%=subArea.getIdentificacaoDtqsa()%></a></b>
					<br>
					(contém <%=subArea.getDestaqueItemRelDtqirs().size()%> de <%=subArea.getQtdMaxItensDtqsa()%>)
					<br>
					<%=subArea.getDescricaoDtqsa()%>
					<%
				} else {
					%>
					&nbsp;
					<%
				}
				%>
				</td>
				<%
			}
			%>
		</tr>
		<%
	}
	%>
	<tr>
		<td colspan="<%=numColunas%>" class="label"> 
		<input type="button" value="Voltar" onclick="voltar()">				
		</td>		
	</tr>	
</table> 

</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>