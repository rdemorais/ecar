
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaItemAri"/>
<jsp:directive.page import="ecar.dao.AcompReferenciaItemDao"/>
<jsp:directive.page import="comum.util.Data"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ApontamentoApt" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.ApontamentoDao" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
  
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %> 
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>

<%
	FuncaoDao funcaoDao = new FuncaoDao(request);
	long codABA = funcaoDao.getCodFuncaoDadosGerais();
	
	
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_APONTAMENTO);
	
	//pega os parametros do request
	String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
	boolean telaVisualizacao = Pagina.getParamStr(request, "tela").equals("V");
	String formaVisualizacaoControle =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");	
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	
	
%>

<script>

function aoClicarBtn2(form){
	if(validarExclusao(form, "excluir")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.apontamentos.exclusao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
		form.submit();
	}
}


function aoClicarIncluir(form) {
	//form.hidAcao.value = "incluir";
	form.action = "frm_inc.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
	form.submit();
}

</script>


<script language="javascript">

<% if (telaVisualizacao) { %>
		function trocaAba(link, codAri){
			document.forms[0].codAri.value = codAri;
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>" ;
			document.forms[0].submit();
		}


		function trocaAbaImpressao(link){
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = + link;
			document.forms[0].submit();
		}
		
		function trocarAba(nomeAba) {
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = nomeAba;
			document.forms[0].submit();
		}
		
		function voltarTelaAnterior(tela) {
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = tela;
			document.forms[0].submit();
		}
		
		function aoClicarVoltar(form){
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.form.action = "../frm_con.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
			document.form.submit();	
		}
	
		function mudaTela(cod){
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.form.cod.value = cod;
			document.form.codPtc.value = cod;
			document.form.action = "../frm_con.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>";
			document.form.submit();	
		}

<% } else { %>

	function aoClicarConsultar(form, cod){
		document.form.codApt.value = cod;
		document.form.action = "frm_con.jsp?hidFormaVisualizacaoEscolhida=" + "<%=formaVisualizacaoControle%>" + "&codTipoAcompanhamento=" + "<%=codTipoAcompanhamento%>";
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

<% } %>


</script>
 
</head>

<%@ include file="../../../cabecalho.jsp" %>
<%@ include file="../../../divmenu.jsp"%> 

<body> 
<div id="conteudo"> 

<% 
try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	String codIett = Pagina.getParamStr(request, "codIett");
	ItemEstruturaIett itemEstrutura = null;
	if(codIett!=null && !codIett.equals("")) {
		itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIett));
	}
	
	boolean permissaoIett = true;
	
	if ( !validaPermissao.permissaoConsultaIETT(Long.valueOf(codIett), seguranca ) ){
//		{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
		permissaoIett = false;
	}
	
	PontoCriticoPtc pontoCritico = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "codPtc")));
	ApontamentoDao apontamentoDao = new ApontamentoDao(request);
	
	String codAba = Pagina.getParamStr(request, "codAba");

	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), new FuncaoDao(request).getFuncaoPontosCriticos().getCodFun());
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoApontamento);

	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	String funcaoSelecionada = "PONTOS_CRITICOS";
	String periodo = Pagina.getParamStr(request, "periodo");
	
	if (telaVisualizacao){ // se tela = "V" provém de visualização dado na URL	
		AcompReferenciaItemAri acompReferenciaItem = ari; %>
		<%@ include file="../../form_visualizar.jsp" %>
	<% } else {
		AcompReferenciaAref	arefSelecionado = null;
		arefSelecionado = ari.getAcompReferenciaAref();%>
		<%@ include  file="../../form_registro.jsp" %>
	<%} %>  
<br>

<div id="subconteudo">
 
<form name="form" method="post">
<input type="hidden" name="autorizarMail" value="N">
<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="codAba" value="<%=codABA%>">
<input type=hidden name="codPtc" value="<%=pontoCritico.getCodPtc()%>">
<input type=hidden name="cod" value=""> 
<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">

<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder e o link voltar -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">
<%if (!Pagina.getParamStr(request, "codIett").trim().equals("")) {%>
	<input type="hidden" name="codIett"       value="<%=Pagina.getParamStr(request, "codIett")%>">
<%} else { %>
	<input type="hidden" name="codIett"       value="<%=ari.getItemEstruturaIett().getCodIett()%>">
<%} %>
<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
<input type="hidden" name="codAba"        value="<%=codABA%>">
<input type="hidden" name="labelEttf"     value="<%=estruturaFuncao.getLabelEttf()%>">
<input type="hidden" name="Usuario"       value="<%=usuario.getCodUsu().toString()%>">
<input type="hidden" name="codApt"        value="">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="tela" value="<%=Pagina.getParamStr(request, "tela")%>">	

<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">

	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<!-- ############### Cabeçalho de Restrições  ################### -->
<%@ include file="cabecalho.jsp"%>

 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
			<%if(permissaoAlterar.booleanValue() && !telaVisualizacao  && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){%>
				<input type="button" value="Adicionar " class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
				<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarBtn2(document.form)">
			<%}%>
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=4><img src="../../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="5%">
			<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
		</td>
		<td>Data</td>
		<td>Autor</td>
		<td>Texto</td>
	</tr>
	<tr><td class="espacador" colspan=4><img src="../../../images/pixel.gif"></td></tr>
	<%
		/* Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela */
		Set apontamentos = pontoCritico.getApontamentoApts();
		if(apontamentos != null ){ 
					Iterator itApt = apontamentos.iterator();
					while(itApt.hasNext()){
						ApontamentoApt apontamento = (ApontamentoApt) itApt.next();
						%>
						<tr class="linha_subtitulo2"> 
							<td>
								<input type="checkbox" class="form_check_radio" name="excluir" value="<%=apontamento.getCodApt()%>">
							</td>
							<td>	
								<%if (!telaVisualizacao){ %>
								<a href="javascript:aoClicarConsultar(document.form,<%=apontamento.getCodApt()%>)">
								<%=Pagina.trocaNullData(apontamento.getDataInclusaoApt())%></a>
								<%} else { %>
									<%=Pagina.trocaNullData(apontamento.getDataInclusaoApt())%>
								<%} %>
							</td>
							<td><%=apontamento.getUsuarioUsu().getNomeUsuSent()%></td>																	
							<td><%=apontamento.getTextoApt()%></td>										
						</tr>	
						<%
					}  
				}			
} catch ( ECARException e ){ 
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	<!-- ############### Listagem  ################### -->

<table>
</div>
<br>
</div>

</form>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../../include/estadoMenu.jsp"%>
<%@ include file="../../../include/mensagemAlert.jsp" %>
</html>
