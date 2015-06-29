<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.dao.ItemEstrutFisicoDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>


<%@ include file="../../include/exibirAguarde.jsp"%>

<%@page import="comum.util.Data"%>
<%@page import="ecar.pojo.historico.HistoricoItemEstruturaIett"%>
<%@page import="ecar.pojo.historico.HistoricoItemEstrtIndResulIettr"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script type="text/javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	form.action = "lista_historico.jsp";
	form.submit();	
}

var popup = null;
</script>

</head>

<body onload="focoInicial(document.form);atualizarInfPrevistoLocal();" onunload="popup.close();">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
String tipoAcao = "alterar";
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
	//HistoricoItemEstruturaIett itemEstrutura = (HistoricoItemEstruturaIett) itemEstruturaDao.getHistorico(Long.valueOf(Pagina.getParamStr(request, "codIett")), (Data.addDias(-3, Data.getDataAtual())));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>

<%@ include file="/titulo_tela.jsp"%>

<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>"/>
 
<util:barraLinksItemEstrutura estrutura="<%=itemEstrutura.getEstruturaEtt()%>" selectedFuncao="<%=codAba%>" codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>"/>

<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	//EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	//estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	//estruturaFuncao = itemEstrutura.getEstruturaEtt().gete;
%>

<br><br>

<!-- TITULO -->


<form name="form" method="post">
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
	HistoricoItemEstrtIndResulIettr itemEstrtIndResul = new HistoricoItemEstrtIndResulIettr();
	_disabled = "";
	String _disabledQtdePorLocal = "";
	String _msgQtdePorLocal = "* " + _msg.getMensagem("itemEstrutura.indResultado.alteracao.exiteEmArf");
	
	String _disabledPrevQtdePorLocal = "";
	String _msgPrevQtdePorLocal = "* " + _msg.getMensagem("itemEstrutura.indResultado.alteracao.exiteEmArf");
	
	//if(!"S".equals(Pagina.getParamStr(request, "existeNomeGraficoGrupo"))){
	//	if (itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request, "codIettir"))) != null){
	//		itemEstrtIndResul = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request, "codIettir")));
	//	}
	//}else{
	//	itemEstrtIndResul = (ItemEstrtIndResulIettr) session.getAttribute("indicadorResultado");
	//}
	
	if((itemEstrtIndResul.getAcompRealFisicoArfs() != null) && (itemEstrtIndResul.getAcompRealFisicoArfs().size() > 0))
		_disabledQtdePorLocal = "disabled";
//		_disabledPrevQtdePorLocal = "disabled";		
	
%>

	<input type="hidden" name="codIettir" id="codIettir" value="<%=Pagina.trocaNull(itemEstrtIndResul.getCodIettir())%>">
	<table class="form">
		<%@ include file="frm_historico.jsp"%>
	</table>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
</form>

<script language="JavaScript">
if( form.indAcumulavelIettr[1].checked == true) {
	document.getElementById('linha').style.display = "";
}
</script>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
<%@ include file="../../include/ocultarAguarde.jsp"%>
</html>