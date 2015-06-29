
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/><%@ include file="/../../login/validaAcesso.jsp"%>
<%@ include file="/../../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.dao.AbaDao"%>

<%@ page import="ecar.util.Dominios"%>

<%@ page import="comum.util.Data"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	ItemEstruturaIett itemEstrutura = null;
	
	//pega os parametros do request
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);

	AcompReferenciaAref	arefSelecionado = null;
	
	
	// verifica a referencia
	if(!"".equals(Pagina.getParamStr(request, "referencia"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
//		strCodAri = Pagina.getParamStr(request, "referencia");
		strCodAri = Pagina.getParamStr(request, "codAri");
	}
	

	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	AcompReferenciaItemAri ari = null;
	if(!"".equals(strCodAri)) {
		
		// Busca o item de estrutura pelo codAri passado como parametro
		ari = (AcompReferenciaItemAri) ariDao.buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = ari.getItemEstruturaIett();
		
		arefSelecionado = ari.getAcompReferenciaAref();
		
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
	//Em visualizar -> validaPermissao.permissaoConsultaParecerIETT 
	ValidaPermissao validaPermissao = new ValidaPermissao();
	//if ( !validaPermissao.permissaoConsultaIETT( itemEstrutura.getCodIett() , seguranca ) )
	//	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	
	//Pega a aba
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("EVENTOS");
	Aba aba_teste = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));		

	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	//estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(Pagina.getParamStr(request,"codAba")));
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(aba_teste.getCodAba().toString()));
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);

%>
<html lang="pt-br">
<head>
<%@ include file="/../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">

function aoClicarConsultar(form, codigo){
	form.codIetta.value = codigo;
	form.hidAcao.value = 'alterar';
	form.action = "frm_con.jsp";
	form.submit();
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
<%--function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}--%>
 
</script>
	</head>
	<body>
		<%@ include file="/../../cabecalho.jsp"%>
		<%@ include file="/../../divmenu.jsp"%>
		
		<div id="conteudo">
		
		<%
		String periodo = Pagina.getParamStr(request, "periodo");
		String funcaoSelecionada = "EVENTOS";
		%>
		<%@ include  file="../form_registro.jsp" %>		

		
<form name="form" method="post" action="">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="hidAcao"       value="">
	<input type="hidden" name="codIett"       value="<%=ari.getItemEstruturaIett().getCodIett()%>">
	<input type="hidden" name="codIetta"      value="">
	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="nomeCheckBox"  value="">
	<input type="hidden" name="codAba"        value="<%=aba_teste.getCodAba().toString()%>">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<div id="subconteudo">

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="espacador">
							<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
						</td>
					</tr>
<%if(permissaoAlterar.booleanValue() && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){%>
					<tr class="linha_titulo" align="right">
						<td colspan="3">
							<input type="button" name="btnAdicionar" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(form)">
							<input type="button" name="btnExcluir"   value="Excluir"   class="botao" onclick="javascript:aoClicarExcluir(form,'excluir')">
						</td>
					</tr>
<%} %>
				</table>
				<!-- ############### Listagem  ################### -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td class="espacador" colspan="5">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<tr class="linha_subtitulo">
							<td width="5%">
								<input type="checkBox" class="form_check_radio" name="todos"	onclick="javascript:selectAll(document.form)">
							</td>
							<td>
								<a href="#"	onclick="this.blur(); return sortTable('corpo',  1, true);"	title="Data">Data</a>
							</td>
							<td>
								<a href="#"	onclick="this.blur(); return sortTable('corpo',  2, true);"	title="Descricao">Descri&ccedil;&atilde;o</a>
							</td>
							<td>
								<a href="#"	onclick="this.blur(); return sortTable('corpo',  3, true);"	title="Última atualização">Última atualização</a>
							</td>
							<td>
								<a href="#"	onclick="this.blur(); return sortTable('corpo',  4, true);"	title="Data de inclusão">Data de inclusão</a>
							</td>
						</tr>
						<tr>
							<td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
					</thead>
					<tbody id="corpo">
<%
		/* Percorre a lista de Beneficiários de ItemEstrutura e imprime na tela */
		if(ari != null && ari.getItemEstruturaIett().getItemEstrutAcaoIettas() != null ){
			List acoes = itemEstruturaDao.ordenaSet(ari.getItemEstruturaIett().getItemEstrutAcaoIettas(), "this.dataIetta", "asc");
			Iterator itAcao = acoes.iterator();
			int cont = 0;
			String cor = new String(); 
			while(itAcao.hasNext()){
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				
				ItemEstrutAcaoIetta ieAcao = (ItemEstrutAcaoIetta) itAcao.next();
				// ignorar registro inativo
				if(Dominios.NAO.equals(ieAcao.getIndAtivoIetta())) {
					continue;
				}
%>
						<tr class="<%=cor%>"
							onmouseover="javascript:destacaLinha(this,'over','')"
							onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')"
							onClick="javascript: destacaLinha(this,'click','<%=cor%>')"
							class="linha_subtitulo2">
							<td valign="top"> 
								<input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieAcao.getCodIetta()%>">
							</td>
							<td valign="top">
								<a href="javascript:aoClicarConsultar(document.form,<%=ieAcao.getCodIetta()%>)"><%=Pagina.trocaNullData(ieAcao.getDataIetta())%></a>
							</td>
							<!-- td><div id="pre"><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></div></td -->
							<td><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></td>
							<td valign="top">
								<% if (ieAcao.getUsuarioUsu() != null) %><%=ieAcao.getUsuarioUsu().getNomeUsu()%></td>
							<td valign="top">
								<% if (ieAcao.getUsuarioUsu() != null) %><%=Pagina.trocaNullData(ieAcao.getDataInclusaoIetta())%></td>
						</tr>
						<%
				cont++;
			}
			
			if (cont == 0){
%>
						<tr>
							<td class="subtitulo" colspan="5"> 
								<b>Nenhum dado cadastrado</b> 
							</td>
						</tr>
<%
			 }
		}
%>
						<!-- ############### Listagem  ################### -->
					</tbody>
				</table>
			</div>
		</form>
		<br>
	</div>
</body>
</html>
<% 
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/../include/estadoMenu.jsp"%>
<%@ include file="/../include/mensagemAlert.jsp"%>
