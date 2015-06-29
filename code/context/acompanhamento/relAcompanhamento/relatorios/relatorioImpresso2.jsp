
<jsp:directive.page import="ecar.dao.AcompReferenciaDao"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.TipoAcompTipofuncacompSisatributoTaTpfaSatbDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.ConfiguracaoDao" %> 
<%@ page import="ecar.pojo.ConfiguracaoCfg" %> 
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	List listaFilhos = new ArrayList();	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String strCodigosAri = Pagina.getParamStr(request, "codigosAri");
	String[] codAriFilhos = request.getParameterValues("codAriFilhos");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String periodo = Pagina.getParamStr(request, "periodo");
	AcompReferenciaAref acompReferenciaAref = null;
	AcompReferenciaItemAri acompReferenciaItem = new AcompReferenciaItemAri();
	AcompReferenciaItemAri ari = new AcompReferenciaItemAri();
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	
	if(mesReferencia != null && !mesReferencia.equals("")) {
		acompReferenciaAref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
	}	
	
	if (strCodAri != null && !"".equals(strCodAri)){
		acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	} else {
		if(itemDoNivelClicado != null && !itemDoNivelClicado.equals("")) {
			if(itemDoNivelClicado.contains("_org")) {
				String strCodItem = itemDoNivelClicado.substring(0, itemDoNivelClicado.indexOf("_org"));
 				itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(strCodItem));
			} else {
				itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			}
 				acompReferenciaItem = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaAref, itemEstrutura);
		}
	}
	
	if(acompReferenciaItem != null) {
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
		if (acompReferenciaAref == null){
			acompReferenciaAref = acompReferenciaItem.getAcompReferenciaAref();
		}
	}
	
	String funcaoSelecionada = "RELATORIO";
	
%>


<%@page import="java.util.HashSet"%><html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script>
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}

function aoClicarGerar(form){
	if(validar(form)){
		form.target = "_blank"; // Referente ao BUG 3048
		form.action = "<%=request.getContextPath()%>/RelatorioAcompanhamentoImpresso";
		form.submit();
		form.target = "";
		/*É necessário setar o target com "" pq senão ele ficaria como "_blank" e 
		em cada submit (seja no cancelar, seja no gerar, ou qualquer outro submit)
		ele abria uma nova janela mesmo assim. */
	}
}

function aoClicarCancelar(form){
	form.action = "relatorioImpresso.jsp";
	form.submit();
}

function validar(form){
	//se posições for selecionado, deve validar se selecionou alguma função
	if (form.posicoes.checked == true){
		if(!validarCheckSelecionado(form, "tipoFuncAcompTpfa")){
			alert("<%=_msg.getMensagem("relAcompanhamento.relImpresso.validacao.posicoes")%>");
			return false;
		}
	}
	return true;
}

function marcarTudo(form){
	//Se todos for selecionado, deve marcar todos os campos
	if (form.todosOsItens.checked){
		form.posicoes.checked = true;
		form.dadosGerais.checked = true;
		form.indicadores.checked = true;
		form.financeiro.checked = true;
		form.localizacao.checked = true;
		form.eventos.checked = true;
		form.pontosCriticos.checked = true;
	}
	else{
		form.posicoes.checked = false;
		form.dadosGerais.checked = false;
		form.indicadores.checked = false;
		form.financeiro.checked = false;
		form.localizacao.checked = false;
		form.eventos.checked = false;
		form.pontosCriticos.checked = false;
	}
	marcarPosicoes(form);
	return true;
}

function marcarPosicoes(form){
	//se posições for selecionado, deve ser marcado seus subordinados
	if (form.posicoes.checked == true){
		var numChecks = 0;
		var nomeCheck = "tipoFuncAcompTpfa";
	    numChecks = verificaChecks(form, nomeCheck);
		if(numChecks > 1) {
			for(i = 0; i < eval('form.' + nomeCheck + '.length'); i++) {
				eval('form.' + nomeCheck + '[i]').checked = true;
			}
		}
		
		if(numChecks == 1) {
			eval('form.' + nomeCheck).checked = true;	
		}
	}
	else{
		var numChecks = 0;
		var nomeCheck = "tipoFuncAcompTpfa";
	    numChecks = verificaChecks(form, nomeCheck);
		if(numChecks > 1) {
			for(i = 0; i < eval('form.' + nomeCheck + '.length'); i++) {
				eval('form.' + nomeCheck + '[i]').checked = false;
			}
		}
		
		if(numChecks == 1) {
			eval('form.' + nomeCheck).checked = false;	
		}
	}
	
	return true;
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

</script>
</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 
	
<form  name="form" method="post" >	
<%
	boolean ehRegistro = false;
	boolean ehVisualizacao = false;
	
	
	
	String tela = Pagina.getParamStr(request, "tela");
	if(tela != null && !tela.equals("")){
		if(tela.equals("R"))
			ehRegistro = true;
		if(tela.equals("V"))
			ehVisualizacao = true;
	}


	if(ehRegistro){	  	
	
		ari = acompReferenciaItem;
		
		AcompReferenciaAref arefSelecionado = ari.getAcompReferenciaAref();
			
		%>
		<input type="hidden" name="tela" value="R">
		<%@ include  file="../../form_registro.jsp" %>
		<%	
			
	} else if(ehVisualizacao){
	
		%>
		<input type="hidden" name="tela" value="V">
		<%@ include  file="../../form_visualizar.jsp" %>
		<%  
	} 
	//Caso venha da tela geral de monitoramento
	else { 
	
		// essa variavel é utilizada no include do cabecalho das paginas
		String telaAnterior = _pathEcar + "/acompanhamento/posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo + "&periodo=" + periodo;//Pagina.getParamStr(request, "periodo");
		%>
		<%@ include file="/titulo_tela.jsp"%><br>
		
		<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
			chamarPagina="posicaoGeral.jsp"
			caminho="<%=_pathEcar%>"
			gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
			request = "<%=request%>"
			/>
			
		
		
		<util:barraLinksRegAcomp
			acompReferencia = "<%=acompReferenciaAref%>"
			selectedFuncao="RELATORIO"
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			abaSuperior="<%= Dominios.SIM %>"
			listaGeral="<%=telaAnterior %>"
			caminho="<%=_pathEcar%>"
			
		/>
		
		
		<br><br><br> 
		
<%	}
	 %>	

	



<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codAri" value="<%=strCodAri%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="codigosAri" value="<%=strCodigosAri%>">
<input type="hidden" name="codigosItemSelecionadosTela" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">

<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="espacador" colspan="3"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	<tr class="linha_titulo">
		<td colspan="2"></td>
		<td align="right">
			<input type="button" value="Gerar Relatório" class="botao" onclick="aoClicarGerar(form);"> 
			<!-- input type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);"-->
		</td>
	</tr>
	<tr><td class="espacador" colspan="3"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td colspan="3" align="center">Selecione abaixo o conteúdo da impressão</td>
	</tr>
	<tr><td class="espacador" colspan="3"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr class="linha_subtitulo2">		
		<td width="10%" align="right"><input type="checkbox" class="form_check_radio" name="todosOsItens" value="S" checked onclick="javascript:marcarTudo(form)"> Marcar todas as opções</td>
		<td width="90%">&nbsp;</td>
	</tr>
	<tr class="linha_subtitulo2">		
		<td align="left"></td>
		<td>
			<input type="checkbox" class="form_check_radio" name="posicoes" value="S" checked onclick="javascript:marcarPosicoes(form); form.todosOsItens.checked=false;"> Posições
		</td>
	</tr>
<%
	TipoFuncAcompDao funcaoDao = new TipoFuncAcompDao(request);
	List lFuncao = funcaoDao.getTipoFuncAcompEmitePosicao();
	Iterator itFuncao = lFuncao.iterator();
	
	TipoAcompTipofuncacompSisatributoTaTpfaSatbDao tipoAcompTipofuncacompSisatributoTaTpfaSatbDao = new TipoAcompTipofuncacompSisatributoTaTpfaSatbDao();
	List listaTipoFuncaoAcompanhamentoTpfa = tipoAcompTipofuncacompSisatributoTaTpfaSatbDao.pesquisarPermissaoVisualizarParecerTipoFuncaoAcompTpfa(acompReferenciaAref.getTipoAcompanhamentoTa(), seguranca.getGruposAcesso());
	
	/* mostra as funções de acompanhamento */
	itFuncao = listaTipoFuncaoAcompanhamentoTpfa.iterator();
	while (itFuncao.hasNext()) {
		TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFuncao.next();

		if("S".equalsIgnoreCase(funcao.getIndEmitePosicaoTpfa())){
%>
		<tr class="linha_subtitulo2">	
			<td width="50%" align="left"></td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="checkbox" class="form_check_radio" name="tipoFuncAcompTpfa" value="<%=funcao.getCodTpfa()%>" checked onclick="javascript:form.posicoes.checked=false;form.todosOsItens.checked=false;">
				<%=funcao.getLabelTpfa()%>
			</td>
		</tr>
<%
		}
	}
%>

	<!-- 
	<tr class="linha_subtitulo2">		
		<td></td>
		<td><input type="checkbox" class="form_check_radio" name="localizacao" value="S" checked onclick="javascript: form.todosOsItens.checked=false;"> Localização</td>
	</tr>
	 -->
	<tr class="linha_subtitulo2">		
		<td></td>
		<td><input type="checkbox" class="form_check_radio" name="indicadores" value="S" checked onclick="javascript: form.todosOsItens.checked=false; form.projecao.checked=this.checked"> Indicadores de Resultado</td>
	</tr>
	<tr class="linha_subtitulo2">		
		<td></td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="checkbox" class="form_check_radio" name="projecao" value="S" checked onclick="javascript: form.todosOsItens.checked=false; if(this.checked==true) form.indicadores.checked=true;">Com Projeção</td>
	</tr>
	<tr class="linha_subtitulo2">		
		<td></td>
		<td><input type="checkbox" class="form_check_radio" name="financeiro" value="S" checked onclick="javascript: form.todosOsItens.checked=false;"> Quadro de Recursos Or&ccedil;ament&aacute;rio-Financeiros</td>
	</tr>
	<!--
	<tr class="linha_subtitulo2">		
		<td></td>
		<td><input type="checkbox" class="form_check_radio" name="eventos" value="S" checked onclick="javascript: form.todosOsItens.checked=false;"> Eventos</td>
	</tr>
	 -->
	 <!-- 
	<tr class="linha_subtitulo2">		
		<td></td>
		<td><input type="checkbox" class="form_check_radio" name="pontosCriticos" value="S" checked onclick="javascript: form.todosOsItens.checked=false;"> Pontos Críticos</td>
	</tr>
	 -->
	<tr class="linha_subtitulo2">		
		<td></td>
		<td>
			<input type="checkbox" class="form_check_radio" name="dadosGerais" value="S" checked onclick="javascript: form.todosOsItens.checked=false;"> Cadastro:
			&nbsp
			<input type="radio" class="form_check_radio" name="indTipoDadosGerais" value="L" checked="true">Relação 
			<input type="radio" class="form_check_radio" name="indTipoDadosGerais" value="R">Resumo
			<input type="radio" class="form_check_radio" name="indTipoDadosGerais" value="C">Completo
		</td>
	</tr>
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>
<input type="hidden" name="titulo_sistema" value="<%=configura.getTituloSistema()%>">
<%
if(codAriFilhos != null) {
	for (int i = 0; i < codAriFilhos.length; i++) {
      	%>
	      	<input type="hidden" name="codAriFilhos" value="<%=codAriFilhos[i]%>">
      	<%
	}
}
%>
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