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
function verSubAreas(){
	if(validaString(document.forms[0].destaqueAreaDtqa,'Área',true)){
		document.forms[0].action = "selecaoSubArea.jsp";
		document.forms[0].submit();
	}
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

<table class="form"> 
	<tr><td colspan="2"></td></tr>
	<tr>
		<td class="label">Selecione a Área</td>
		<td>
		<combo:ComboTag 
				nome="destaqueAreaDtqa"
				objeto="ecar.pojo.DestaqueAreaDtqa" 
				label="nomeDtqa" 
				value="codDtqa" 
				order="nomeDtqa"
		/>				
		</td>		
	</tr>
	<tr>
		<td class="label">&nbsp;</td>
		<td>
		<input type="button" value="Ver Subáreas" onclick="javascript:verSubAreas()">				
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