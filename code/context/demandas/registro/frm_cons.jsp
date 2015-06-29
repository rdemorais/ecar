
<jsp:directive.page import="java.util.Date"/>

<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="ecar.dao.ApontamentoAnexoDao"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.AcompRelatorioArel" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.taglib.util.BarraLinkDemandaVisaoTag" %>

<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.dao.RegDemandaAnexoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 

<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="comum.util.Data" %>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 


<%@page import="ecar.pojo.RegDemandaAnexoRegdan"%>
<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="comum.util.FileUpload"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function aoClicarCancelar(form){
	form.reset();
}
function onLoad(form) {
	aoClicarCancelar(form);
}
function aoClicarApontamento(form) {
	form.action = "frm_cons.jsp";
	form.submit();
}
function aoClicarAlterarDemanda(form, codigo) {
	form.hidAcao.value = "";
	form.codRegd.value = codigo;
	form.action = "frm_alt.jsp";
	form.submit();
}
function aoClicarIncluirAnexo(form, codRegd) {
	form.hidAcao.value = "incluir";
	form.codRegd.value = codRegd;
	form.action = "../anexo/frm_inc.jsp";
	form.submit();
}
function aoClicarIncluirApontamento(form, codRegd) {
	form.hidAcao.value = "incluir";
	form.codRegd.value = codRegd;
	form.action = "../apontamento/frm_inc.jsp";
	form.submit();
}
function aoClicarDetalharApontamento(codRegda, codRegd) {
	form.hidAcao.value = "detalhar";
	document.getElementById('codRegda').value = codRegda;
	form.codRegd.value = codRegd;
	form.action = "../apontamento/frm_alt.jsp";
	form.submit();
}
function aoClicarAlteracaoApontamento(codRegd, codRegda){
	form.codRegd.value = codRegd;
	form.codRegda.value = codRegda;
	form.action = "../apontamento/frm_alt.jsp";
	form.submit();
}
function aoClicarDetalharAnexo(codRegdan,codRegd) {
	form.hidAcao.value = "detalhar";
	form.codRegdan.value = codRegdan;
	form.codRegd.value = codRegd;
	form.action = "../anexo/detalhaAnexo.jsp";
	form.submit();
}
function aoClicarClassificarOrdenar(campo,codRegd, tabAtual){
	if (campo != form.aptCampo.value) {
		form.aptCampo.value = campo;
		form.aptOrdem.value = "asc";
	} else {
		if (form.aptOrdem.value == "asc") {
			form.aptOrdem.value = "desc";
		} else {
			form.aptOrdem.value = "asc";
		}
	}
	form.aptRefazPesquisa.value = "refaz";
	form.action = "frm_cons.jsp?tabAtual=" + tabAtual;
	form.codRegd.value = codRegd;
	form.submit();
}
function aoClicarExcluirApontamento(codRegd){
	if(validarExclusao()){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.codRegd.value = codRegd;
		form.hidAcao.value = "excluir";
		form.action = "../apontamento/ctrl.jsp";
		form.submit();	
	}
}
function aoClicarExcluirAnexo(codRegd){
	if(validarExclusao()){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.codRegd.value = codRegd;
		form.hidAcao.value = "excluir";
		form.action = "../anexo/ctrlAnexo.jsp";
		form.submit();	
	}
}
function validarExclusao(){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verCheckExc(form, "excluir");
	
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < form.excluir.length; i++)
				if(form.excluir[i].checked)
					algumMarcado = true;	
		} else {
			algumMarcado = form.excluir.checked;
		}
	}
	
	if(!algumMarcado){
		alert("Pelo menos um registro deve ser selecionado.");
		return false;
	} else {
		return true;
	}
}
function verCheckExc(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}
function selectAll(form, nomeCheckBoxCtrl, nomeCheckBoxDep){

    if (typeof nomeCheckBoxCtrl == "undefined") nomeCheckBoxCtrl = "todos";
    if (typeof nomeCheckBoxDep == "undefined") nomeCheckBoxDep = "excluir";

	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, nomeCheckBoxDep);

	if(numChecks > 1) {
		for(i = 0; i < eval('form.' + nomeCheckBoxDep + '.length'); i++)
			if(eval('form.' + nomeCheckBoxDep + '[i].disabled') == false) {
				eval('form.' + nomeCheckBoxDep + '[i]').checked = eval('form.' + nomeCheckBoxCtrl).checked;	
			}
	}
	
	if(numChecks == 1) {
		if(eval('form.' + nomeCheckBoxDep + '.disabled') == false) {
			eval('form.' + nomeCheckBoxDep).checked = eval('form.' + nomeCheckBoxCtrl).checked;	
		}
	}
}
function aoClicarConsultar(form, codigo, codIett, codTa, codRegd){ 
	document.form.codAri.value = codigo;
	document.form.codTipoAcompanhamento.value = codTa;
    document.form.codIettRelacao.value = codIett;
	document.form.action = "<%=_pathEcar%>/acompanhamento/relAcompanhamento/resumo/detalharItem.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo + 
	"&codTipoAcompanhamento=" + codTa + "&codRegd=" + codRegd;
	document.form.submit();
}

function aoClicarDemandas(form, numPagina, totPaginas){ 
	document.form.hidNumPagina.value = numPagina;
	document.form.hidTotPagina.value = totPaginas;
	if (document.getElementById('telaAssociacaoDemandas').value == 'S'){
		form.action = "<%=_pathEcar%>/cadastroItens/associacaoDemandas/lista.jsp";
	} else {
		form.action = "lista.jsp";
	}
	
	document.form.submit();
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
	//Guarda a pagina que chamou a funcionalidade 
	int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
	int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
	String visaoDescricao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getDescricaoVisao();
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	String telaAssociacaoDemandas = "";
	String codAba = "";
	String codIett = "";
	String ultimoIdLinhaDetalhado = "";
	if(isFormUpload) {
		campos = FileUpload.criaListaCampos(request);
		telaAssociacaoDemandas = String.valueOf(FileUpload.verificaValorCampo(campos,"telaAssociacaoDemandas"));
		codAba = String.valueOf(FileUpload.verificaValorCampo(campos,"codAba"));
		codIett = String.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
		ultimoIdLinhaDetalhado = String.valueOf(FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado"));
	} else {
		telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
		codAba = Pagina.getParamStr(request, "codAba");
		codIett = Pagina.getParamStr(request, "codIett");
		ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
	}
%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="origem" value="frm_cons.jsp">
	<input type="hidden" name="codAri" value="">
	<input type="hidden" name="codTipoAcompanhamento" value="">
	<input type="hidden" name="codIettRelacao" value="">
	<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">
	<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="telaDetalhamentoDemanda" name="telaDetalhamentoDemanda" value="<%="S"%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
	<input type="hidden" name="codRegd" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela_visao.jsp"%>
	<div id="linkstop">
		<a href="javascript:aoClicarDemandas(form,'<%=hidNumPagina%>', '<%=hidTotPagina%>')">Voltar</a>
	</div>
	
<%
	RegDemandaRegd regDemanda;
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);
	RegDemandaAnexoDao regDemandaAnexoDao = new RegDemandaAnexoDao(request);
	CorDao corDao = new CorDao(request);
	
	List tpfaOrdenadosEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	
	if(session.getAttribute("objRegDemanda") != null){
		regDemanda = (RegDemandaRegd) session.getAttribute("objRegDemanda");
		session.removeAttribute("objRegDemanda");
	}else{
		regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	}
	
	String acao = "consulta";
	String tabAtual = Pagina.getParam(request, "tabAtual");

	if(tabAtual==null || tabAtual.length()==0) {
		tabAtual = BarraLinkDemandaVisaoTag.TAB_DADOS_GERAIS;
	}
	
%>

	<util:barraLinkDemandaVisaoTag
		tabAtual="<%=tabAtual%>"
		chamarPagina="frm_cons.jsp"
		codRegd ="<%= Pagina.getParam(request, "codRegd") %>"
		regDemanda = "<%=regDemanda %>"
	/> 

<%
 
 if (tabAtual!=null && tabAtual.equals(BarraLinkDemandaVisaoTag.TAB_DADOS_GERAIS)) {

%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<%@ include file="form_consulta.jsp"%>
	
	
<%
 
 } else if (tabAtual!=null && tabAtual.equals(BarraLinkDemandaVisaoTag.TAB_ENCAMINHAMENTO)) {

%>	
	<br>
	
	<h1>Encaminhamento</h1>
	
<%
	String aptCampo = "";
	String aptOrdem = "";
	
	if (Pagina.getParam(request, "aptCampo") != null) {
		aptCampo = Pagina.getParam(request, "aptCampo");
	} else {
		aptCampo = "dataRegda";
	}
	
	if (Pagina.getParam(request, "aptOrdem") != null) {
		aptOrdem = Pagina.getParam(request, "aptOrdem");
	} else {
		aptOrdem = "desc";
	}

	//Campos de ordenação da listagem
	%>
	<input type="hidden" name="aptCampo" value="<%=aptCampo%>">
	<input type="hidden" name="aptOrdem" value="<%=aptOrdem%>">
	<input type="hidden" name="aptRefazPesquisa" value="">

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
    	
   		<tr class="linha_titulo" align="right">
			<td colspan="6">
			<%
			VisaoDemandasVisDem visaoSessao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
			if (visaoSessao!=null && visaoSessao.getEhPermitidoIncluirApontamento()!=null && visaoSessao.getEhPermitidoIncluirApontamento().equals("S")) {
			%>
				<input type="button" value="Adicionar Encaminhamento" class="botao" onclick="aoClicarIncluirApontamento(form, <%=regDemanda.getCodRegd()%>);">
			<%
			}
			if (visaoSessao!=null && visaoSessao.getEhPermitidoIncluirApontamento()!=null && visaoSessao.getEhPermitidoExcluirApontamento().equals("S")) {
			%>	
				<input type="button" value="Excluir Encaminhamento" class="botao" onclick="aoClicarExcluirApontamento(<%=regDemanda.getCodRegd()%>);">
			<%
			}
			%>
			</td>
		</tr>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
<%
		List lista = new ArrayList();
		lista.addAll(regDemanda.getRegApontamentoRegdas());
		
		// refaz a ordenação
		regApontamentoDao.classificarOrdenacao(aptCampo, aptOrdem, lista);
//		lista = Collections.reverse(lista);
		request.getSession().setAttribute("listaApontamentos", lista);
		
		if (lista != null && lista.size() > 0) {
%>
			<tr class="linha_subtitulo">
				<td width="1%" nowrap> &nbsp;
				</td>
				<td width="1%" align="center">&nbsp;</td>
				<td width="1%" align="center">&nbsp;</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('infoRegda',<%=regDemanda.getCodRegd()%>, '<%=BarraLinkDemandaVisaoTag.TAB_ENCAMINHAMENTO%>');">
					Comentário</a>
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('dataRegda',<%=regDemanda.getCodRegd()%>, '<%=BarraLinkDemandaVisaoTag.TAB_ENCAMINHAMENTO%>');">
					Data Encaminhamento</a>
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('usuarioUsu',<%=regDemanda.getCodRegd()%>, '<%=BarraLinkDemandaVisaoTag.TAB_ENCAMINHAMENTO%>');">
					Usuário</a>
				</td>
			</tr>
			<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
<%
			Iterator it = lista.iterator();
			int contApont = 0; // Contador para verificação se é o primeiro apontamento [por Thaise]
			Date dataMaisRecente = null;
			
			while (it.hasNext()) {
				RegApontamentoRegda regApontamento = (RegApontamentoRegda) it.next();
				
				if (dataMaisRecente == null || regApontamento.getDataRegda().compareTo(dataMaisRecente) > 0){
					dataMaisRecente = regApontamento.getDataRegda();
				}
			}
			
			it = lista.iterator();
			
			while (it.hasNext()) {
				RegApontamentoRegda regApontamento = (RegApontamentoRegda) it.next();
				
				String data = "";
				
				if (regApontamento.getDataRegda() != null) {
					data = Data.parseDate(regApontamento.getDataRegda());
				}
														
%>
				<tr class="linha_subtitulo2">
					<td nowrap="nowrap" width="1%">
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=regApontamento.getCodRegda()%>" <%=regApontamento.getDataRegda().compareTo(dataMaisRecente) == 0 ?"":"disabled" %>>
					</td>
					<td>
						<a href="javaScript:aoClicarDetalharApontamento(<%=regApontamento.getCodRegda()%>, <%=regDemanda.getCodRegd()%>);">
						<img border="0" src="<%=request.getContextPath()%>/images/icon_alterar.png" alt="Alterar"></a>&nbsp;
					</td>
					
					<td> <% if (regApontamento.getAnexos()!= null && regApontamento.getAnexos().size()!=0) { %>
							<a href="javaScript:aoClicarDetalharApontamento(<%=regApontamento.getCodRegda()%>, <%=regDemanda.getCodRegd()%>);" ><img title="<%=regApontamento.getAnexos().size()%><%if(regApontamento.getAnexos().size()==1){%> arquivo anexo<%}else{%> arquivos anexos<%}%>" style="border: 0"src="<%=_pathEcar%>/images/anexo.gif"></a>					
						 <% } else { %> 
							&nbsp;
							<%}%>
					</td>	
					<td><%=regApontamento.getInfoRegda()%></td>  <!--  .replaceAll("\r\n","<br>")%></td> -->
					<td align="center"><%=data%> </td>
					<td><%=regApontamento.getUsuarioUsu().getNomeUsuSent()%></td>
				</tr>
<%
			}
%>
			<input type="hidden" name="codRegda" id="codRegda" value="">
<%
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="6">
				<b>Nenhum registro foi encontrado</b>
				</td>
			</tr>
<%
		}		
%>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
	</table>

<%
 
 } else if (tabAtual!=null && tabAtual.equals(BarraLinkDemandaVisaoTag.TAB_ITENS_RELACIONADOS)
		 && regDemanda.getItemRegdemandaIregds().size() > 0) {

%>
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
   		<tr class="linha_subtitulo">
			<td align="center">Itens</td>
		</tr>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
<%
		List listaItens = new ArrayList();

		listaItens.addAll(regDemanda.getItemRegdemandaIregds());
		
		if (listaItens.size() > 0) {
			Iterator itItem = listaItens.iterator();
			
			List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
			StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
			
			session.removeAttribute("linkCodDemanda");
			session.setAttribute("linkCodDemanda", regDemanda.getCodRegd());

			UsuarioUsu usuarioUsu = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
			ValidaPermissao validaPermissao = new ValidaPermissao();
			
			while (itItem.hasNext()) {
				ItemEstruturaIett item = (ItemEstruturaIett) itItem.next();
				
				Map map = acompReferenciaItemDao.criarMapPeriodoAri(null, item);
				
				//Verificar se tem Acompanhamento caso houver selecionar o último
				AcompReferenciaAref acomp = null;

				List arefs = acompReferenciaDao.getUltimoAcompanhamentoItem(item, Integer.valueOf(1));
				if (!arefs.isEmpty()){
					acomp = (AcompReferenciaAref) arefs.get(0);
				}
				
				AcompReferenciaItemAri ari = null;
				
				if (acomp != null) {
					ari = (AcompReferenciaItemAri) map.get(acomp);
				}
%>
				<tr class="linha_subtitulo2">
					<td>
						<p><%=item.getEstruturaEtt().getNomeEtt()%>&nbsp;-&nbsp;
							<%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt())%>						
						</p>
					</td>
				</tr>
<%
			}
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="6">
				<b>Nenhum registro foi encontrado</b>
				</td>
			</tr>
<%
		}		
%>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
	</table>
 
 <%
 } else if (tabAtual!=null && tabAtual.equals(BarraLinkDemandaVisaoTag.TAB_ANEXOS)) {
%>	

<%
	String aptCampo = "";
	String aptOrdem = "";
	
	if (Pagina.getParam(request, "aptCampo") != null) {
		aptCampo = Pagina.getParam(request, "aptCampo");
	} else {
		aptCampo = "descricao";
	}
	
	if (Pagina.getParam(request, "aptOrdem") != null) {
		aptOrdem = Pagina.getParam(request, "aptOrdem");
	} else {
		aptOrdem = "desc";
	}

	//Campos de ordenação da listagem
	%>
	<input type="hidden" name="aptCampo" value="<%=aptCampo%>">
	<input type="hidden" name="aptOrdem" value="<%=aptOrdem%>">
	<input type="hidden" name="aptRefazPesquisa" value="">


    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
    	
   		<tr class="linha_titulo" align="right">
			<td colspan="6">
				<%
				VisaoDemandasVisDem visaoSessao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
				if (visaoSessao!=null && visaoSessao.getEhPermitidoIncluirAnexoDemanda()!=null && visaoSessao.getEhPermitidoIncluirAnexoDemanda().equals("S")) {
				%>
					<input type="button" value="Adicionar Anexo" class="botao" onclick="aoClicarIncluirAnexo(form, <%=regDemanda.getCodRegd()%>);">
				<%
				}
				if (visaoSessao!=null && visaoSessao.getEhPermitidoExcluirAnexoDemanda()!=null && visaoSessao.getEhPermitidoExcluirAnexoDemanda().equals("S")) {
				%>
					<input type="button" value="Excluir Anexo" class="botao" onclick="aoClicarExcluirAnexo(<%=regDemanda.getCodRegd()%>);">
				<%
				}
				%>
			</td>
		</tr>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
<%
		List anexos = new ArrayList();
		anexos.addAll(regDemanda.getAnexos());
		
		// refaz a ordenação
		regDemandaAnexoDao.ordenar(aptCampo, aptOrdem, anexos);
		
		request.getSession().setAttribute("listaAnexos", anexos);
		
		if (anexos != null && anexos.size() > 0) {
%>
			<tr class="linha_subtitulo">
				<td width="1%" nowrap> &nbsp;
				</td>				
				<td align="center">
					Data
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('descricao', <%=regDemanda.getCodRegd()%> , '<%=BarraLinkDemandaVisaoTag.TAB_ANEXOS%>');">
					Descrição</a>
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('srcAnexo', <%=regDemanda.getCodRegd()%>, '<%=BarraLinkDemandaVisaoTag.TAB_ANEXOS%>');">
					Arquivo</a>
				</td>
			</tr>
			<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
<%
			Iterator it = anexos.iterator();
			
			while (it.hasNext()) {
			
				RegDemandaAnexoRegdan regDemandaAnexo = (RegDemandaAnexoRegdan) it.next();
				
				String dataInclusao = "";
				String descricao = "";				
				String fonte = "";
				if (regDemandaAnexo.getDataInclusao() != null) {
					dataInclusao = Data.parseDate(regDemandaAnexo.getDataInclusao());
				}
				descricao = regDemandaAnexo.getDescricao();
				fonte = regDemandaAnexo.getSrcAnexo();
														
%>
				<tr class="linha_subtitulo2">
					<td nowrap="nowrap" width="1%">
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=regDemandaAnexo.getCod()%>">
					</td>
					<td align="center"><%=dataInclusao%> </td>
					<td align="center"><a href="javaScript:aoClicarDetalharAnexo(<%=regDemandaAnexo.getCod()%>,<%=regDemanda.getCodRegd()%>);" ><%=descricao%> </a></td>
					<td align="center"><%=fonte%> </td>
				</tr>
<%
			}
%>
<input type="hidden" name="codRegdan" id="codRegdan" value="">
<%
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="6">
				<b>Nenhum registro foi encontrado</b>
				</td>
			</tr>
<%
		}		
%>
		<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
	</table>

<%
		}		
%>	

	<%@ include file="../../include/estadoMenu.jsp"%>
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