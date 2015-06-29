<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.UnidadeOrcamentariaUO" %>
<%@ page import="ecar.dao.UnidadeOrcamentariaDao" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript">

function validarAlterar(form){
	if(form.orgaoOrg.value == ""){
		alert("<%=_msg.getMensagem("unidadeOrcamentaria.validacao.orgaoOrg.obrigatorio")%>");
		form.orgaoOrg.focus();
		return(false);
	}
	if(!validaNum(form.codigoIdentUo, "Código", false)){
		alert("<%=_msg.getMensagem("unidadeOrcamentaria.validacao.codigoIdentUo.invalido")%>");
		form.codigoIdentUo.focus();
		return(false);
	}
	if(!validaString(form.descricaoUo, "Nome Unidade", true)){
		return(false);
	}
	if(!validaString(form.siglaUo, "Sigla", true)){
		return(false);
	}
	
	return(true);
}

function limpar(form){

	form.codUsu.value = "";
	form.nomeUsu.value = "";
}

function getDadosPesquisa(codigo, descricao){
	document.form.codUsu.value = codigo;
	document.form.nomeUsu.value = descricao;
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	try{
		UnidadeOrcamentariaDao unidadeDao = new UnidadeOrcamentariaDao(request);
		UnidadeOrcamentariaUO unidade;
		
		if(session.getAttribute("objUnidadeOrcamentaria") != null){
			unidade = (UnidadeOrcamentariaUO) session.getAttribute("objUnidadeOrcamentaria");
			session.removeAttribute("objUnidadeOrcamentaria");
		}else{
			unidade = (UnidadeOrcamentariaUO) unidadeDao.buscar(UnidadeOrcamentariaUO.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";

		String comboAdm = "";
		String comboOrc = "";
%>
		<input type="hidden" name="codigo" value="<%=unidade.getCodUo()%>">
	
		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
<%
	} catch ( ECARException e){
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>