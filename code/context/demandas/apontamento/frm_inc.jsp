
<jsp:directive.page import="ecar.taglib.util.BarraLinkDemandaVisaoTag"/><%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>

<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.dao.RegDemandaDao" %>

<%@ page import="comum.util.Data"%>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 


<%@page import="ecar.pojo.VisaoDemandasVisDem"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validar(form){
	if(!validaString(form.infoRegda,'Comentário',true)){
		return(false);
	}

	return(true);
}
function aoClicarGravar(form){
	if(validar(form)){
		form.hidAcao.value = "incluir";
		form.action = "../apontamento/ctrl.jsp";
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
function aoClicarVoltar(form, codRegd) {
	form.hidAcao.value = "cancelar";
	form.action = "../apontamento/ctrl.jsp";
	form.codRegd.value = codRegd;
	form.submit();

}
function aoClicarApontamento(form) {
	form.action = "../registro/frm_cons.jsp";
	form.submit();
}
function aoClicarTrocarAba(form, pagina){
	document.form.action = pagina; 
	document.form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">

<%
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	
	String acao = "incluir";
	
	String tabAtual = Pagina.getParam(request, "tabAtual");

	if(tabAtual==null || tabAtual.length()==0) {
		tabAtual = BarraLinkDemandaVisaoTag.TAB_ENCAMINHAMENTO;
	}
	String visaoDescricao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getDescricaoVisao();
	String telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
	String codAba = Pagina.getParamStr(request, "codAba");
	String codIett = Pagina.getParamStr(request, "codIett");
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>	
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
	<!-- TITULO -->
	<%@ include file="/titulo_tela_visao.jsp"%>
	<div id="linkstop">
		<a href="javascript:aoClicarVoltar(form, <%=regDemanda.getCodRegd()%>);">Voltar</a>
	</div>

	<util:barraLinkDemandaVisaoTag
		tabAtual="<%=tabAtual%>"
		chamarPagina="../registro/frm_cons.jsp"
		codRegd ="<%= Pagina.getParam(request, "codRegd") %>"
		regDemanda = "<%=regDemanda %>"
	/>  
	
	
		
	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="linha_titulo" align="center">
			<td colspan="6">
			<input name="btnGravar" type="button" value="Gravar" class="botao" onclick="aoClicarGravar(form);">
			<input name="btnVoltar" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltar(form, <%=Long.valueOf(Pagina.getParam(request, "codRegd"))%>);">
			</td>
		</tr>
	</table>
	
<%
	RegApontamentoRegda regApontamento = new RegApontamentoRegda();
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="linha_titulo" align="center">
			<td colspan="6">
			<input name="btnGravar" type="button" value="Gravar" class="botao" onclick="aoClicarGravar(form);">
			<input name="btnVoltar" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltar(form, <%=Long.valueOf(Pagina.getParam(request, "codRegd"))%>);">
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>