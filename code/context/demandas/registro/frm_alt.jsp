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

function aoClicarAlterar(form){
	if(validar(form)){
		form.hidAcao.value = "alterar";
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
	if(form.origem.value != "" && form.origem.value == "frm_cons.jsp"){
		form.action = form.origem.value;
	}else{
		form.action = "lista.jsp";
	}
	form.submit();
}
function aoClicarDemandas(form, numPagina, totPaginas){ 
	document.form.hidNumPagina.value = numPagina;
	document.form.hidTotPagina.value = totPaginas;
	if (document.getElementById('telaAssociacaoDemandas').value == 'S'){
		form.action = "<%=_pathEcar%>/cadastroItens/associacaoDemandas/lista.jsp";
	}
	else if (document.getElementById('telaDetalhamentoDemanda').value == 'S'){
		form.action = "frm_cons.jsp";
	}
	else {
		form.action = "lista.jsp";
	}
	
	document.form.submit();
}



</script>

<%String acao = "alterar";%>
<%@ include file="funcoes.jsp"%>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
	//Guarda a pagina que chamou a funcionalidade
	int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
	int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");

	String origem = "frm_alt.jsp";
	if (!"".equals(Pagina.getParamStr(request, "origem"))) {
		origem = Pagina.getParam(request, "origem");
	}
	String visaoDescricao = "";
	VisaoDemandasVisDem visaoSelecionada = (VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA);
	if(visaoSelecionada !=null){
		visaoDescricao = visaoSelecionada.getDescricaoVisao();
	}
	String telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
	String telaDetalhamentoDemanda = Pagina.getParamStr(request, "telaDetalhamentoDemanda");
	String codAba = Pagina.getParamStr(request, "codAba");
	String codIett = Pagina.getParamStr(request, "codIett");
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="origem" value="<%=origem%>">
	<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">
	<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="telaDetalhamentoDemanda" name="telaDetalhamentoDemanda" value="<%=telaDetalhamentoDemanda%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
	<!-- TITULO -->
	<%@ include file="/titulo_tela_visao.jsp"%>
	<div id="linkstop">
		<a href="javascript:aoClicarDemandas(form,'<%=hidNumPagina%>', '<%=hidTotPagina%>')">Voltar</a>
	</div>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
<%
	RegDemandaRegd regDemanda;
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	VisaoDao visaoDao = new VisaoDao(request);
	
	if(session.getAttribute("objRegDemanda") != null){
		regDemanda = (RegDemandaRegd) session.getAttribute("objRegDemanda");
		session.removeAttribute("objRegDemanda");
	}else{
		regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	}
	
	_disabled = "";
%>
	<table class="form">
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
		<%@ include file="form.jsp"%>
	</table>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
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