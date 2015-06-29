<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.TextosSiteTxt" %>
<%@ page import="ecar.dao.TextosSiteDao" %>
<%@ include file="../../frm_global.jsp"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">

function validarAlterar(form){
	if(form.empresaEmp.value == ""){
		alert("<%=_msg.getMensagem("textoSite.validacao.empresaEmp.obrigatorio")%>");
		form.empresaEmp.focus();
		return(false);		
	}

	if(form.idiomaIdm.value == ""){
		alert("<%=_msg.getMensagem("textoSite.validacao.idiomaIdm.obrigatorio")%>");
		form.idiomaEmp.focus();
		return(false);		
	}

	if(!validaString(form.descricaoUsoTxts, "Descrição", true)){
		return false;		
	}	

	if(!validaString(form.textoTxts, "Texto", true)){
		return false;		
	}	
	return true;

}

</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<h1>Atributos</h1>
	
<%	try {
		TextosSiteDao textoSiteDao = new TextosSiteDao(request);
	
		TextosSiteTxt textoSite = (TextosSiteTxt) textoSiteDao.buscar(TextosSiteTxt.class, Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
		_disabled = "";
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigoTxts" value="<%=textoSite.getCodTxtS()%>">
		<input type="hidden" name="codigoEmp" value="<%=textoSite.getEmpresaEmp().getCodEmp()%>">
		<input type="hidden" name="codigoIdm" value="<%=textoSite.getIdiomaIdm().getCodIdm()%>">				

		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<!-- desabilitar os combos -->
		<script language="javascript">
			document.form.empresaEmp.disabled=true;
			document.form.idiomaIdm.disabled=true;
			document.form.descricaoUsoTxts.focus();
		</script>

<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript">
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

</body>
</html>