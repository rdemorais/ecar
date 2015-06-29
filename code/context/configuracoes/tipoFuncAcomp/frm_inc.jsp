<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<%@ include file="validacao.jsp" %>
<script language="javascript" type="text/javascript">
function validarGravar(form){
	return valida(form);
}
</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>
<%


try{
	TipoFuncAcompTpfa tipoFuncAcomp = new TipoFuncAcompTpfa();
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	
	_disabled = "";
	
	//Excluir as funções filhos, neto **********************
	List funcoesFilho = new ArrayList();
	
	//List funcoesPermitidos = new ArrayList();
   	//funcoesPermitidos = tipoFuncAcompDao.getTipoFuncAcompPermitidos(funcoesFilho);
   	
   	String strReadOnly = "false";
	boolean blVisualizaDesc = true;
	String _pesqdisabled = "";
	
%>
<div id="conteudo">

<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%@ include file="form.jsp"%>
	
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>