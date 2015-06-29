<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta"%>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.util.Dominios"%>

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 

	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
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

<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/tableSort.js"></script>

<script>
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function recarregar(){
	document.forms[0].action = "eventos.jsp";
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

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 


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
		funcaoSelecionada="EVENTOS"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/>

<br>

<form  name="form" method="post" >	
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
<%
}
%>

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

<input type="hidden" name="hidAcao" value="">

<div id="subconteudo">

<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
		<b>Eventos</b>
		</td>
	</tr>
</table>

<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<thead> 
		<tr><td class="espacador" colspan="4"><img src="../images/pixel.gif"></td></tr>
		<tr class="linha_subtitulo">
			<td >
				<a href="#" onclick="this.blur(); return sortTable('corpo', 0, true);" title="Data">Data</a>
			</td>
			<td>
				<a href="#" onclick="this.blur(); return sortTable('corpo', 1, true);" title="Descricao">Descri&ccedil;&atilde;o</a>
			</td>
			<td>
				<a href="#" onclick="this.blur(); return sortTable('corpo',  2, true);" title="Última atualização">Última atualização</a>
			</td>
			<td>
				<a href="#" onclick="this.blur(); return sortTable('corpo',  3, true);" title="Data de inclusão">Data de inclusão</a>
			</td>
		</tr>
		<tr><td class="espacador" colspan="4"><img src="../images/pixel.gif"></td></tr>	
	</thead>
	<tbody id="corpo">	
	<%
		/* Percorre a lista de Beneficiários de ItemEstrutura e imprime na tela */
		if(itemEstrutura.getItemEstrutAcaoIettas() != null ){
			List acoes = itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstrutAcaoIettas(), "this.dataIetta", "desc");
			Iterator itAcao = acoes.iterator();
			while(itAcao.hasNext()){
				ItemEstrutAcaoIetta ieAcao = (ItemEstrutAcaoIetta) itAcao.next();
				// ignorar registro inativo
				if(Dominios.NAO.equals(ieAcao.getIndAtivoIetta())) {
					continue;
				}
				%>
				<tr class="linha_subtitulo2">
					<td valign="top">						
						<%=Pagina.trocaNullData(ieAcao.getDataIetta())%>
					</td>
					<!-- td><div id="pre"><%=ieAcao.getDescricaoIetta()%></div></td -->
					<td><%=ieAcao.getDescricaoIetta()%></td>
					<td valign="top"><% if (ieAcao.getUsuarioUsu() != null) %><%=ieAcao.getUsuarioUsu().getNomeUsu()%></td>
					<td valign="top"><% if (ieAcao.getUsuarioUsu() != null) %><%=Pagina.trocaNullData(ieAcao.getDataInclusaoIetta())%></td>
				</tr>	
				<%
			} 
		}
%>
	</tbody>
</table>
<!-- ############### Listagem  ################### -->

</div>
</form>

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

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>