<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %> 
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %> 

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>


<script language="javascript" src="../js/datas.js"></script>
<script language="javascript" src="../js/numero.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/limpezaCamposMultivalorados.js"></script>

<script>
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function recarregar(){
	document.forms[0].action = "dadosGerais.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<%
try{
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
%>

<div id="conteudo">
	<%
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	if(!"".equals(strCodAri)) {
		acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
	
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if ( !validaPermissao.permissaoConsultaParecerIETT( itemEstrutura.getCodIett() , seguranca ) )
		{
			response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
		}			
		
		
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	%>
	
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		chamarPagina="posicaoGeral.jsp"
	/> 

	<%@ include file="botoesAvancaRetrocede.jsp" %>

	<util:arvoreEstruturas 
		itemEstrutura="<%=itemEstrutura%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/> 

	<br>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="DADOS_GERAIS"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/> 
	
	<br>

	<form  name="form" method="post" >		
	
	<!-- Campos para manter a seleção em Posição Geral -->
	<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
	<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">	
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">

	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	
	<!-- Campos para manter a seleção em Posição Geral -->
	<%
	if(acompReferenciaItem != null) {
	%>
		<table border="0" width="100%">
			<tr>
				<td valign="bottom" class="texto" align="left" colspan="2"> 
					<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
					<b>Mês de referência: </b>
					<combo:ComboReferenciaTag
						nome="referencia"
						acompReferenciaItem="<%=acompReferenciaItem%>"
						scripts="onchange='recarregar()'"
					/>			
				</td>
			</tr>
		</table>
		<input type="hidden" name="codAri" value="<%=acompReferenciaItem.getCodAri()%>">
	<%
	}
	%>
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			<b>Dados Gerais</b>
			</td>
		</tr>
	</table>

	<table name="form" class="form" width="100%">
<%
EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();

EstruturaDao estruturaDao = new EstruturaDao(request);
List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);


if(atributos != null){
	Iterator it = atributos.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura atributo="<%=atributo%>" itemEstruturaIett="<%=itemEstrutura%>" desabilitar="<%=new Boolean(true)%>" seguranca="<%=seguranca%>" exibirBotoes="<%=new Boolean(false)%>"  contextPath="<%=request.getContextPath()%>" request="<%=request %>"/>	
		<%
	} 
}
%>

</table>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label" align="left">
				&nbsp;
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

</html>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
