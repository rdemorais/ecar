
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaSisAtributoIettSatb"/>
<jsp:directive.page import="ecar.dao.ExportacaoRelatorioItemEstruturaDao"/><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.pojo.UsuarioUsu" %> 
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="ecar.bean.intercambioDados.TelaExportacaoBean"%>
<%@ page import="ecar.intercambioDados.exportacao.ExportarParecerMonitoramento" %>
<%@ page import="ecar.intercambioDados.montador.Montador"%>
<%@ page import="ecar.intercambioDados.ControladorIntercambioDados"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ecar.taglib.util.Input"%>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.AtributoLivre" %>
<%@ page import="ecar.taglib.util.Input"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/relacaoItensJs.js" type="text/javascript"></script>

<script language="javascript">


</script>

</head>

<body>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<div id="conteudo">

<% 		

try{

	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
	String filtroSituacoes = Pagina.getParamStr(request, "filtroSituacoes");
	

	//ExportacaoRelatorioItemEstruturaDao exporDao = new ExportacaoRelatorioItemEstruturaDao(request);
		
	//List listArquivosExportados = exporDao.gerarArquivosExportacaoMonitoramentoPorReferenciaTxt(
	//	new ecar.dao.ConfiguracaoDao(request).getConfiguracao(),mesReferencia, niveisPlanejamento,codTipoAcompanhamento, semInformacaoNivelPlanejamento,filtroSituacoes);
		
	/* Busca coleção com ciclos a serem considereados */
	Long codArefReferencia = Long.valueOf(mesReferencia);
	
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	List listNiveis = new ArrayList();
	String[] niveis = niveisPlanejamento.split(":");
	for(int n = 0; n < niveis.length; n++){
		String codNivel = niveis[n];
		
		if(!"".equals(codNivel)){
			listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
		}
	}
	
	Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
	if(semInformacaoNivelPlanejamento.equals(Dominios.SIM)) {
		isSemInformacaoNivelPlanejamento = new Boolean(true);
	}
	
	Long situacao = null;
	if(!"".equals(filtroSituacoes)){
		situacao = Long.valueOf(filtroSituacoes);
	}
	
	TelaExportacaoBean telaBean = new TelaExportacaoBean();
	Montador montador = new Montador (null,Montador.TIPO_MOVIMENTO_EXPORTACAO);
	ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();

	telaBean = ctrl.gerarArquivosMonitoramento(codArefReferencia,listNiveis, Long.valueOf(codTipoAcompanhamento), isSemInformacaoNivelPlanejamento, situacao, seguranca);
	session.setAttribute("telaBean", telaBean);
	
	ItemEstruturaIett itemPrincipal = null;
	List lEstruturas = estruturaDao.getEstruturaPrincipal();
		
	
	List itensBarra = new ArrayList();
	itensBarra.add("Modelos");
	itensBarra.add("Filtros");
	itensBarra.add("Formatos");
	
	int index = 0; //Modelos
	
	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		} 
	}

%>
			
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
	
<%
	if(request.getParameter("posicaoGeral") != null && !request.getParameter("posicaoGeral").equals("")) {
		
		String situacaoDatas = Pagina.getParamStr(request, "situacaoDatas");
		String status = Pagina.getParamStr(request, "relatorio");
		
		//Seta o ciclo (1 Ciclo, 2 Ciclo, etc..)
		String periodo = "";	
		String periodoEscolhido="";
		if(!"true".equals(status))
			periodo = Pagina.getParamStr(request, "periodo");
		else
			periodo = "1";		
		if("".equals(periodo))
			periodo = "1";
		
		// essa variavel é utilizada no include do cabecalho das paginas
		String telaAnterior = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo + "&periodo=" + periodo;
		
		String hidAcao = Pagina.getParam(request, "hidAcao");
		String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
		
		Boolean exibirAbasAcompanhamento = Boolean.TRUE;
		if(formaVisualizacao.equalsIgnoreCase("personalizado")) {
			exibirAbasAcompanhamento= Boolean.FALSE;
		}
%>
		<br><br>
		<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
			chamarPagina="../acompanhamento/posicaoGeral.jsp" 
			exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>"
			tela="lista"
			caminho="<%=_pathEcar%>" 
			gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
			request = "<%=request%>"
			hidAcao = "<%=hidAcao%>"
		/>
<%		
		//Lista todos os AREF's (ciclos de referência) por tipo de acompanhamento(esse tipo de acompanhamento foi passado pelo request)
		List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));
		AcompReferenciaAref acompReferencia = null;
		
		Iterator itAcomp = acompanhamentos.iterator();
		while(itAcomp.hasNext()){
			acompReferencia = (AcompReferenciaAref) itAcomp.next();
			if((acompReferencia.getCodAref().toString()).equals(mesReferencia)) {
				break;
			}
		}
		
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
%>		
		<util:barraLinksRegAcomp
			acompReferencia = "<%=acompReferencia%>"
			selectedFuncao=""
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			abaSuperior="<%= Dominios.SIM %>"
			listaGeral="<%=telaAnterior %>"
			caminho="<%=_pathEcar%>"
			tipoFiltroEscolhido = "<%=hidAcao%>"
		/> 
		
		<br><br>
<%
	} else {
%>
	
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
		chamarPagina="listaRelAcomp.jsp"
		tela="opcoes"
		caminho="<%=_pathEcar%>"
		gerarRelatorio="<%=Boolean.TRUE%>"
		exibirAbaFiltro="<%=Boolean.FALSE%>"
	/>
<%
	}
%>
	
	<form name="form" method="post">
		<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
		
	<!-- util:barraLinksRelatorioItens
		itensBarra="< %=itensBarra%>"
		chamarPagina="relAcompOpcoes"
		indexAtivo="< %=index%>"
		semLinks="S"
	/-->
	
	</thead>
	
	<tbody id="corpo1" >
	
	<table class="form" border="0">
		<tr>
			<td width="15%">&nbsp;Arquivos Gerados</td>
		</tr>
	</table>
	
	<table>	
	<%
	List caminhoArquivoExportacaoBean = telaBean.getCaminhosArquivoComHash(request);
	if (caminhoArquivoExportacaoBean != null && caminhoArquivoExportacaoBean.size() > 0){
	%>	
		<display:table id="displayTag" name="<%=caminhoArquivoExportacaoBean%>"  export="false" excludedParams="hidAcao">
			<display:column property="nomeEstrutura" title="Estrutura" sortable="true" group="1"/>
			<display:column property="nomeFuncao" title="Função"  sortable="true" group="2"/>
			<display:column property="nomeArquivo" title="Arquivo"  sortable="true" href="../DownloadFile" paramId="RemoteFile" paramProperty="caminhoFisicoSemEncode" />
		</display:table>
	<%	
	} else {
		String msgNenhumArquivo = _msg.getMensagem("exportacao.comunicacao.geracaoArquivo.nenhumArquivo");
		%>
		<tr>
			<td>
			<%=msgNenhumArquivo%>
			</td>
		</tr>
		<%
	}
	%>
	</table>	

	<%@ include file="../../include/estadoMenu.jsp"%>
		
	</form>
<%	
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
	%>
	<script language="javascript">
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td>&nbsp;</td></tr>
</table>

</div> <!-- conteudo -->
</body>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>