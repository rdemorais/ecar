<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="ecar.pojo.Aba"%>
<%@page import="ecar.pojo.Cor"%>
<%@page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.pojo.ConfigMailCfgm"%>
<%@page import="ecar.pojo.SisGrupoAtributoSga"%>

<%@page import="ecar.pojo.UploadTipoArquivoUta"%>
<%@page import="ecar.pojo.UploadTipoCategoriaUtc"%>
<%@page import="ecar.pojo.SituacaoSit"%>
<%@page import="ecar.pojo.SisAtributoSatb"%>
<%@page import="ecar.pojo.ItemEstUsutpfuacIettutfaPK"%>
<%@page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@page import="ecar.pojo.AcompRelatorioArel"%>
<%@page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@page import="ecar.pojo.StatusRelatorioSrl"%>
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@page import="ecar.pojo.ItemEstruturaIett" %>
<%@page import="ecar.pojo.AcompReferenciaAref"%>
<%@page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@page import="ecar.pojo.ItemEstrutUploadIettup"%>
<%@page import="ecar.pojo.ItemEstrUplCategIettuc"%>
<%@page import="ecar.pojo.UsuarioUsu"%>

<%@page import="ecar.dao.AbaDao"%>
<%@page import="ecar.dao.CorDao"%>
<%@page import="ecar.dao.UsuarioDao"%>
<%@page import="ecar.dao.ConfiguracaoDao"%>
<%@page import="ecar.dao.ItemEstruturaDao"%>
<%@page import="ecar.dao.AcompReferenciaItemDao"%>
<%@page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@page import="ecar.dao.AcompReferenciaDao"%>
<%@page import="ecar.dao.EstruturaFuncaoDao"%>
<%@page import="ecar.dao.ConfigMailCfgmDAO"%>
<%@page import="ecar.dao.ItemEstruturaUploadDao"%>
<%@page import="ecar.dao.TipoFuncAcompDao"%>
<%@page import="ecar.dao.AcompRelatorioDao"%>
<%@page import="ecar.dao.SituacaoDao"%>
<%@page import="ecar.dao.ItemEstruturaUploadCategoriaDao"%>
<%@page import="ecar.dao.UploadTipoCategoriaDao"%>

<%@page import="ecar.util.Dominios"%>
<%@page import="ecar.permissao.ValidaPermissao"%>
<%@page import="ecar.exception.ECARException"%>

<%@page import="comum.util.Data"%>
<%@page import="comum.util.FileUpload"%>
<%@page import="comum.util.Mensagem"%>
<%@page import="comum.util.Pagina"%>
<%@page import="comum.util.Util"%>
<%@page import="comum.database.Dao"%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>


<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%

try {
	boolean ehRegistro = true;
	
	String tela = Pagina.getParamStr(request, "tela");
	if(tela !=null && (tela.equals("V")))
		ehRegistro = false;
	
	Dao dao = new Dao();
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request); 
	AcompReferenciaItemDao ariDao =  new AcompReferenciaItemDao(request);
	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao();
	SituacaoDao situacaoDao = new SituacaoDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR) session.getAttribute("seguranca")).getUsuario();

	ConfiguracaoCfg conf = (ConfiguracaoCfg)session.getAttribute("configuracao");
	
	
	String indExibirSituacoesFormatoAbas = ""; 
	if(conf.getIndExibirSituacoesFormatoAbas() != null) {
		indExibirSituacoesFormatoAbas = conf.getIndExibirSituacoesFormatoAbas();
	}
	
	
	String ocultarObservacoesParecer = conf.getIndOcultarObservacoesParecer();
	

	/* permissão de visualização de parecer */
	boolean permissaoConsultarParecer = false;
	/* permissao de edicao de parecer */
	/* Esta permissão depende de muitos parâmetros do sistema */
	boolean permissaoAlterarParecer = false;
	/*indica se o tipo de acompanhamento é separado por orgao ou nao*/
	boolean ehSeparadoPorOrgao = false;
	

	String strCodAri = Pagina.getParamStr(request, "codAri");
	String strCodAcomp = Pagina.getParamStr(request, "codAcomp");
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String msgConfirm = null;

	// Veio nova referência, selecionada na combo, deve trocar o Ari.
	if (!Pagina.getParamStr(request, "referencia").equals("")
	 && !"S".equals(Pagina.getParamStr(request,	"linkControle"))) {
//		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	/*  Acompanhamento referencia inicializada antes para que a autorização seja feita logo */
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) dao.buscar(AcompReferenciaItemAri.class, Long	.valueOf(strCodAri));
	
	if (mesReferencia!= null && mesReferencia.equals("")){
		mesReferencia = ari.getAcompReferenciaAref().getCodAref().toString();
	}

	ValidaPermissao validaPermissao = new ValidaPermissao();
			
	// esse teste é necessário para o primeiro item	
	EstruturaFuncaoEttf estruturaFuncaoItensAnexo = null;
	if(ari.getItemEstruturaIett().getItemEstruturaIett() != null) {	
		estruturaFuncaoItensAnexo = (new EstruturaFuncaoDao(request)).getItensAnexo(ari.getItemEstruturaIett().getItemEstruturaIett().getEstruturaEtt());
	} else {
		estruturaFuncaoItensAnexo = (new EstruturaFuncaoDao(request)).getItensAnexo(ari.getItemEstruturaIett().getEstruturaEtt());
	}
	
	permissaoAlterarParecer = validaPermissao.permissaoAlterarItem(ari.getItemEstruturaIett(), seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoItensAnexo).booleanValue() ;
	
	StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) dao.buscar(StatusRelatorioSrl.class,Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
	TipoAcompanhamentoTa tipoAcompanhamento = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(); 
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
	
	//Lista todos os AREF's (ciclos de referência) por tipo de acompanhamento(esse tipo de acompanhamento foi passado pelo request)
	List acompanhamentosReferencia = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(tipoAcompanhamento.getCodTa());
	
	/* permissão de liberação de acompanhamento */
	Boolean permissaoReferencia = new Boolean(false);
	permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferencia(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(),seguranca.getGruposAcesso() ));
			
	List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());			

	if (validaPermissao.permissaoConsultaParecerIETT(ari.getItemEstruturaIett().getCodIett(), seguranca)){
		permissaoConsultarParecer = true;
	}
	
	AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
	
	// Mantis 0011550 
	// Quando o a aba de pareceres é a primeira configurada aba aparecer não deveria chegar aqui
	// se não existem relatórios que o usuário pode ver.
	
	// conta os pareceres que existem das funcoes de acompanhamento que podem visualizar pareceres. 
	if (arelDao.ContaArelsDasFuncoesDoAri(ari, listaPermissaoTpfa) == 0){
		
		String enderecoAbaVisualizacao = "";
		
		AbaDao abaDao = new AbaDao(request);
		
		List listAbasComAcesso = abaDao.getListaAbasComAcessoConfiguradasNaEstrutura(ari.getItemEstruturaIett().getEstruturaEtt(), tipoAcompanhamento ,seguranca.getGruposAcesso());
		
		String nomeAba = "DETALHAR_ITEM"; //Nome da aba padrão.
		
	    if (listAbasComAcesso!=null && !listAbasComAcesso.isEmpty()) {
	    	if (listAbasComAcesso.size() > 0){
	    		Aba abaDirecionaRegistro = (Aba) listAbasComAcesso.get(0);
	        	nomeAba = abaDirecionaRegistro.getNomeAba();
	        	if("SITUACAO".equals(nomeAba)){
	        		if (listAbasComAcesso.size() > 1){
	        			abaDirecionaRegistro = (Aba) listAbasComAcesso.get(1);
	    	        	nomeAba = abaDirecionaRegistro.getNomeAba();
	        		}
	        		else{
	        			nomeAba = "DETALHAR_ITEM";
	        		}
	        	}
	    	}        		
	    }
		
	    enderecoAbaVisualizacao = /*_pathEcar +*/ "/" +abaDao.pesquisaEnderecoAbaRegistro(tipoAcompanhamento, seguranca.getGruposAcesso(), Integer.parseInt(Pagina.getParamStr(request, "tipoPadraoExibicaoAba")), nomeAba);
	    
	    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(enderecoAbaVisualizacao);
	    dispatcher.forward(request, response);
	}

	
	String abaSelecionada = Pagina.getParamStr(request, "funcaoParecer"); 	
	String periodo = Pagina.getParamStr(request, "periodo");
	AcompReferenciaAref arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();
	
	if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
		ehSeparadoPorOrgao = true;
	}

%>
<html lang="pt-br">
<head>
	<%@ include file="../../include/meta.jsp"%>
	<%@ include file="../../titulo.jsp"%>

	<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
	<!--script language="javascript" src="<%=_pathEcar%>/js/prototype.js"></script-->
	<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/jquery.1.7.1.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/jquery.qtip.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=_pathEcar%>/js/jquery.qtip.css" type="text/css">
	<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/ckeditor/ckeditor.js" type="text/javascript"></script>
</head>
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<div id="conteudo"> 
<form  name="form" id="form" method="post">
<% 

	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento"); 
	boolean exibeCabecalho = true;
	List<ItemEstruturaIett> listaDescendentes = new ArrayList<ItemEstruturaIett>();
		
	if(exibeCabecalho) {
		ItemEstruturaIett itemEstrutura = ari.getItemEstruturaIett();
		
		listaDescendentes = itemEstruturaDao.listarDescendentes(itemEstrutura.getCodIett());
		
		String funcaoSelecionada = "SITUACAO";
		
		AbaDao abaDao = new AbaDao(request);
		Aba aba = null;
				
		// Validação registro ou visualização
		if(ehRegistro) {
	//		aba = abaDao.buscarAbaPadrao(1);
		//	funcaoSelecionada = aba.getNomeAba().toUpperCase();
			%>	<%@ include file="frmRegistro.jsp" %>	<%
		} else {
			//aba = abaDao.buscarAbaPadrao(2);
			//funcaoSelecionada = aba.getNomeAba().toUpperCase();
			%>	<%@ include file="frmVisualizar.jsp" %>	<%
		}

	}
%>

	<input type="hidden" name="permissaoReferencia" value="<%=permissaoReferencia%>">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="codAri" id="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
	
	
	<!-- variaveis setadas para poder executar as ações em cima de diferentes arels -->
	<input type="hidden" name="codArel" id="codArel" value="">
	<input type="hidden" name="codTpfa" id="codTpfa" value="">
	<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>">
	<input type="hidden" name="usuarioUsu" value="<%=usuario.getCodUsu()%>">
	<input type="hidden" name="funcao" id="funcao" value="">
	<input type="hidden" name="funcaoParecer" id="funcaoParecer" value="">
	<input type="hidden" name="autorizarMail" id="autorizarMail" value="">
	<input type="hidden" name="indLiberado" id="indLiberado" value="">	
	<input type="hidden" name="cor" id="cor" value="">
    <input type="hidden" name="situacaoSit" id="situacaoSit" value="">
    <input type="hidden" name="descricaoArel" id="descricaoArel" value="">
    <input type="hidden" name="complementoArel" id="complementoArel" value="">
	<input type="hidden" name="nomeUsu" id="nomeUsu" value="">
	<input type="hidden" name="codUsu" id="codUsu" value="">
	
	<!-- campos para adição de anexo -->
	<input type="hidden" name="cod" id="cod" value="<%=Pagina.getParamStr(request, "cod") %>">
	<input type="hidden" name="inclusaoAnexo" id="inclusaoAnexo" value="">
	<input type="hidden" name="codIett" id="codIett" value="<%=ari.getItemEstruturaIett().getCodIett().toString()%>">
	<input type="hidden" name="arquivoIettup" id="arquivoIettup" value="">
	<input type="hidden" name="descricaoIettup" id="descricaoIettup" value="">
	<input type="hidden" name="uploadTipoArquivoUta" id="uploadTipoArquivoUta" value="">
	<input type="hidden" name="codIettuc" id="codIettuc" value="">
	<input type="hidden" name="hidAcao" id="hidAcao" value="">
	
	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
	
	<!-- Campos para manter a seleção em Posição Geral -->
	<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">
	
	<!-- PODE SER QUE USE -->
	<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<input type=hidden name="abaSelecionada" value="<%=abaSelecionada%>">
	
	<input type="hidden" name="codRegd" value="">
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="left">
			<%
					
				if("".equals(periodo))
					periodo = "1";
				int intPeriodo = 1;
				try {
					intPeriodo = Integer.parseInt(periodo);
				} catch(NumberFormatException numE) { }
			 %>
				<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->
				<br />
				<span class="label">Ciclo(s) de Exibição:</span>
				<select name="periodo" onchange="reloadPeriodo(this,'<%=tela%>')">
					<option value="" >  --SELECIONE-- </option>
					<% for(int i=1; i<=12; i++) { %>
					<option value="<%=i %>" <%= (intPeriodo ==i ?"selected":" ") %>> <%=i %> <%=i > 1 ? "Ciclos" : "Ciclo" %></option>
					<% } %>
				</select> 
			</td>
		</tr>			
	</table>
	<br />
</form>
<form  name="formMulti" id="formMulti" method="post" enctype="multipart/form-data">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="codAri" id="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	
	<!-- variaveis setadas para poder executar as ações em cima de diferentes arels -->
	<input type="hidden" name="codArel" id="codArel" value="">
	<input type="hidden" name="codTpfa" id="codTpfa" value="">
	<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>">
	<input type="hidden" name="codUsu" id="codUsu" value="<%=usuario.getCodUsu()%>">
	<input type="hidden" name="usuarioUsu" id="usuarioUsu" value="<%=usuario.getCodUsu()%>">
	<input type="hidden" name="funcao" id="funcao" value="">
	<input type="hidden" name="funcaoParecer" id="funcaoParecer" value="">
	<input type="hidden" name="autorizarMail" id="autorizarMail" value="">
	<input type="hidden" name="indLiberado" id="indLiberado" value="">	
	<input type="hidden" name="cor" id="cor" value="">
    <input type="hidden" name="situacaoSit" id="situacaoSit" value="">
    <input type="hidden" name="descricaoArel" id="descricaoArel" value="">
    <input type="hidden" name="complementoArel" id="complementoArel" value="">
    
    <input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
<%	
	List setCores2 = new CorDao(request).listar(Cor.class, new String[]{"ordemCor","asc"});
	if (setCores2 != null) {
		Cor cor2 = null;
		Iterator itCores = setCores2.iterator();
		while (itCores.hasNext()) {
			cor2 = (Cor) itCores.next();
%>
	<input type="hidden" name="ant_<%=cor2.getCodCor()%>" id="ant_<%=cor2.getCodCor()%>" value="">
	<input type="hidden" name="freq_<%=cor2.getCodCor()%>" id="freq_<%=cor2.getCodCor()%>" value="">
	<input type="hidden" name="ativo[<%=cor2.getCodCor()%>]" id="ativo[<%=cor2.getCodCor()%>]" value="">
<%		}
	}
%>	
	<input type="hidden" name="descricaoSolucaoPtc" id="descricaoSolucaoPtc" value="">
	<input type="hidden" name="dataSolucaoPtc" id="dataSolucaoPtc" value="">
	<input type="hidden" name="nomeUsu" id="nomeUsu" value="">
	<input type="hidden" name="excluirPtCritico" id="excluirPtCritico" value="">
	
	<!-- campos para adição de anexo -->
	<input type="hidden" name="cod" id="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
	<input type="hidden" name="inclusaoAnexo" id="inclusaoAnexo" value="">
	<input type="hidden" name="descricaoIettup" id="descricaoIettup" value="">
	<input type="hidden" name="codIett" id="codIett" value="<%=ari.getItemEstruturaIett().getCodIett().toString()%>">
	<input type="hidden" name="excluirAnexo" id="excluirAnexo" value="">
	
	<span style="display:none">
		<input type="file" name="arquivoIettup" id="arquivoIettup">
	</span>
	
	<input type="hidden" name="uploadTipoArquivoUta" id="uploadTipoArquivoUta" value="">
	<input type="hidden" name="codIettuc" id="codIettuc" value="">
	<input type="hidden" name="hidAcao" id="hidAcao" value="">
	
	
	<!-- Campos para manter a seleção em Posição Geral -->
	<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">
	
	<!-- PODE SER QUE USE -->
	<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	
	<!-- tela com a parte de pareceres que foram consolidados -->
	<%@ include file="consolidado.jsp"%>
	
	<!-- Configuração da lista de pareceres -->
	
	<!-- Se for pra exibir em abas -->
	
	<%if(indExibirSituacoesFormatoAbas.equals("S")) {
		request.setAttribute("tipoPadraoExibicaoAba",  Pagina.getParamStr(request, "tipoPadraoExibicaoAba"));
	%>
	
	<util:barraLinksRegAcompParecer
		acompReferenciaItem="<%=ari%>"  
		selectedFuncao="<%=abaSelecionada%>"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		request="<%=request%>"
		itensFuncaoAcompanhamento="<%=itensFuncaoAcompanhamento%>" 
		periodo="<%=periodo%>"
		tela="<%=tela%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
	    primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
	    itemDoNivelClicado="<%=request.getParameter("itemDoNivelClicado")%>"
	    codTipoAcompanhamento="<%=codTipoAcompanhamento%>"
	    hidFormaVisualizacaoEscolhida="<%=request.getParameter("hidFormaVisualizacaoEscolhida")%>"	    
	    
	/> 
	
	<% 
	}
	%>
	<%if (listaPermissaoTpfa != null && !listaPermissaoTpfa.isEmpty()){ 
	%>
	<!-- tela com a parte de emissão de parecer -->
	
	<%@ include file="avaliacoes.jsp" %>
	<%} %>
</form>
	
<script type="text/javascript">

function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}
function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
function reload(){
	if(document.forms[0].periodo.value != ""){
		document.forms[0].action = window.location.href;
		document.forms[0].submit();		
	}
}
function reloadPeriodo(periodo, tela){	
	form.periodo.value = periodo.value;
	if(document.forms[0].periodo.value != ""){
		document.forms[0].action = "relatorios.jsp?tela=" + tela;
		document.forms[0].submit();		
	}
}



</script>

<script language=javascript>
	
	if('<%=msgConfirm%>' != 'null' && '<%=msgConfirm%>' != '' && <%=permissaoReferencia%> == true){
  		if (confirm('<%=msgConfirm%>')){
			form.action = "<%=_pathEcar%>/acompanhamento/datasLimites/datasLimites.jsp?vemDaPgAcompRelatorio=S";
			form.submit();	
  		} 
	}
	
	
	
</script>




<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e) {
 %>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</div>

<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>

</body>
</html>