<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ include file="../../frm_global.jsp"%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script language="javascript">
function validarPesquisar(form){
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%
try{
%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	LocalItemLit localItem;
	LocalItemDao localItemDao = new LocalItemDao(request);
	
	if(session.getAttribute("objPesquisa") != null  && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		localItem = (LocalItemLit) session.getAttribute("objPesquisa");
	}else{
		localItem = new LocalItemLit();
  	}
  	
  	boolean pesquisa = true;
  	
	_disabled = "";
	_obrigatorio = "";
	_comboSimNao = "Todos";
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>