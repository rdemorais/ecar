
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.AcompReferenciaItemDao"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaItemAri"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ApontamentoApt" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.pojo.UsuarioUsu" %> 
<%@ page import="ecar.dao.ApontamentoDao" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>

<%@ page import="comum.util.Data" %>

<html lang="pt-br">
<head>
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script> 
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_APONTAMENTO);
	String formaVisualizacaoControle =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
%>

<script>

function aoClicarBtn2(form)
{
	if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
		if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.apontamentos.criacao.autorizaEnviaEmail")%>') == true )) {
			document.form.autorizarMail.value = 'S';
		} 
	}
	if(validar(form)){
		document.form.btn2[0].disabled = true;
		document.form.btn2[1].disabled = true;
		document.form.hidAcao.value = "incluir";
		document.form.action = "ctrl.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
		document.form.submit();
	}
}

</script>


<script>
<%@ include file="validacao.jsp"%>
function aoClicarVoltar(form){
	document.form.action = "lista.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
	document.form.submit();	 
}
function mudaTela(cod){
	document.form.cod.value = cod;
	document.form.action = "../frm_con.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
	document.form.submit();	
}

function trocarAba(nomeAba) {
	document.forms[0].action = '../' + nomeAba + "?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
	document.forms[0].submit();
}

function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}


</script>

</head>

<%@ include file="../../../cabecalho.jsp" %>
<%@ include file="../../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ApontamentoApt apontamento = new ApontamentoApt();
	PontoCriticoPtc pontoCritico = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "codPtc")));		
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), new FuncaoDao(request).getFuncaoPontosCriticos().getCodFun());
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	
	//pega os parametros do request
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	
	AcompReferenciaAref	arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();
	String funcaoSelecionada = "PONTOS_CRITICOS";
	String periodo = Pagina.getParamStr(request, "periodo");
%>
		
	<%@ include  file="../../form_registro.jsp" %>

	
	


<!-- ############### Cabeçalho de Restrições  ################### -->
<%@ include file="cabecalho.jsp"%>

	
	
	<form  name="form" method="post" >
 	<input type="hidden" name="autorizarMail" value="N">
 	<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
	  <util:barrabotoes btn2="Gravar" voltar="Cancelar"/>

	<table name="form" class="form">
	
	<%@ include file="form.jsp"%>
	</table> 
	  <util:barrabotoes btn2="Gravar" voltar="Cancelar"/>

</table>

</form>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	%>
	<script>
	document.form.action = "lista.jsp";
	document.submit();
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>

<%@ include file="../../../include/mensagemAlert.jsp" %>
</html>