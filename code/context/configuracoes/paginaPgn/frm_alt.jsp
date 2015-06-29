<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.PaginaPgn" %>
<%@ page import="ecar.dao.PaginaDao" %>
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
	if(form.paginaAreaSitePa.value == ""){
		alert("<%=_msg.getMensagem("paginaPgn.validacao.paginaAreaSitePa.obrigatorio")%>");
		form.paginaAreaSitePa.focus();
		return(false);	
	}
	if(!validaString(form.nomePgn,"Nome da Página",true)){
		return(false);
	}
	if(!validaString(form.tituloPgn,"Título da Página",true)){
		return(false);
	}
	if(form.idiomaIdm.value == ""){
		alert("<%=_msg.getMensagem("paginaPgn.validacao.idiomaIdm.obrigatorio")%>");
		form.idiomaIdm.focus();
		return(false);	
	}
	if(!validaString(form.urlPgn,"URL",true)){
		return(false);
	}
	if(form.indHomePgn[0].checked == false && form.indHomePgn[1].checked == false){
	alert("<%=_msg.getMensagem("paginaPgn.validacao.indHomePgn.obrigatorio")%>");
		return(false);
	}
	if(form.indLoginPgn[0].checked == false && form.indLoginPgn[1].checked == false){
		alert("<%=_msg.getMensagem("paginaPgn.validacao.indLoginPgn.obrigatorio")%>");
		return(false);
	}
	if(form.indMapaPgn[0].checked == false && form.indMapaPgn[1].checked == false){
		alert("<%=_msg.getMensagem("paginaPgn.validacao.indMapaPgn.obrigatorio")%>");
		return(false);
	}
	if(form.indMapaPgn[0].checked == true && !validaString(form.tituloMapaPgn,"Título do Mapa",true)){
		return(false);
	}
	if(form.indMapaPgn[0].checked == true && !validaString(form.descricaoMapaPgn, "Descrição do Mapa", true)){
		return(false);
	}
	if(!validaString(form.qtdSubAreaPgn, "Qtde. de Sub-Áreas", true)||!validaNum(form.qtdSubAreaPgn, "Qtde. de Sub-Áreas", true)){
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

<%	try{
		PaginaDao paginaDao = new PaginaDao(request);
		PaginaPgn paginaPgn = (PaginaPgn) paginaDao.buscar(PaginaPgn.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
		_disabled = "";
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
		
		<input type="hidden" name="codigo" value="<%=paginaPgn.getCodPgn()%>">
		
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
		document.form.action ="ctrl.jsp";
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