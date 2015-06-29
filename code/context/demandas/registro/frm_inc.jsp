<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>

<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="comum.util.Data"%>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 


<%@page import="ecar.dao.VisaoDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	adicionaEntidade(codigo, descricao);
}

function aoClicarGravar(form){
	if(validar(form)){
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp"
		form.submit();
	}
}	

function aoClicarCancelar(form){
	form.reset();
	focoInicial(form);
}

function onLoad(form) {
	aoClicarCancelar(form);
}

function aoClicarVoltar(form) {
	form.hidAcao.value = "";
	form.action = "lista.jsp";
	form.submit();
}
</script>

<%String acao = "incluir";%>
<%@ include file="funcoes.jsp"%>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
	String visaoDescricao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getDescricaoVisao();
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="origem" value="frm_inc.jsp">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela_visao.jsp"%>
	
	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

<%
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	VisaoDao visaoDao = new VisaoDao(request);
	RegDemandaRegd regDemanda;
	
	if(session.getAttribute("objRegDemanda") != null){
		regDemanda = (RegDemandaRegd) session.getAttribute("objRegDemanda");
		session.removeAttribute("objRegDemanda");
	}else{
		regDemanda = new RegDemandaRegd();
  	}
	
	_disabled = "";
%>

	<table class="form">
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	
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