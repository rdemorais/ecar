<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.dao.AbaDao" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.pojo.Aba" %>
<%@ page import="ecar.pojo.FuncaoFun" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

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
<script language="javascript" src="<%=_pathEcar%>/js/selectMultiplos.js" type="text/javascript"></script>

<script language="javascript" type="text/javascript">
function validarAlterar(form){
	if (!validarOrdens(form)) {
		return(false);
	}

	if (!validarLabels(form)) {
		return(false);
	}

	if (!validaPosicaoAbas()){
		return(false);
	}

	return(true);
}
function abrirPopUpUpload(nome, labelCampo) {
	abreJanela("../../configuracoes/abas/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir=", 500, 100, nome);
}
function aoClicarBtn4(form){
	document.forms[0].action = "frm_inc.jsp";
	document.forms[0].submit();

}

</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data" >
	<input type="hidden" name="hidAcao" value="alterar">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%
	try {
		AbaDao abaDao = new AbaDao(request);
%>
		<util:barrabotoes alterar="Gravar" btn4="Cancelar"/>
		
		<%@include file="form.jsp" %>
	
		<util:barrabotoes alterar="Gravar" btn4="Cancelar"/>
<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript" type="text/javascript">
			document.form.action = "ctrl.jsp";
			document.form.submit();
		</script>
		<%
	} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
	}
%>
	
</form>

</div>
<%@ include file="/include/mensagemAlert.jsp"%>
</body>
</html>