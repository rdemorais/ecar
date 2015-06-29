<%@ page language="java"%>
<jsp:directive.page import="ecar.pojo.AtributoDemandaAtbdem"/>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoDemanda"/>
<jsp:directive.page import="ecar.pojo.DemAtributoDema"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<jsp:directive.page import="ecar.dao.DemandasGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.DemandasGrpAcesso"/>
<jsp:directive.page import="ecar.dao.VisaoGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.VisaoDemandasGrpAcesso"/>
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.UsuarioDao"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.ExportacaoRelatorioDemandasDao"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Map"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="comum.util.Data" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Pagina" %>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>

<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.SitDemandaDao" %>



<%@page import="ecar.pojo.VisaoDemandasVisDem"%>

<%@page import="ecar.dao.VisaoDao"%><html lang="pt-br">
<head>

<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

//Métodos de frmPesquisa.js sobrescrevidos para haver novas consultas nas paginações
//mudança realizada para não ocorrer erro de lazyInitializationException
function aoClicarPrimeiro(form){
	if (form.hidNumPagina.value == 0){
		alert("Você já está na primeira página");
		return(false);
	}else{
		form.refazPesquisa.value = 'refaz';
		form.hidNumPagina.value = 0;
	}
	form.submit();
}

function aoClicarUltimo(form){
	if (form.hidNumPagina.value == (form.hidTotPagina.value - 1)){
		alert("Você já está na última página");
		return(false);
	}else{
		form.refazPesquisa.value = 'refaz';
		form.hidNumPagina.value = form.hidTotPagina.value - 1;
	}
	form.submit();
}

function aoClicarAnterior(form){
	if (form.hidNumPagina.value == 0){
		alert("Você já está na primeira página");		
		return(false);
	}else{
		form.refazPesquisa.value = 'refaz';
		form.hidNumPagina.value = form.hidNumPagina.value - 1;
	}
	form.submit();
}
function aoClicarProximo(form){
	if (form.hidNumPagina.value == (form.hidTotPagina.value - 1)){
		alert("Você já está na última página");
		return(false);
	}else{
		form.refazPesquisa.value = 'refaz';
		form.hidNumPagina.value = parseInt(form.hidNumPagina.value) + 1;
	}
	form.submit();
}
function aoClicarAdicionar(){
	form.action = "frm_inc.jsp";
	form.submit();
}
function aoClicarAlteracao(codigo){
	form.codRegd.value = codigo;
	form.action = "frm_alt.jsp";
	form.submit();
}
function aoClicarConsultar(codigo){
	form.codRegd.value = codigo;
	form.action = "frm_cons.jsp";
	form.submit();
}
function aoClicarClassificarDemanda(codigo){
	form.codRegd.value = codigo;
	form.action = "frm_classifica.jsp";
	form.submit();
}
function aoClicarClassificarOrdenar(campo){
	if (campo != form.clCampo.value) {
		form.clCampo.value = campo;
		form.clOrdem.value = "asc";
	} else {
		if (form.clOrdem.value == "asc") {
			form.clOrdem.value = "desc";
		} else {
			form.clOrdem.value = "asc";
		}
	}
	form.refazOrdenacao.value = "refaz";
	form.action = "lista.jsp";
	form.submit();
}
function aoClicarExcluir(){
	if(validarExclusao()){
		if(existeItemAssociado()){
			if (!confirm("<%=_msg.getMensagem("registroDemanda.exclusao.erroItemAssociadoDemanda")%>")){
				return(false);
			}
		} else if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
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
				if(form.excluir[i].checked){
					algumMarcado = true;	
				}
				
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

function existeItemAssociado(){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verCheckExc(form, "excluir");
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < form.excluir.length; i++){
				if(form.excluir[i].checked){
					algumMarcado = true;
					if(form.existeItem[i].value == 'S'){
						return true;			
					}	
				}
			}	
		} else {
			algumMarcado = form.excluir.checked;
			if(algumMarcado && form.existeItem.value == 'S'){
				return true;
			}
		}
	}
	return false;
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

function filtrar() {
	form.hidAcao.value = "inicio_filtro";
	form.action = "ctrl.jsp";
	form.submit();
}

function gerarArquivo() {
	form.hidAcao.value = "exportar";
	form.hidGerarArquivo.value = "S";
	form.action = "ctrl.jsp";
	form.submit();
}

function aoClicarClassificarDemandas(codAtributoDemanda){
	form.colunaClicadaClassificarDemandas.value = codAtributoDemanda;
	form.action = "lista.jsp";
	form.submit();
}

</script>

</head>

<%
	String caminhoArquivoGerado = "";
	String msg = null;

	VisaoDao visaoDao = new VisaoDao(request);
	VisaoDemandasVisDem visaoSessao = null;
			
	// faz a consulta das visoes grupos de acesso para saber se o usuario possui permissão para visualizar as demandas de uma visao definida em "visaoParameter"
	int visaoParameter = 0;
	String visaoDescricao = null;
	if (request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)==null ||( Pagina.getParamInt(request, "visao") > 0 && 
				((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue()
					!= Pagina.getParamInt(request, "visao"))) {	
		visaoParameter = Pagina.getParamInt(request, "visao");
		if(visaoParameter>0) {
			visaoSessao = (VisaoDemandasVisDem)visaoDao.buscar(VisaoDemandasVisDem.class, new Long(visaoParameter));
			visaoSessao.getVisaoSituacaoDemandas().size();
			request.getSession().setAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA, visaoSessao);
			visaoDescricao = visaoSessao.getDescricaoVisao();
		}
	} else {
		visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue();
		visaoSessao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
		visaoDescricao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getDescricaoVisao();
	}

	List listaRegistros = (List) session.getAttribute("listaRegistrosRelatorio");
	String hashNomeArquivo = null;
	UsuarioUsu usuarioImagem = null;  
	
	
	%>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>


<% 
	if (request.getParameter("messageKey") != null) {
		String key = request.getParameter("messageKey");
		
		Mensagem.alert(_jspx_page_context, _msg.getMensagem(key));
	}
%>



<div id="conteudo">
	
	<form name="form" method="post">
	
	<input type="hidden" name="hidGerarArquivo" id="hidGerarArquivo" value="">
	<input type="hidden" name="hidAcao" value="">	
	<input type="hidden" name="codRegd" value="">
	<input type="hidden" name="origem" value="lista.jsp">
	<input type="hidden" name="colunaClicadaClassificarDemandas" value="">	
	<input type="hidden" name="visao" value="">	
	
	<%
	
	String clCampo = "";
	String clOrdem = "";
	boolean classifica = false;
	
	if (Pagina.getParam(request, "clCampo") != null) {
		clCampo = Pagina.getParam(request, "clCampo");
	} else {
		clCampo = "codRegd";
	}
	
	if (Pagina.getParam(request, "clOrdem") != null) {
		clOrdem = Pagina.getParam(request, "clOrdem");
	} else {
		clOrdem = "asc";
	}
	
	//Variável setada para caso de uso ClassificaDemanda
	if ("classifica".equals(session.getAttribute("classifica"))) {
		classifica = true;
	}
	
	//Campos de ordenação da listagem%>
	<input type="hidden" name="clCampo" value="<%=clCampo%>">
	<input type="hidden" name="clOrdem" value="<%=clOrdem%>">
	<input type="hidden" name="refazPesquisa" value="">
	<input type="hidden" name="refazOrdenacao" value="">
	
<%
	try{
	
	/* lista de colunas de uma estrutura */
		List lColunas = new ArrayList();// inicialmente vazia
	
	/* uma coluna de uma estrutura */
		ObjetoDemanda coluna;
	/* Iterador de colunas  da estrutura */
		Iterator itColunas;
	/* Número de atributos demandas para formar o número de colunas */
		int numColunas = 0;
		
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);

		if(visaoParameter>0) { 
			lColunas = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaListaDemandas(new Long(visaoParameter));
			numColunas = lColunas.size();
		}
%>
	
	
	<%@ include file="/titulo_tela_visao.jsp"%>
	<div id="subconteudo">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
    	
	<tr class="linha_titulo" align="right">
			<td colspan="<%=numColunas+1%>">
				<%if (!classifica) {%>
				
					<%if (visaoSessao!=null && visaoSessao.getEhPermitidoIncluirDemanda()!=null && visaoSessao.getEhPermitidoIncluirDemanda().equals("S")) {%>
						<input type="button" value="Adicionar Demanda" class="botao" onclick="aoClicarAdicionar();">
					<%}%>
					<%if (visaoSessao!=null && visaoSessao.getEhPermitidoIncluirDemanda()!=null && visaoSessao.getEhPermitidoExcluirDemanda().equals("S")) {%>
						<input type="button" value="Excluir Demanda" class="botao" onclick="aoClicarExcluir(document.forms[0], 'excluir');">
					<%}%>
					&nbsp;&nbsp;&nbsp;
				<%}%>
				<%if (visaoSessao!=null) {%>
					<a href="#" onclick="filtrar()">Filtrar</a>&nbsp;&nbsp;&nbsp; 
					<a href="#" onclick="gerarArquivo()">Gerar Arquivo</a>
				<% }%>
			</td>
		</tr>
		<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>

<%
	
		RegDemandaDao regDemandaDao = new RegDemandaDao(request);
		
		// Configuração	
		//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		
		//Define o número máximo de registros a serem exibidos na página de acordo com o que foi setado 
		//no menu 'Geral' da Configuração do Sistema. 
		int ITENS_PAGINA = configuracao.getNuItensExibidosPaginacao();
			
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
		
		List lista = null;
		RegDemandaRegd regDemandaPesq = new RegDemandaRegd();

		boolean ehFiltro = false;
				
			
			//É filtro
			if(session.getAttribute("filtrado")!=null){
					ehFiltro = true;
			}
						
			//se for classificação
			//if (classifica && !ehFiltro){
					
					// CÓDIGO QUE RETORNA AS DEMANDAS QUE ESTÃO A CLASSIFICAR E QUE ESTÃO NOS MESMOS GRUPOS DE ACESSOS DO USUARIO LOGADO E QUE PERTECEM A UMA DETERMINADA VISAO
					//lista = 
					//regDemandaDao.getRegDemandasClassificacaoComPermissaoGrupoUsuario(
					//	regDemandaPesq, 
					//	seguranca.getGruposAcesso(), 
						//ehFiltro,
						//seguranca.getUsuario());
					//lista = 
						//regDemandaDao.getDemandasAClassificarComPermissaoNosGruposAcessosUsuario(regDemandaPesq, seguranca.getUsuario(), visaoParameter, ehFiltro);
			///}
			//else{
					// CÓDIGO QUE RETORNA AS DEMANDAS COM QUALQUER SITUAÇÃO E QUE ESTÃO NOS MESMOS GRUPOS DE ACESSOS DO USUARIO LOGADO E QUE PERTECEM A UMA DETERMINADA VISAO
					lista = regDemandaDao.getDemandasComPermissaoNosGruposAcessosUsuario(seguranca.getUsuario(), visaoSessao, ehFiltro);
			//}

		
		if ("refaz".equals(Pagina.getParamStr(request, "refazOrdenacao"))) {
			// refaz a ordenação
			regDemandaDao.classificarOrdenacao(clCampo, clOrdem, lista);
		}
		
						
		Map colunaClicada = new HashMap();
		Long codColunaClicada = new Long("0");
		String ordenacaoColunaClicada = "asc";
		 
		//Se foi clicado em ordenar demandas, seta a coluna e a ordenação na sessão.
		//Só entra neste if se vier do método aoClicarClassificarDemandas(*) 
		if(!Pagina.getParamStr(request, "colunaClicadaClassificarDemandas").equals("")){
			codColunaClicada = new Long(Pagina.getParamStr(request, "colunaClicadaClassificarDemandas"));
			
			if(session.getAttribute("colunaClicada") != null){
				Map colunaClicadaTemp = (Map) session.getAttribute("colunaClicada"); 
				if(codColunaClicada.equals((Long) colunaClicadaTemp.get("codColunaClicada")) && ((String) colunaClicadaTemp.get("ordenacaoColunaClicada")).equals("asc")){
					ordenacaoColunaClicada = "desc";
				}								
			}
			
			colunaClicada.put("codColunaClicada", codColunaClicada);
			colunaClicada.put("ordenacaoColunaClicada", ordenacaoColunaClicada);
			
			session.setAttribute("colunaClicada", colunaClicada);
		}
		
		//Pega a coluna e a ordenação da sessão e ordena as demandas de acordo com esses parâmetros 
		if(session.getAttribute("colunaClicada") != null){
			colunaClicada = (Map) session.getAttribute("colunaClicada"); 
			codColunaClicada = (Long) colunaClicada.get("codColunaClicada");
			ordenacaoColunaClicada = (String) colunaClicada.get("ordenacaoColunaClicada");
			
			regDemandaDao.ordenacaoDemandas(codColunaClicada, ordenacaoColunaClicada, lista);
		}
				
		//guarda na sessão a lista que será utilizada para gerar o arquivo .txt
		request.getSession().setAttribute("listaRegistrosRelatorio", lista);
		
		int tamLista = (lista != null ? lista.size() : 0);
		int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
		
		hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
		
		if (limite > 0) {
				
%>
			<tr>
				<td align="center" colspan="<%=numColunas+1%>">
					<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"
					numItensExibidosPaginacao="<%=ITENS_PAGINA%>" totalItens="<%=tamLista%>" />
					<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">	
					<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">	
				</td>
			<tr>
			<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo" align="right">
		<%
			/* desenha as colunas de uma estrutura */
			itColunas = lColunas.iterator();
			int numColuna = 1;
			String nomeCbCtrl = "excluirAll";
			String strCheckBox;
			if (classifica){			
				strCheckBox = "<td nowrap=\"nowrap\" width=\"1%\" align=\"left\"></td>";
			}
			else{
				strCheckBox = "<td nowrap=\"nowrap\" width=\"1%\" align=\"left\"><input type=\"checkbox\" name=\"" + nomeCbCtrl + "\" onclick=\"javascript:selectAll(document.form, '" + nomeCbCtrl + "');\"></td>";
			}			
			
			String strColunaVazia = "<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
			
			//strCheckBox
			//strColunaVazia
			
			while (itColunas.hasNext()){
				coluna = (ObjetoDemanda) itColunas.next();
								
		%>
			<%=strCheckBox%>			
			<td width="<%=coluna.iGetLargura(new Long(visaoParameter))%>%" align="left">
			
		<%
			//Se for a coluna da ordenação divide a célula em duas ára adicionar a setinha SERPRO SM 149740
			if (codColunaClicada!= null && codColunaClicada.equals(coluna.iGetCodAtbdem() ) ) { 
		%>
			<table > 
				<td align="left"  >
		<% } %>
			
			<!--  a href="#" onclick="this.blur(); return sortTable('corpo1',  <%=numColuna%>, false);"-->											
			<a href="#" onclick="aoClicarClassificarDemandas('<%= coluna.iGetCodAtbdem()%>');">
			<%numColuna++;%>
			<%=coluna.iGetLabel()%>
			</a>
			
				<%
			if (codColunaClicada!= null && codColunaClicada.equals(coluna.iGetCodAtbdem() ) ) { 
				String imgOrd = "../../images/icon_bullet_seta_up.png";
				if (ordenacaoColunaClicada.equals("desc")){
					imgOrd = "../../images/icon_bullet_seta_down.png";
				}  
			%>
				</td>
					<td> <img src="<%=imgOrd%>" border="0" onclick="aoClicarClassificarDemandas('<%= coluna.iGetCodAtbdem()%>');"> </td>
				</table>
			<% } %>
			
			</td>
		<%
				strCheckBox = "";
			}
		%>			
 
			<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
<%
		}
		
		
		int mes = 0, mes_dt = 0;
		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		
		int cont = 0;
		String cor = new String();
		
%>
		<tbody id="corpo1">
<%		
		// para cada registro de demanda
		for (int i = hidNumPagina * ITENS_PAGINA; i < limite; i++) {
			if (cont % 2 == 0){
				cor = "cor_nao";
			} else {
				cor = "cor_sim"; 
			}
			
			RegDemandaRegd regDemanda = (RegDemandaRegd) lista.get(i);
			
			String situacao = "";
			String dataSolicitacao = "";
	
%>
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" >
				<td nowrap="nowrap" width="1%" valign="top">
					<%if (!classifica) {//if (regDemandaDao.validaUsuarioAltExc(regDemanda, request) && !classifica) {%>
						<%
						if (visaoSessao!=null && visaoSessao.getEhPermitidoExcluirDemanda().equals("S")) {
						%>
							<input type="checkbox" name="excluir" value="<%=regDemanda.getCodRegd()%>">					
						<%
						}
						
						if (visaoSessao!=null && visaoSessao.getEhPermitidoAlterarDemanda().equals("S")) { 
						
						%>
							<a href="javascript:aoClicarAlteracao(<%=regDemanda.getCodRegd()%>);">
							<img src="../../images/icon_alterar.png" border="0"></a>
						<%
						}
						%>
						<input type="hidden" name="existeItem" value="<%=regDemanda.getItemRegdemandaIregds().size() == 0? "N":"S"%>">
					<%}%>
					<%if (classifica) {%>
						<a href="javascript:aoClicarClassificarDemanda(<%=regDemanda.getCodRegd()%>);">
						<img src="../../images/icon_classificar.png" border="0"></a>
					<%}%>
				</td>
				
				<%
					itColunas = lColunas.iterator();
				
					while (itColunas.hasNext()){
					
					coluna = (ObjetoDemanda)itColunas.next();
				%>
				
				<td valign="top" align="left" width="<%=coluna.iGetLargura(new Long(visaoParameter))%>%">
					<%
					String informacaoAtbdem = "";
					
					if (!classifica) {						
						if (coluna.iGetGrupoAtributosLivres() != null)  {
							Iterator itRegDem =  regDemanda.getDemAtributoDemas().iterator();
							while (itRegDem.hasNext()) {
								DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem.next();
								
								// se o grupo de "demAtributoDema" é o mesmo do atributo 
								if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
									if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
									 
										informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao() + configuracao.getSeparadorCampoMultivalor();
									
									} 
									else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										
										informacaoAtbdem = informacaoAtbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() + configuracao.getSeparadorCampoMultivalor();
									} //se for do tipo imagem não faz nada, deixa em branco.
								} 
							}
							if (informacaoAtbdem.length() > 0){
								informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - configuracao.getSeparadorCampoMultivalor().length()); 
							}
//							out.println(informacaoAtbdem);
					%>
							<a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
							<%=informacaoAtbdem%></a>
					<%		
						} else {
//							out.println(coluna.iGetValor(regDemanda));

							if (coluna.iGetNome().equals("localDemandaLdems")){
					%>
							<a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
							<%=coluna.iGetValoresCodFk(regDemanda)%></a>
					<%
							}							
							else{
							
								String valor;
								
								if (coluna.iGetValor(regDemanda).equalsIgnoreCase("S")){
									
									valor = "Sim";
								}
								else if (coluna.iGetValor(regDemanda).equalsIgnoreCase("N")){
									valor = "Não";
								}
								else{
									valor = coluna.iGetValor(regDemanda);
								}
							
					 %>
							<a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
							<%=valor%></a>
					<%
							}
						}
									
					} else {
					
						if (coluna.iGetGrupoAtributosLivres() != null)  {
							Iterator itRegDem =  regDemanda.getDemAtributoDemas().iterator();
							while (itRegDem.hasNext()) {
								DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem.next();
								
								if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
									if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
									 
										informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao() + configuracao.getSeparadorCampoMultivalor();
									
									} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										
										informacaoAtbdem = informacaoAtbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() + configuracao.getSeparadorCampoMultivalor();
									} //se for do tipo imagem não faz nada, deixa em branco.
								} 
							}
							if (informacaoAtbdem.length() > 0){
								informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - configuracao.getSeparadorCampoMultivalor().length()); 
							}
							
							out.println(informacaoAtbdem);
					
						} else {
							out.println(coluna.iGetValor(regDemanda));
						}						
					}
					%>
				</td>
				
				<%
					}
				%>
			</tr>
<%
		}
%>
		</tbody>
<%		
		if (limite > 0) { 
%>
			<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
			<tr>
				<td align="center" colspan="<%=numColunas+1%>">
				<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"
				numItensExibidosPaginacao="<%=ITENS_PAGINA%>" totalItens="<%=tamLista%>"/>
				</td>			
			<tr>
			<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
<%
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="6">
				<% if (session.getAttribute("filtrado")!=null) {%>
					<b><%=_msg.getMensagem("registroDemanda.filtro.nenhumRegistro")%><b>
				<%} else { %>
					<b>Nenhum registro foi encontrado</b>
				<%}%>
				</td>
			</tr>
			<tr><td class="espacador" colspan="<%=numColunas+1%>"><img src="../../images/pixel.gif"></td></tr>
<%
		}		
		
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	}
%>
	
	</table>
	</div>
	<%@ include file="../../include/estadoMenu.jsp"%>
	
	</form>
	
</div>

</body>

<%@ include file="../../include/mensagemAlert.jsp"%>

</html>