<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.PaginaAreaSitePa" %>
<%@ page import="ecar.dao.PaginaAreaSiteDao" %>
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
<script language="javascript" src="../../js/numero.js"></script>

<script language="javascript">
function validarAlterar(form){
	if(!validaString(form.nomePas, "Nome", true)){
		return(false);
	}
	if(!validaString(form.textoPas, "Texto", true)){
		return(false);
	}
	if(form.indCapaPas[0].checked == false && form.indCapaPas[1].checked == false){
		alert("<%=_msg.getMensagem("paginaAreaSite.indCapaPas.validacao.indCapaPas.obrigatorio")%>");
		return(false);
	}
	if(!validaString(form.urlPas, "URL", true)){
		return(false);
	}
	if(!validaString(form.corPas, "Cor", true)){
		return(false);
	}
	
	if(!validaString(form.seqApresentacaoPas, "Seqüência", true)||!validaNum(form.seqApresentacaoPas, "Seqüência", true)){
		return(false);
	}
	return(true);
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
	<%@ include file="/titulo_tela.jsp"%>
	
<%	

	boolean ehPesquisa = false;

	try {

		PaginaAreaSiteDao paginaAreaSiteDao = new PaginaAreaSiteDao(request);
		PaginaAreaSitePa paginaAreaSite = (PaginaAreaSitePa) paginaAreaSiteDao.buscar(PaginaAreaSitePa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
		_disabled = "";
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=paginaAreaSite.getCodPas()%>">

		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

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