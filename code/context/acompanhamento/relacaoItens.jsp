<jsp:directive.page import="ecar.pojo.Link"/>
<jsp:directive.page import="ecar.dao.LinkDao"/>
<jsp:directive.page import="ecar.dao.ItemEstUsutpfuacDao"/>
<jsp:directive.page import="ecar.dao.EstruturaAcessoDao"/>

<jsp:directive.page import="ecar.dao.EstAtribTipoAcompEataDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>

<jsp:directive.page import="ecar.pojo.Pesquisa"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.dao.PesquisaDao"/>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.bean.NomeImgsNivelPlanejamentoBean"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.OrgaoDao"%>
<%@ page import="ecar.pojo.OrgaoOrg"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao"%>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>
<%@ page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.EstruturaAcessoDao"%>

<%@ page import="comum.util.Data"%>
<%@ page import="comum.database.Dao"%>

<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>

<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>

<%
Date dataInicio = Data.getDataAtual();

String hidAcao = Pagina.getParam(request, "hidAcao");
String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");


%>


<%@page import="ecar.pojo.SisGrupoAtributoSga"%>
<%@page import="comum.util.Util"%>
<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@page import="java.util.Arrays"%>
<%@page import="ecar.pojo.Etiqueta"%>
<%@page import="ecar.servico.EtiquetaService"%>
<%@page import="org.apache.commons.lang.StringUtils"%><html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/listaItens.js" type="text/javascript"></script>		 
		<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
		<!-- As funções javascript estam nesta biblioteca -->
		<script language="javascript" src="<%=_pathEcar%>/js/relacaoItensJs.js" type="text/javascript"></script>						
		<script type="text/javascript">
		


function reloadNivelPlanejamento() {

	
	//se for pesquisa salva nao guarda o tipo de filtro
	if(document.form.hidAcao != null && document.form.hidAcao.value == "pesquisaSalva") {
		document.form.formaVisualizacao.value = '<%=Pagina.getParamStr(request, "formaVisualizacao")%>';
	}	
	document.form.action =  "<%=idPagina%>.jsp";
	document.form.submit();		
}
		

function reload2(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != "" && document.form.mesReferencia.value != "0"){
		//se for pesquisa salva nao guarda o tipo de filtro
		if(document.form.hidAcao != null && document.form.hidAcao.value == "pesquisaSalva") {
			document.form.formaVisualizacao.value = '<%=Pagina.getParamStr(request, "formaVisualizacao")%>';
		}

		document.form.relatorio.value = "true";
		document.form.situacaoDatas.value = "";
		document.form.action ="<%=idPagina%>.jsp";
		document.form.submit();		
	}
}

function aoClicarGerarArquivo() {
	document.form.action = "../relAcompanhamento/geracaoArquivos.jsp?posicaoGeral=S";
	document.form.submit();
}

function reloadAlterarReferenciaRelatorio(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != "" && document.form.mesReferencia.value != "0"){
		//se for pesquisa salva nao guarda o tipo de filtro
		if(document.form.hidAcao != null && document.form.hidAcao.value == "pesquisaSalva") {
			document.form.hidAcao.value =  "";
		}
		document.form.relatorio.value = "true";
		document.form.action =  "<%=idPagina%>.jsp";
		document.form.submit();		
	}
}

function reloadSituacaoDatas(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != "" && document.form.mesReferencia.value != "0"){
		//se for pesquisa salva nao guarda o tipo de filtro
		if(document.form.hidAcao != null && document.form.hidAcao.value == "pesquisaSalva") {
			document.form.hidAcao.value =  "";
		}

		document.form.relatorio.value = "true";
		document.form.situacaoDatas.value = "true";
		document.form.action =  "<%=idPagina%>.jsp";
		document.form.submit();		
	}
}


function reload(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != "" && document.form.mesReferencia.value != "0"){
		//se for pesquisa salva nao guarda o tipo de filtro
		if(document.form.hidAcao != null && document.form.hidAcao.value == "pesquisaSalva") {
			document.form.hidAcao.value =  "";
		}
		document.form.action =  "<%=idPagina%>.jsp";
		document.form.submit();		
	}
}

</script>

	</head>
<%
try {
	request.setAttribute("exibirAguarde", "true");
	AcompReferenciaItemAri ari = null;
%>
<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

<%

	//Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	
	
	List<Long> etiquetas = new ArrayList<Long>();
	
	String [] etiquetasSelecionadas = request.getParameterValues("etiquetas");
	
	if(etiquetasSelecionadas != null){
		for(int i = 0; i < etiquetasSelecionadas.length; i++){
			if(StringUtils.isNotBlank(etiquetasSelecionadas[i])){
				String [] arrayEtiquetas = etiquetasSelecionadas[i].split(",");
				for(int j = 0; j < arrayEtiquetas.length; j++){
					if(StringUtils.isNotBlank(arrayEtiquetas[j])){
						etiquetas.add(new Long(arrayEtiquetas[j]));
					}
				}
			}
		}
	}
	

	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	ValidaPermissao validaPermissao = new ValidaPermissao();
	AcompReferenciaAref acompReferencia = null;
	boolean ehSeparadoPorOrgao = false;
	String enderecoAbaVisualizacaoSemFuncao = "";
		
	//status indica se é relatório
	String status = Pagina.getParamStr(request, "relatorio");
	String situacaoDatas = Pagina.getParamStr(request, "situacaoDatas");
	String flag = "";
	int contador = 0;
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	
	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		}
	}

	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
	if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
		ehSeparadoPorOrgao = true;
	}
	
	EstruturaDao estruturaDao = new EstruturaDao(request);
	EstAtribTipoAcompEataDao estAtribTipoAcompEataDao = new EstAtribTipoAcompEataDao(request);
	EstruturaAcessoDao estruturaAcessoDao = new EstruturaAcessoDao(null);
	
	//verifica se o usuário pode "Gerar Ciclo de Acompanhamento
	boolean permissaoAdministradorAcompanhamento = estruturaAcessoDao.temPermissoesAcessoAcomp(tipoAcompanhamento, seguranca.getGruposAcesso());
	
	
	//Define a forma de visualização das abas de acompanhamento
	//Filtro geral -> exibe todas as abas
	// Demais filtros -> só exibe a aba específica do acompanhamento selecionado
			
	Boolean exibirAbasAcompanhamento = Boolean.TRUE;
	if(formaVisualizacao.equalsIgnoreCase("personalizado")) {
		exibirAbasAcompanhamento= Boolean.FALSE;
	} 	
	
	Map mapItensReferencia = new HashMap();
	
%>
	<body><!-- TÍTULO --> 
	<div id="conteudo">
	
	<%@include file="/titulo_tela.jsp"%>
	<br><br>
	
	
	<%
	
	if("".equals(codTipoAcompanhamento)) {
%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="linha_subtitulo" align="left">
					<td>
						<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
					</td>
				</tr>
			</table>

<%
	} else {
		
%>

		<!--------------------------  ABA DE TIPO DE ACOMPANHAMENTO---------------------------------->

		<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
			chamarPagina="posicaoGeral.jsp" 
			exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>"
			tela="lista"
			caminho="<%=_pathEcar%>" 
			gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
			request = "<%=request%>"
			hidAcao = "<%=hidAcao%>"
		/>
		
		<!--------------------------  ABA DE TIPO DE ACOMPANHAMENTO---------------------------------->
		
		
<%
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	UsuarioDao usuarioDao = new UsuarioDao(request);
	OrgaoDao orgaoDao = new OrgaoDao(request);
	ItemEstrutMarcadorDao marcadorDao = new ItemEstrutMarcadorDao(request);
	CorDao corDao = new CorDao(request);
	AbaDao abaDao = new AbaDao(request);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	LinkDao linkDao = new LinkDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	
	Boolean isSemInformacaoNivelPlanejamento  = null;
	ItemEstruturaIett itemEstrutura = null;
	UsuarioUsu usuario = null;
	AcompReferenciaAref arefSelecionado = null;

	
	String strCodAri = Pagina.getParamStr(request, "codAri");	
	//Pega no request (como String q na primeira iteração são vazios) o "mesReferencia" e o "niveisPlanejamento"
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");		
	//verifica se foi feita alguma alteração no nivel de planejamento
	String niveisPlanejamentoAlterado  = Pagina.getParamStr(request, "niveisPlanejamentoAlterado");
	

	if (Pagina.getParamStr(request, "hidAcao").equals("pesquisaSalva")){
		if (!Pagina.getParamStr(request, "codReferencia").equals("")){
			mesReferencia =  Pagina.getParamStr(request, "codReferencia");
		}
	}

	// Pega da configuração para saber o número de itens para paginação
	int nuPagina = 1; //qual a pagina que esta sendo visualizada
	if(Pagina.getParamStr(request, "nuPagina") != null && !Pagina.getParamStr(request, "nuPagina").equals("")) {
		nuPagina = Integer.parseInt(Pagina.getParamStr(request, "nuPagina"));
	} else if(request.getAttribute("nuPagina") != null && !request.getAttribute("nuPagina").equals("")) {
		nuPagina = Integer.parseInt((String)request.getAttribute("nuPagina"));
	}
	int nuItensPaginacao = configuracao.getNuItensExibidosPaginacao(); //numero de itens a serem exibidos por pagina
	int nuItensTotal = 0;
	
	//verificar se a aba situacao esta configurada para aparecer e seta os parametros para chamar o javascript
	Aba abaVisualizacao = abaDao.buscarAbaItemComAcesso(tipoAcompanhamento, seguranca.getGruposAcesso(), "SITUACAO");
	boolean situacaoEstaConfigurada = false;
	String nomeAbaVisualizacao = "";
	if(abaVisualizacao != null) {
		situacaoEstaConfigurada = true;
	} else { 
		// se a aba nao estiver configurada procura o nome para montar a mensagem para o usuário
		 abaVisualizacao = abaDao.buscarAba("SITUACAO");
		 nomeAbaVisualizacao = abaVisualizacao.getLabelAba();	
	}
	String enderecoAba = abaDao.pesquisaEnderecoAbaRegistro(tipoAcompanhamento, seguranca.getGruposAcesso(), 1, null);
	String enderecoAbaVisualizacao = abaDao.pesquisaEnderecoAbaRegistro(tipoAcompanhamento, seguranca.getGruposAcesso(), 2, null);
	
	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturasAtivasInativas();
	
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
	//Se vier nulo ou vazio, seta para o valor padrão que é Não
	if (exigeLiberarAcompanhamento == null || exigeLiberarAcompanhamento.trim().equals("")){
		exigeLiberarAcompanhamento = Dominios.NAO;
	}
	EstruturaEtt menorEttCfg = null;
	int menorNivel = -1;
	String itemDoNivelClicado = "";
	
	if("posicaoGeral".equals(idPagina)) {
		if(configuracao != null){
			menorEttCfg = tipoAcompanhamento.getEstruturaEtt();
			if(menorEttCfg != null){
				menorNivel = estruturaDao.getNivel(menorEttCfg);
			}
		}
	} else if("proximosItens".equals(idPagina)) {
		itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");

		if(itemDoNivelClicado != null && !"".equals(itemDoNivelClicado)){
			menorEttCfg = ((ItemEstruturaIett)(itemDao.buscar(ItemEstruturaIett.class, Long.valueOf (itemDoNivelClicado)))).getEstruturaEtt();
			if(menorEttCfg != null){
				//Menor nivel = nivel do item escolhido na tela anterior + 1
				menorNivel = estruturaDao.getNivel(menorEttCfg) + 1;
			}
		}
	}
	

	////REFERENCIAS///////////////////////////////
	
	//Lista todos os AREF's (ciclos de referência) por tipo de acompanhamento(esse tipo de acompanhamento foi passado pelo request)
	List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));
	List comboReferencia = new ArrayList();
	if(ehSeparadoPorOrgao)
		comboReferencia = acompReferenciaDao.getComboReferencia(tipoAcompanhamento);
	else 
		comboReferencia = new ArrayList(acompanhamentos);
	
	//Coloca na sessão as referências agrupadas que são exibidas no combo para serem utilizadas em outras telas
	session.setAttribute("referenciasAgrupadasCombo", comboReferencia);
	
	//Lista o AREF mais próximo da data atual
	//ATENÇÃO: Depois, esse Aref poderá ser passado via request 
	AcompReferenciaAref arefAtual = acompReferenciaDao.getAcompSelecionado(comboReferencia);
	if(mesReferencia.equals(""))
		mesReferencia = arefAtual.getCodAref().toString();
	
	String strLabelOrgao = configuracao.getLabelOrgao();
	
	usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	//Níveis de Planejamento por usuário -> Lista em strAux os CodSatb separados por ":"  
	List niveisUsuario = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
	Iterator itNiveisUsu = niveisUsuario.iterator();
	StringBuffer strAux = new StringBuffer("");	
	while(itNiveisUsu.hasNext()) {
		UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsu.next();
		if(usuAtributo.getSisAtributoSatb().getIndAtivoSatb().equals("S")) {
			strAux.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
			strAux.append(":");
		}	
	}
	String strNiveisPlanejamentoUsuario = strAux.toString();
	
	//Seta o ciclo (1 Ciclo, 2 Ciclo, etc..)
	String periodo = "";	
	String periodoEscolhido="";
	if(!"true".equals(status))
		periodo = Pagina.getParamStr(request, "periodo");
	else
		periodo = "1";		
	if("".equals(periodo))
		periodo = "1";
	periodoEscolhido = periodo + (("1".equals(periodo)) ? " Ciclo" : " Ciclos");
	
	// Seta "niveisPlanejamento" com os códigos do sisAtributo separados por ":" ex.:(64:59:), isso por usuário
	String niveisPlanejamento = "";
	if("S".equals(niveisPlanejamentoAlterado)){
		niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	} else{
		if(request.getSession().getAttribute("niveisPlanejamento")!=null){
			niveisPlanejamento = (String)request.getSession().getAttribute("niveisPlanejamento");			
		}
	}
	
	// verifica se foi marcado a opção "sem informação" no filtro de nivel de planejamento 
	String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
	if("".equals(semInformacaoNivelPlanejamento)){
		if(request.getSession().getAttribute("semInformacaoNivelPlanejamento")!=null && !"".equals(request.getSession().getAttribute("semInformacaoNivelPlanejamento"))){
			semInformacaoNivelPlanejamento = (String)request.getSession().getAttribute("semInformacaoNivelPlanejamento");			
		}
	}
	
	if((Dominios.SIM).equals(semInformacaoNivelPlanejamento)) { 
		isSemInformacaoNivelPlanejamento = new Boolean(true);
	} else {
		isSemInformacaoNivelPlanejamento = new Boolean(false);
	}

	//se o link estiver configurado 
	if("".equals(niveisPlanejamento) && linkDao.estaConfiguradoLink("NIVEL_PLANEJAMENTO")) {
		
		// caso o usuário tenha removido todos o niveis de planejamento marcados
		if(niveisPlanejamentoAlterado.equals("S")) {
			niveisPlanejamento = "";
		} else {
			//caso seja a primeira vez que a pagina é acessada, os niveis de planejamento selecionados vão ser iguais ao nivel de usuario + sem informação
			niveisPlanejamento = strNiveisPlanejamentoUsuario;
			//faz a pesquisa no banco
			isSemInformacaoNivelPlanejamento = new Boolean(true);
			//marca no popup
			semInformacaoNivelPlanejamento = "S";
		}
	} 

	
	//Seta o "orgaoResponsavel" com o valor do request
	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
	}
	//Se "orgaoResponsavel" for igual a null-> seta "orgaoEscolhido" como "Todos"
	String orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos";	
	
	//?Acho que não está usando?
	boolean comboOrgao = true;
	
	
	AcompReferenciaItemAri ariHidden = null;
	
	if(!itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ) {
		ariDao = new AcompReferenciaItemDao(request);
		itemEstruturaDao = new ItemEstruturaDao(request);	
		ItemEstruturaIett iett = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf (itemDoNivelClicado));
		ariHidden = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefAtual,iett);		
	}
	
	// essa variavel é utilizada no include do cabecalho das paginas
	String telaAnterior = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo + "&periodo=" + periodo;//Pagina.getParamStr(request, "periodo");
	
	
	Iterator acompanhamentosIt = acompanhamentos.iterator();
	while(acompanhamentosIt.hasNext()) {
		arefSelecionado = (AcompReferenciaAref)acompanhamentosIt.next();
		if((arefSelecionado.getCodAref().toString()).equals(mesReferencia)) {
			break;
		} 
	}

	if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
		ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
	} 
	
	//se nao coseguir encontrar o ari pela regra de cima, vai buscar pelo codigo
	if(ari == null && !"".equals(strCodAri)){
			// Busca o item de estrutura pelo codAri passado como parametro
			ari = (AcompReferenciaItemAri) ariDao.buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
			itemEstrutura = ari.getItemEstruturaIett();
	}			
	
	
 	boolean encontrouAcompReferencia = false;
	Iterator itAcomp = acompanhamentos.iterator();
	while(itAcomp.hasNext()){
		acompReferencia = (AcompReferenciaAref) itAcomp.next();
		if((acompReferencia.getCodAref().toString()).equals(mesReferencia)) {
			encontrouAcompReferencia = true;
			break;
		}
	}
	
	if(!encontrouAcompReferencia && arefAtual != null){
		acompReferencia	= arefAtual;
	}
	
	//seleciona a aba superior
	String abaSuperiorSelecionada =  null;	
	if(situacaoDatas.equals("true")){
		abaSuperiorSelecionada = "SITUACAO_PONTOS_CRITICOS";
	} else if(!"true".equals(status)){
		//se for lista geral
		abaSuperiorSelecionada = "SITUACAO";
	} else {
		abaSuperiorSelecionada =  "";
	}

	
%>		
		<!--------------------------  ABA DE LISTAS---------------------------------->

		<util:barraLinksRegAcomp
			acompReferencia = "<%=acompReferencia%>"
			selectedFuncao="<%=abaSuperiorSelecionada%>"
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

		<!--------------------------  ABA DE LISTAS---------------------------------->


			<form name="form" method="post" action="">
				<input type="hidden" name="nuPagina" value="<%=nuPagina%>">
				<input type="hidden" name="codTipoAcompanhamento" id="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" id="hidFormaVisualizacaoEscolhida" name="hidFormaVisualizacaoEscolhida" value="<%=formaVisualizacao %>">
				<!-- Guarda o codigo da pesquisa -->
				<input type="hidden" id="formaVisualizacao" name="formaVisualizacao" value="<%=formaVisualizacao %>">
				<!-- Guarda se a tela é relatorio ou de situação datas -->
				<input type="hidden" id="relatorio" name="relatorio" value="<%=status%>">
				<input type="hidden" id="situacaoDatas" name="situacaoDatas" value="<%=situacaoDatas%>">	
				<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">			
				
		  		<% 
		  		if(ariHidden != null){ %>
			  		<input type="hidden" name="codAri" value="<%=ariHidden.getCodAri()%>">								
					<input type="hidden" name="primeiroAriClicado" value="<%=ariHidden.getCodAri()%>">
				<%} else {%>
			  		<input type="hidden" name="codAri" value="">								
					<input type="hidden" name="primeiroAriClicado" value="">				
				<%} 				
				if(!itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null){ %>
					<input type="hidden" name="primeiroIettClicado" value="<%=itemDoNivelClicado%>">					
				<%} else {%>
					<input type="hidden" name="primeiroIettClicado" value="">
				<%}
				String codReferencia = Pagina.getParamStr(request, "codReferencia");
				if(codReferencia.equals("")){
					codReferencia = mesReferencia;
				} //referencia por Thaise
				if(codReferencia.equals("")){
					codReferencia = Pagina.getParamStr(request, "referencia");
					mesReferencia = codReferencia;
				} //referencia

				if("S".equals(niveisPlanejamentoAlterado)){
					request.getSession().setAttribute("niveisPlanejamento", niveisPlanejamento);
					request.getSession().setAttribute("semInformacaoNivelPlanejamento", semInformacaoNivelPlanejamento);
				}
				%>
				<input type="hidden" name="hidAcao" value="<%=hidAcao%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
				<input type="hidden" name="niveisPlanejamentoAlterado" value="<%=niveisPlanejamentoAlterado%>">
				<input type="hidden" name="primeiraChamada" value='<%=Pagina.getParamStr(request, "primeiraChamada")%>'>
				<input type="hidden" name="orgaoEscolhido" value="<%=orgaoEscolhido%>">
				<input type="hidden" name="periodoEscolhido" value="<%=periodoEscolhido%>">				
				<input type="hidden" name="ComboClicado" id="ComboClicado" value="">
				<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
				<input type="hidden" name="referencia" value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="semInformacaoNivelPlanejamento" id="semInformacaoNivelPlanejamento" value="<%=semInformacaoNivelPlanejamento%>">
				<input type="hidden" name="codIettRelacao" value='<%=Pagina.getParamStr(request, "codIettRelacao")%>'>
				<input type="hidden" name="codigosItem" value="">
				<input type="hidden" name="primeiroCodAri" value="">
				<input type="hidden" name="codArisControle" value="">
				<input type="hidden" name="codArisControleVisualizacao" value="">
				<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
				
				
			<!-- itens gerais por request -->	
				<input type="hidden" name="codUsuarioLogado" id="codUsuarioLogado" value="<%=usuario.getCodUsu()%>">
				<input type="hidden" name="status" id="status" value="<%=status%>">
				<input type="hidden" name="pathEcar" id="pathEcar" value="<%=_pathEcar%>">										
				<input type="hidden" name="idPagina" id="idPagina" value="<%=idPagina%>">
				<input type="hidden" name="caminhoUrl" id="caminhoUrl" value="<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>">										
				<input type="hidden" name="exigeLiberarAcompanhamento" id="exigeLiberarAcompanhamento" value="<%=exigeLiberarAcompanhamento%>">										
				<input type="hidden" name="permissaoAdministradorAcompanhamento" id="permissaoAdministradorAcompanhamento" value="<%=permissaoAdministradorAcompanhamento%>">										
				<input type="hidden" name="codAref" id="codAref" value="<%=codReferencia%>">	

			<!-- Div para guardar os novos filhos de um determinado nó via Ajax. Todos os novos filhos 
				que serão processados serão registrados aqui. -->	
				<div id="hiddenFilhosExibir">
				</div>														
				

<%
		// se não tiver tipo de acompanhamento selecionado, não carregar dados,
		// ver "else" no fim do arquivo
		if(!"".equals(codTipoAcompanhamento)) {
%>

				<table border="0" width="100%">
					<% if("true".equals(status)){ %>
					<br></br>
					<tr>
						<%if("true".equals(situacaoDatas)){%>
							<input type="button" value="Gerar" class="botao" onclick="gerarRelatorio(form, true);">
						<%}else{%>
							<input type="button" value="Prosseguir" class="botao" onclick="gerarRelatorio(form, false);">	
						<%}%>
						
					</tr>
					<% } %>
					<tr>
						<td valign="bottom" class="texto" align="left">

							<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->
							<b>Ciclo de Exibição:</b>
							<select name="periodo" onchange="reload()"
								<% if("true".equals(status)){ %> disabled <% } %>>
								<option value="">
									--SELECIONE--
								</option>
								<% 
								for(int i=1;i<=12;i++){ 
									Integer inteiro = new Integer(i);
								%>
									<option value="<%=i%>"
										<%if(inteiro.toString().equals(periodo)) out.println("selected");%>>
										<%=inteiro%> <%=i > 1 ? "Ciclos" : "Ciclo" %>
									</option>
							  <%}%>
							</select>
						</td>
						<td align="right" valign="bottom" class="texto">
							<% /* Verifica a exibição do filtro Nível Planejamento */ %>

							<br>
							<b>Ciclo de referência: </b>
							<!-- COMBO DE PERÍODOS DE REFERÊNCIA -->
							<select name="mesReferencia" 
								<%
								if(!"true".equals(status)){ %>  
									onchange="javascript:reload()"
								<%} else if(!"true".equals(situacaoDatas)){ %>
									onchange="javascript:reloadAlterarReferenciaRelatorio()"
								<%} else {%>
									onchange="javascript:reloadSituacaoDatas()"
								<%} %>>
								
									<option value="0"
									<%
										if("0".equals(mesReferencia)){
									%> selected
									<%
									}
									%>>
										--SELECIONE--
									</option>
									<%
								String selMesRef = "";
						
							if (codReferencia.equals("")){
								if (!mesReferencia.equals("") && !mesReferencia.equals("0")){
									codReferencia = mesReferencia;
								}
								else{
									codReferencia = arefAtual.getCodAref().toString();
								}
							}
							
							if (!arefAtual.getCodAref().toString().equals(codReferencia)){
								arefAtual = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codReferencia));
							}
							
							Iterator itAcomp2 = comboReferencia.iterator();
							while(itAcomp2.hasNext()){
								acompReferencia = (AcompReferenciaAref) itAcomp2.next();
								
								
								if(arefAtual != null && 
									//se for separado por orgao, compara dia, mes e ano
									(ehSeparadoPorOrgao && arefAtual.getAnoAref().equals(acompReferencia.getAnoAref()) &&
										arefAtual.getMesAref().equals(acompReferencia.getMesAref()) &&
										arefAtual.getDiaAref().equals(acompReferencia.getDiaAref())) || 
									//se nao for separado por orgao, compara o cod_aref
									(!ehSeparadoPorOrgao && arefAtual.getCodAref().equals(acompReferencia.getCodAref()))) {
				
										selMesRef = "selected";
										mesReferencia = acompReferencia.getCodAref().toString();
							%>
									<script language="Javascript" type="text/javascript">
										document.form.referencia_hidden.value = "<%=mesReferencia%>";
										document.form.referencia.value = "<%=mesReferencia%>";
										document.form.codAref.value = "<%=mesReferencia%>";
									</script>
							<%
									
								} else {
									selMesRef = "";
								}
								
								StringBuffer opcaoCombo = new StringBuffer(acompReferencia.getDiaAref())
														.append("/").append(acompReferencia.getMesAref())
														.append("/").append(acompReferencia.getAnoAref());
								
								if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompReferencia).size() > 1){
									opcaoCombo.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
								} else {
									opcaoCombo.append(" - ").append(acompReferencia.getNomeAref());
								}
								
							%>
								<option value="<%=acompReferencia.getCodAref()%>" <%=selMesRef%>>
									<%=opcaoCombo%>
								</option>
							<%
							} %>
							</select>
						</td>
					</tr>
				</table>
			<%
			if((mesReferencia != null && !"".equals(mesReferencia)) && !"".equals(periodo)){	
				List listNiveis = new ArrayList();
				String[] niveis = niveisPlanejamento.split(":");
				for(int n = 0; n < niveis.length; n++){
					String codNivel = niveis[n];
					
					if(!"".equals(codNivel)){
						listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
					}
				}
	
				
				
				
				if("true".equals(status)){ 
%>
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto">
							 
							<%if(!"true".equals(situacaoDatas)){ %>
								<b>Selecione os itens para gerar relatório:</b>
							<% } else {%>
								<b>Selecione os ítens para os quais deseja gerar:</b>
							<% } %>
						</td>
					</tr>
				</table>
<%
				}
				Long codArefReferencia = Long.valueOf(mesReferencia);				
				int qtdePeriodosAnteriores;
				if ("true".equals(status)){
					qtdePeriodosAnteriores = 1;
				}
				else{
					qtdePeriodosAnteriores = Integer.valueOf(periodo).intValue();
				}
				
				if (!"".equals(Pagina.getParamStr(request, "periodo")) && !"true".equals(status)){
					qtdePeriodosAnteriores =  Integer.valueOf(Pagina.getParamStr(request, "periodo")).intValue();
				} else {
					qtdePeriodosAnteriores = 1;
				}
				
				/* Busca coleção com ciclos a serem considereados */
				Collection periodosConsideradosAgrupados = new ArrayList();
				
				Collection secretarias = new ArrayList();
				Collection periodosConsideradosListagem = new ArrayList();
				
				String codTipoAcompanhamentoEscolhido =	Pagina.getParamStr(request, "codTipoAcompanhamento");
				
				//ciclo referência
				//caso a referência escolhida tenha vindo da tela de filtros seta o código da referencia para receber
				if (mesReferencia != null && !mesReferencia.equals("")){
					codArefReferencia = Long.valueOf(mesReferencia);
				}
								
				if(codArefReferencia != null && codArefReferencia.intValue() > 0) {
				
					//Se o tipo de acompanhamento estiver configurada para separar por órgãos
					if (ehSeparadoPorOrgao){
						//Guarda a rafarência selecionada tanto se veio do filtro quanto se veio do combo da tela
						AcompReferenciaAref referenciaSelecionada = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, codArefReferencia);
						//Pega a lista de referências consideradas agrupadas por dia/mes/ano
						periodosConsideradosAgrupados = acompReferenciaDao.getPeriodosAgrupadosConsiderados(comboReferencia, referenciaSelecionada, qtdePeriodosAnteriores);
												
						//Completa a lista de ciclos de referência com outas referências de mesmo dia/mes/ano das referências consideradas
						periodosConsideradosListagem = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(periodosConsideradosAgrupados);
						
						//Recupera os órgãos da lista total de referências
						secretarias = acompReferenciaDao.getOrgaosReferencias(periodosConsideradosListagem);
					}
					else{
						 
						periodosConsideradosListagem = acompReferenciaDao.getPeriodosAnterioresOrdenado(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento), false);
						periodosConsideradosAgrupados = periodosConsideradosListagem;
						
						/* Busca coleção com todas as secretarias que possuem algum acompanhamento de referência cadastrado */
//						secretarias = acompReferenciaDao.getOrgaosComAcompanhamentosCriados(codTipoAcompanhamento);
					}
				}							
				/* seta na sessão coleções de periodos agrupados e total, para serem utilizadas no grafico */
				session.setAttribute("periodosConsideradosAgrupados", periodosConsideradosAgrupados);
				session.setAttribute("periodosConsideradosListagem", periodosConsideradosListagem);
				
				List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
				
				StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
		                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
				
				boolean primeiro = true;
				boolean ultimo = false;
						
				Iterator itSecretarias = secretarias.iterator();	
				AcompReferenciaAref referenciaOrgaoAtual = null;
				
				//se nao tiver nenhuma secretaria, passa logo secretaria == null
				if(!itSecretarias.hasNext()) {
					ultimo = true;
				}
				
				while(itSecretarias.hasNext() || ultimo) {
					/* Na primeira vez, atribui null à secretaria para imprimir informação de periodos EM MONITORAMENTO */
					OrgaoOrg secretaria = null;
					//Variável usada quando o Tipo de Acompanhamento estiver setado para "Separar por Orgao"
					String nomeOrgaoSepararPorOrgao = "";
					
					if(ultimo){
						secretaria = null;
						ultimo = false;
					}else{
						secretaria = (OrgaoOrg) itSecretarias.next();
						nomeOrgaoSepararPorOrgao = "_org" + secretaria.getCodOrg();
						if(!itSecretarias.hasNext()) {
							ultimo= true;	
						}
					}
					
					
									
					/* seta na sessão coleção de periodos, para ser utilizado no grafico */
//					if(secretaria == null)
//						session.setAttribute("periodosConsiderados", periodosConsideradosListagem);
//					else
//						session.setAttribute("periodosConsiderados" + secretaria.getCodOrg(), periodosConsideradosListagem);

					Long codIettPai = null;
								
					
					Object itens[] = null;
					codReferencia = Pagina.getParamStr(request, "codReferencia");
					
					if (codReferencia == null || codReferencia == "")
						codReferencia = mesReferencia;
					
					
					//referencia do combo de referencias (a referencia do combo pode nao ser a mesma referencia que esta sendo iterada baseada nos órgãos)
					//para cada órgão é uma referência diferente
					AcompReferenciaAref arefSelecionada = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codReferencia));	
					//guarda a lista de referencia que vao ser usadas para imprimir o lapis
					Collection listaReferenciasOrgaoAtualLapis = new ArrayList();
					//guarda a lista de referencia que vao ser usadas para imprimir os pareceres
					Collection listaReferenciasOrgaoAtualCarinha = new ArrayList();
					
					if(ehSeparadoPorOrgao ) {
                        // descobre a referencia que tem o mesmo dia,mes e ano da referencia do combo mas com orgao diferente
                        //a arefSelecionada é passada como parametro para pegar o dia, mes e ano 
                        referenciaOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(secretaria, arefSelecionada);
                        
                        //se existe uma referencia para o orgao atual, adiciona as duas listas
                        if(referenciaOrgaoAtual != null) {
                        	listaReferenciasOrgaoAtualLapis.add(referenciaOrgaoAtual);
                        	listaReferenciasOrgaoAtualCarinha.add(referenciaOrgaoAtual);
                        }	
                        AcompReferenciaAref arefPeriodo	= null;
                        
                        
                        //no caso de mais de um periodo e o periodo atual nao ter nenhum aref no orgao atual pq o orgao foi criado por causa dos outros periodos
                        Iterator itPeriodosOrgaoAtual =  periodosConsideradosAgrupados.iterator();
                        while(referenciaOrgaoAtual == null && itPeriodosOrgaoAtual.hasNext()) {
                        	arefPeriodo	= (AcompReferenciaAref) itPeriodosOrgaoAtual.next();
                        	referenciaOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(secretaria, arefPeriodo);
                        	
                        }
                        
                       
                		if(referenciaOrgaoAtual != null)
                			listaReferenciasOrgaoAtualCarinha.add(referenciaOrgaoAtual);
                		 
                		
                		//gera as duas lista de referencias para imprimir o parecer e o lapis
                		itPeriodosOrgaoAtual =  periodosConsideradosAgrupados.iterator();
    					while(itPeriodosOrgaoAtual.hasNext()) {
    						arefPeriodo	= (AcompReferenciaAref) itPeriodosOrgaoAtual.next();
    						AcompReferenciaAref refOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(secretaria, arefPeriodo);
    						if(refOrgaoAtual != null) { 
								if(!listaReferenciasOrgaoAtualCarinha.contains(refOrgaoAtual)) 
									listaReferenciasOrgaoAtualCarinha.add(refOrgaoAtual);
								
								if(!listaReferenciasOrgaoAtualLapis.contains(refOrgaoAtual)) 
									listaReferenciasOrgaoAtualLapis.add(refOrgaoAtual);	
    						} 
    					}
    			        
                    } else {
                        //se nao for separado por orgao, só vai existir uma referência. 
                        referenciaOrgaoAtual =  arefSelecionada;
                    }
					
					
					
					
				
					
					/***********************************************************/
					/*******************TIPOS DE FILTRO*************************/
					/***********************************************************/
					//1) GERAL
					//2) PESQUISA DA LISTAGEM DE MONITORAMENTO
					//3) MINHA VISAO
					//4) MINHAS PENDENCIAS
					//5) PERSONALIZADO 
					
			
					/********************PESQUISA DA LISTAGEM DE MONITORAMENTO*************************/	
					/********************FILTRO : PERSONALIZADO*************************/	
					if (Pagina.getParamStr(request, "hidAcao").equals("pesquisarFiltros")) {
					%>
						<!-- Guarda os campos hidden da consulta feita no personalizado -->
						<%@ include file="camposHiddenPesquisa.jsp"%>
												
						<!---------------------------------------------------------------->	
					<% 	

						String msg = null;
				
						String[] cores  = request.getParameterValues("cor");
				        Collection lCoresSelecionadas = new ArrayList();
				       
				        if (cores != null) {
				        	for (int i = 0; i < cores.length; i++) {
				            	Cor cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(cores[i]));
				                lCoresSelecionadas.add(cor);
				        	}
						}
		
						String[] tipoFuncoesAcompanhamento  = request.getParameterValues("tipoFuncAcompTpfa");
									Collection lFuncoesAcompanhamentoSelecionadas = new ArrayList();
							
						if (tipoFuncoesAcompanhamento != null) {
				        	for (int i = 0; i < tipoFuncoesAcompanhamento.length; i++) {
				            	TipoFuncAcompTpfa tipoFuncAcompTpfa = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(tipoFuncoesAcompanhamento[i]));
				                lFuncoesAcompanhamentoSelecionadas.add(tipoFuncAcompTpfa);
				            }
				        }

						List niveisUsuarioFiltro = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
						Iterator itNiveisUsuFiltro = niveisUsuario.iterator();
						StringBuffer strAuxFiltro = new StringBuffer("");
						
						while(itNiveisUsuFiltro.hasNext()) {
							UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsuFiltro.next();
							strAuxFiltro.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
							strAuxFiltro.append(":");
						}
						String strNiveisPlanejamentoUsuarioFiltro = strAux.toString();
						
						itens =	acompReferenciaItemDao.getItensAcompanhamentoFiltroItens(
							new Long(codTipoAcompanhamentoEscolhido),
							periodosConsideradosListagem,
							lCoresSelecionadas,
							lFuncoesAcompanhamentoSelecionadas,
							usuario,
							seguranca.getGruposAcesso(),
							listNiveis,
							isSemInformacaoNivelPlanejamento,
							secretaria,
							request 
							);

										
					
					/********************FILTRO: MINHA VISAO*************************/	 
					} else if (Pagina.getParamStr(request, "hidAcao").equals("pesquisarMinhaVisao")) {
										
											
						itens = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
								periodosConsideradosListagem, listNiveis, secretaria, 
								usuario, seguranca.getGruposAcesso(), 
								Long.valueOf(codTipoAcompanhamento), codIettPai,
								isSemInformacaoNivelPlanejamento, null, null, -2, nuPagina, etiquetas, true);
						
					} 
					
					
					/********************FILTRO: MINHAS PENDENCIAS*************************/
					else if (Pagina.getParamStr(request, "hidAcao").equals("pesquisarMinhasPendencias")) {
						
						itens = acompReferenciaItemDao.getItensAcompanhamentoComPendencias(
											periodosConsideradosListagem, listNiveis, secretaria, 
											usuario, seguranca.getGruposAcesso(),
											tipoAcompanhamento, codIettPai, arefSelecionada, tpfaOrdenadosPorEstrutura,
											isSemInformacaoNivelPlanejamento, null, null, -2, nuPagina);
											
			
					}
				
					
					/*******************************FILTRO: GERAL ******************************/
					else {
					
						itens = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
											periodosConsideradosListagem, listNiveis, secretaria, 
											usuario, seguranca.getGruposAcesso(), 
											Long.valueOf(codTipoAcompanhamento), codIettPai,
											isSemInformacaoNivelPlanejamento, null, null, -2, nuPagina, etiquetas, false);
	

					}
					 
					 
				 	/********************FILTRO: PESQUISAS SALVAS*************************/
 					//Filtra a consulta de itens para exibir apenas os que foram salvos pelo filtro
 					if(Pagina.getParamStr(request, "hidAcao").equals("pesquisaSalva")){
						
 						int codPesquisa = Pagina.getParamInt(request, "formaVisualizacao");
						PesquisaDao pesquisaDao = new PesquisaDao(request);
						Pesquisa pesquisa = (Pesquisa) pesquisaDao.buscar(Pesquisa.class,Long.valueOf(codPesquisa));
						List itensPraPesquisa = new ArrayList();
					
						boolean existeNivelPlanejamento = linkDao.estaConfiguradoLink("NIVEL_PLANEJAMENTO");
						
						
						//recupera os itens que foram salvos na pesquisa filtrados pelo nivel de planejamento
						itens = acompReferenciaItemDao.getItensAcompanhamentoPesquisa(
								periodosConsideradosListagem, listNiveis, orgaoResponsavel, 
								usuario, seguranca.getGruposAcesso(), 
								Long.valueOf(codTipoAcompanhamento), codIettPai,
								isSemInformacaoNivelPlanejamento,-2, nuPagina, pesquisa.getPesquisaIetts(), secretaria);
					
		
					}
 					/**********************************************************************/	
					
					
 					/* Recupera lista de itens para os periodos considerados por orgão responsavel */
					List itensAcompanhamentos = new ArrayList((Collection)itens[0]);
					List itensGeral = new ArrayList((Collection)itens[1]);
					
					//Iterator itItensAcompanhamentos = itensAcompanhamentos.iterator();

					/********************IMPRESSAO DOS ITENS DE ACOMPANHAMENTO*************************/	
					/* Se for encontrado algum periodo, imprime lista de itens */
		
					if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
%>
				
				<div id="subconteudo" align="right">
					<%
					List links = linkDao.listar(Link.class, new String[]{"codLink", "asc"});
					Iterator itLinks = links.iterator();
										
					if(secretaria == null){
					%>
						<input type="hidden" name="codSecretaria" value="0">												
					<%} else { %>						
						<input type="hidden" name="codSecretaria" value="<%=secretaria.getCodOrg()%>">						

					<%}
					
					if(primeiro && !"true".equals(status)) {%>
					<util:barraLinks					
						_pathEcar="<%= _pathEcar%>"
						verificaMonitoramento="<%=new Boolean(true) %>"
						request="<%=request%>"
						/>
						
					<%} %>	
					<!-- O hidden  numeroColunasTabela é usado na exibição dos filhos no Ajax(botão carregando)-->
					<input type="hidden" name="numeroColunasTabela" id="numeroColunasTabela" value="<%=	periodosConsideradosAgrupados.size() + 7%>">
					
					<table id="tabelaItemEstrutura<%=nomeOrgaoSepararPorOrgao %>" border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- TÍTULO DA TABELA -->
						<tr class="linha_titulo">
							<td colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<%
								if(tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals(Dominios.SIM)) {
									if(secretaria != null) {
								 		out.println(secretaria.getDescricaoOrg());
								 	} else {
								 		out.println(configuracao.getLabelAgrupamentoItensSemOrgao());	
								 	}
								}
							 	 
								%>
							</td>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- CABEÇALHO DA TABELA -->
						<tr class="linha_subtitulo">
							<td width="1%"></td>
							<td width="50%" colspan="2">Acompanhamentos</td>
							<!-- o órgão escolhido vem da tela de filtro -->
							<td width="10%" align="center" nowrap><%=strLabelOrgao%></td>
							<%if(!"true".equals(status)){ %>
								<td width="5%" align="center" colspan="2">Ação</td>	
							<%} else {%>
								<td width="5%" align="center" colspan="2"></td>
							<%}%>
							
							<%
								/* Imprime o nome dos ciclos */
								Iterator it = periodosConsideradosAgrupados.iterator();
								double tamanhoColuna = 38 / periodosConsideradosAgrupados.size();
								while(it.hasNext()){
									AcompReferenciaAref acompanhamento = (AcompReferenciaAref) it.next();
									int tamanhoListaMesmaReferencia = acompReferenciaDao.getListaMesmaReferenciaDiaMesAnoCount(acompanhamento);
									
									String estilo = "";
									
									String nomeReferencia = "";
									
									if(ehSeparadoPorOrgao && tamanhoListaMesmaReferencia > 1) {
										nomeReferencia = acompanhamento.getDiaAref() + "/" + acompanhamento.getMesAref() + "/" + acompanhamento.getAnoAref();
									} else {
										nomeReferencia = acompanhamento.getNomeAref();
									}
									
									// se nao for o mes de referencia selecionado, o periodo deve ficar com a cor escura
									if(!"".equals(mesReferencia) && !mesReferencia.equals(acompanhamento.getCodAref().toString()))
										estilo = "class=\"corSelecao\"";
							%>
							<td id="selecionado" <%=estilo%> align="center" width="<%=tamanhoColuna%>%">
							<%=nomeReferencia%> 
							</td>
							<%							
								}					
								if("true".equals(status)){
							%>
							<td align="center">
								Todos
								<% if(secretaria != null){ %>
									<input type="checkbox" class="form_check_radio" value="todos" id="<%=secretaria.getCodOrg()%>"	onClick="checkAll(<%=secretaria.getCodOrg()%>)">
									<input type="hidden" id="ComboClicado<%=secretaria.getCodOrg()%>" value="">
								<%} else {%>
									<input type="checkbox" class="form_check_radio" value="todos" id="<%=ConstantesECAR.ZERO%>" onClick="checkAll(<%=ConstantesECAR.ZERO%>)">
									<input type="hidden" id="ComboClicado<%=ConstantesECAR.ZERO%>" value="">
								<%}%>
							</td>
							<%
								}
								codReferencia = Pagina.getParamStr(request, "codReferencia");
								if(codReferencia.equals("")){
									codReferencia = mesReferencia;
								}								
							%>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>

						<!-- Aqui começam a ser imprimidos os itens de acompanhamento -->
						<!-- DADOS DA TABELA -->
						<%
								boolean acaoLapis = true; // mudar de acordo com a regra de exibicao de acao
								Map dadosGrafico = new HashMap();
								List itensSessao = new ArrayList();
								List itensSessaoVisualizar = new ArrayList();
								
								//seta a sessão que será utilizada pelo servlet que gera os filhos da árvore 											
								if(session.getAttribute("periodosConsiderados") != null)
									session.removeAttribute("periodosConsiderados");
								session.setAttribute("periodosConsiderados", periodosConsideradosAgrupados);
								
								
								// esta coleção de itens é utilizada para montar o ajax no arquivo GeraFilhosIettServlet
								String colecaoItens = "";
								if(secretaria != null)
									colecaoItens = "colecaoItens_org" +  secretaria.getCodOrg().toString();
								else 	
									colecaoItens = "colecaoItens";
							
						
								if(session.getAttribute(colecaoItens) != null)
										session.removeAttribute(colecaoItens);
								session.setAttribute(colecaoItens, itensAcompanhamentos);
										
								mapItensReferencia.put(referenciaOrgaoAtual, itensAcompanhamentos);
								

								/* Itera pelos itens  - melhoria na quantidade de consultas - DATASUS*/
								List<Long> listaCodigosIett = new ArrayList<Long>();
								List<Long> listaParaFuncoes = new ArrayList<Long>();
								Iterator itensIn = itensAcompanhamentos.iterator();
								while(itensIn.hasNext()){
									
									AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itensIn.next();
									ItemEstruturaIett item = aeIett.getItem();
									listaCodigosIett.add(item.getCodIett().longValue());
									listaParaFuncoes.add(item.getEstruturaEtt().getCodEtt());
								}
								AcompReferenciaItemDao referenciaItemDao = new AcompReferenciaItemDao(request);
								List<AcompReferenciaItemAri> listaAcompReferenciaItemAri =  referenciaItemDao.getListaAcompReferenciaItemByItemEstruturaIett(arefSelecionada.getCodAref(),listaCodigosIett);
								
								//public List<AcompReferenciaItemAri> getListaAcompReferenciaItemByItemEstruturaIettOrgao(OrgaoOrg orgao,
										//AcompReferenciaAref acompanhamento, List<Long> listaCodigosIett)
								List<AcompReferenciaItemAri> listaAcompReferenciaItemAriOrgao = 
									referenciaItemDao.getListaAcompReferenciaItemByItemEstruturaIettOrgao(secretaria,arefSelecionada,listaCodigosIett);								
								
		
								EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request); 
								List<EstruturaFuncaoEttf> listaEstruturaFuncao = estruturaFuncaoDao.getListaDeFuncoes(listaParaFuncoes);
								
								//List<ItemEstrutMarcadorIettm> getListaItemEstrutMarcadorIettm(List<Long> listaCodigoitem, UsuarioUsu usuario)
								List<ItemEstrutMarcadorIettm> listaIettms = marcadorDao.getListaItemEstrutMarcadorIettm(listaCodigosIett,usuario);
								List<Long> listaCodigoIettms = new ArrayList<Long>();
								for (ItemEstrutMarcadorIettm itemIettm : listaIettms) {
									listaCodigoIettms.add(itemIettm.getItemEstruturaIett().getCodIett());
								}
								
								List listAbasComAcesso = abaDao.getListaAbasComAcesso(tipoAcompanhamento, seguranca.getGruposAcesso(), 1);
								
								Map mapAcaoGeral = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsideradosAgrupados,listaCodigosIett);
								Map mapAcaoSeparaOrgao = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsideradosAgrupados, listaReferenciasOrgaoAtualLapis,listaCodigosIett);
								
								List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
								
								/* Itera pelos itens */
								Iterator itItens = itensAcompanhamentos.iterator();
		
								while(itItens.hasNext()){
									
									if(request.getAttribute("itensSessao")!=null){
										itensSessao = (List)request.getAttribute("itensSessao");
										itensSessaoVisualizar = (List)request.getAttribute("itensSessaoVisualizar");
									}
									
									AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
									
									ItemEstruturaIett item = aeIett.getItem();
									ariDao = new AcompReferenciaItemDao(request);
									
									AcompReferenciaItemAri acompAri = null;
									AcompReferenciaItemAri acompArii = null;
									
									if (tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")){
									
										// Vai buscar o acompReferenciaItem do item corrente, do órgão corrente e do mesmo dia/mes/ano da referência selecionada
										//acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIettOrgao(secretaria, arefSelecionada,item);
										acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIettOrgao(secretaria,listaAcompReferenciaItemAri,item,arefSelecionada);
									}
									else{
										//acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionada,item);
										acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIett(listaAcompReferenciaItemAri,item,arefSelecionada.getCodAref());
									}
									
									String linkAbrirIettComeco = "";
									String linkAbrirIettFim = "";
									String linkAbrirArvoreIettComeco = "";
									String linkAbrirArvoreIettFim = "";
																		
									if("posicaoGeral".equals(idPagina)) {
										//Verificar para mostrar itens até o menor nivel configurado nas configurações gerais...
										//Se menorNivel == -1, não foi informado nehuma estrutura, então ignora esta validação
										int nivelEtt = item.getNivelIett().intValue();
										if(menorNivel != -1 && nivelEtt > menorNivel) {
											continue;
										}
									}
									
									
							
			
									if(acompAri != null && tipoAcompanhamento != null) {
										String paginaAba = "";	
										String paginaAbaVisualizacao = "";
										boolean possuiFuncaoConfigurada = false;
										Aba proximaAba = null;
							        	// valor tipo 1 => registro 2=> visualização
							        	//TODO - Ponto de sujestão p/ melhoria(DATASUS) foi removido para fora do laço de repetição para não 
							        	//onerar a listatem de itens.
							            //List listAbasComAcesso = abaDao.getListaAbasComAcesso(tipoAcompanhamento, seguranca.getGruposAcesso(), 1);
							        	
							        	
							            if (listAbasComAcesso!=null && !listAbasComAcesso.isEmpty()) {
							            	Aba abaDirecionaRegistro = (Aba) listAbasComAcesso.get(0);
							            	String nomeAba = abaDirecionaRegistro.getNomeAba();
							            	
							    	        if(abaDirecionaRegistro.getFuncaoFun()!= null){        	
							    	        	if(acompAri.getItemEstruturaIett().getEstruturaEtt() != null){
							    	        		
							    	        		List<EstruturaFuncaoEttf> lista = estruturaFuncaoDao.getListaDeFuncoes(acompAri,listaEstruturaFuncao);
							    	        		for(EstruturaFuncaoEttf funcao: lista){
							    	        			if(abaDirecionaRegistro.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
								    	        			possuiFuncaoConfigurada = true;
								    	        			break;
								    	        		}
							    	        		}
							    	        		
							    	        		/*
							    	        		
							    	        		//List<EstruturaFuncaoEttf> getListaDeFuncoes(AcompReferenciaItemAri acompAri)
							    	        		Set listaFuncoes = acompAri.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
								    	        	/////
								    	        	
								    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
								    	        	while(itListaFuncoes.hasNext()){
								    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
								    	        		if(abaDirecionaRegistro.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
								    	        			possuiFuncaoConfigurada = true;
								    	        			break;
								    	        		}
								    	        	}*/
							    	        	} else{
							    	        		possuiFuncaoConfigurada = true;
							    	        	}
							    	        } else{
							    	        	possuiFuncaoConfigurada = true;
							    	        }
							    	        
							    	        
							            }
							        	
							           if(!possuiFuncaoConfigurada){
								            List listAbasComAcessoTotal = abaDao.getListaAbasComAcesso(tipoAcompanhamento, seguranca.getGruposAcesso());
								            						            
								            
								            Iterator itAbas = listAbasComAcessoTotal.iterator();
								    	    while (itAbas.hasNext()) {
								   	    	 Aba aba = (Aba) itAbas.next();
								   	    	 
								   	    	 boolean possuiAba = false;
								   	    	 if (acompAri != null){
								   	    	        if(aba.getFuncaoFun()!= null){        								   	    	        	
								   	    	        	List<EstruturaFuncaoEttf> lista = estruturaFuncaoDao.getListaDeFuncoes(acompAri,listaEstruturaFuncao);
								    	        		for(EstruturaFuncaoEttf funcao: lista){
								    	        			if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
								   	    	        			possuiFuncaoConfigurada = true;
								   	    	        			proximaAba = aba; 
								   	    	        			break;
								   	    	        		}
								    	        		}
								   	    	        	/*
								   	    	        	Set listaFuncoes = acompAri.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
								   	    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
								   	    	        	while(itListaFuncoes.hasNext()){
								   	    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
								   	    	        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
								   	    	        			possuiFuncaoConfigurada = true;
								   	    	        			proximaAba = aba; 
								   	    	        			break;
								   	    	        		}
								   	    	        	}*/
								   	    	        } else{
								   	    	        	possuiFuncaoConfigurada = true;
								   	    	        	proximaAba = aba;
								   	    	        }
								   	    	 }
								   	    	 if(possuiFuncaoConfigurada){
								   	    		 break;
								   	    	 }
								    	    }
								    	    
								    	    if(proximaAba != null){
								    	    								    	    
								    	    	
												/***************ABA DADOS GERAIS*************************/
												if ("DADOS_GERAIS".equals(proximaAba.getNomeAba())) {
													paginaAba = "acompanhamento/dadosGerais/frm_con.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/dadosGerais/dadosGerais.jsp";
												/***************ABA EVENTOS*****************************/
												} else if ("EVENTOS".equals(proximaAba.getNomeAba())) {
													paginaAba = "acompanhamento/realizacoes/eventos.jsp";	
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/eventos/eventos.jsp";													
												/***************ABA PONTOS CRITICOS*********************/	
												} else if ("PONTOS_CRITICOS".equals(proximaAba.getNomeAba())) {
													paginaAba = "acompanhamento/restricoes/pontosCriticos.jsp";
													paginaAbaVisualizacao = "acompanhamento/restricoes/pontosCriticos.jsp?tela=V";
												/***************ABA SITUACAO PONTOS CRITICOS***********/	
												} else if(proximaAba.getNomeAba().equals("SITUACAO_PONTOS_CRITICOS")) {
													paginaAba =  "acompanhamento/situacaoDatas/situacaoDatas.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoDatas/situacaoDatas.jsp";
												/***************ABA GALERIA***************************/	
												} else if ("GALERIA".equals(proximaAba.getNomeAba())) {
													paginaAba =  "acompanhamento/galeria/galeria.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/galeria/galeria.jsp";
												/***************ABA FINANCEIRO************************/
												} else if ("FINANCEIRO".equals(proximaAba.getNomeAba())) {
													paginaAba =  "acompanhamento/financeiro/financeiro.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/financeiro/financeiro.jsp";
												/***************ABA RESUMO***************************/
												} else if("RELACAO".equals(proximaAba.getNomeAba())){
													paginaAba =  "acompanhamento/resumo/detalharItem.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
									            /***************ABA METAS E INDICADORES***************/	
									            } else if ("REL_FISICO_IND_RESULTADO".equals(proximaAba.getNomeAba())) {
									            	paginaAba = "acompanhamento/resultado/indicadoresResultado.jsp";	
									            	paginaAbaVisualizacao = "acompanhamento/resultado/indicadoresResultado.jsp?tela=V";
												/***************ABA SITUACAO E INDICADORES*************/	
									            } else if (proximaAba.getNomeAba().equals("SITUACAO_INDICADORES")) {
									            	paginaAba = "acompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
									            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
									            /***************ABA ETAPA*****************************/		
									            }	else if ("ETAPA".equals(proximaAba.getNomeAba())) {
									            	paginaAba = "acompanhamento/etapa/etapas.jsp";
									            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/etapas/etapas.jsp";
												/***************ABA DATAS LIMITES*********************/	
												} else if ("DATAS_LIMITES".equals(proximaAba.getNomeAba())) {
													paginaAba = "acompanhamento/datasLimites/datasLimites.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/datasLimites/datasLimites.jsp";
												/***************ABA SITUACAO***************************/
												} else if("SITUACAO".equals(proximaAba.getNomeAba())){
													paginaAba = "acompanhamento/situacao/relatorios.jsp";
													paginaAbaVisualizacao = "acompanhamento/situacao/relatorios.jsp?tela=V";
												/***************ABA GRAFICO DE GANTT*********************/
												} else if("GRAFICO_DE_GANTT".equals(proximaAba.getNomeAba())){
													paginaAba = "acompanhamento/graficoGantt/graficoGantt.jsp";
													paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/graficoGantt/graficoGantt.jsp";
												/***************ABA RELATORIO ***************************/
												}	else if("RELATORIO".equals(proximaAba.getNomeAba())) {
													paginaAba= "acompanhamento/relatorios/relatorioImpresso.jsp?tela=R";
													paginaAbaVisualizacao = "acompanhamento/relatorios/relatorioImpresso.jsp?tela=V";
								        		}else {
								                	
								                	//Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje é uma tela fixa.
								                	paginaAba = "acompanhamento/resumo/detalharItem.jsp";
								                	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
								                }
																			    	    
											// adiciona tipo padrao de exibicao
											if (paginaAba!=null) {
												String tipoPadraoAba = "1";
												paginaAba += (paginaAba.indexOf("&")>0 || paginaAba.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoAba:"?tipoPadraoExibicaoAba=" + tipoPadraoAba);												
							           		}	
							            }
							           }
							            
							            if(!possuiFuncaoConfigurada){
							            	String enderecoAbaSemFuncao = "acompanhamento/acessoSemFuncao.jsp";
							            	String tipoPadraoExibicaoAba = "1"; 
							            	enderecoAbaSemFuncao += (enderecoAbaSemFuncao.indexOf("&")>0 || enderecoAbaSemFuncao.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAba:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAba);
							            	
											linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" 
												+ status
												+ "','"+ acompAri.getCodAri().toString() 
												+ "','"+ periodo 
												+ "','"+ tipoAcompanhamento.getCodTa().toString()
												+ "','"+ acompAri.getAcompReferenciaAref().getCodAref().toString()
												+ "','"+ enderecoAbaSemFuncao + "')\" title=\"Editar\">";
												
												
											enderecoAbaVisualizacaoSemFuncao = "acompanhamento/acessoSemFuncao.jsp";
											String tipoPadraoExibicaoAbaVisualizacao = "2"; 
											enderecoAbaVisualizacaoSemFuncao += (enderecoAbaVisualizacaoSemFuncao.indexOf("&")>0 || enderecoAbaVisualizacaoSemFuncao.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaVisualizacao:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaVisualizacao);

							    			
							        	} else{
										
							        	enderecoAbaVisualizacaoSemFuncao = "";
							        		
							        	if(paginaAba != null && !"".equals(paginaAba)){
								        	linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" 
												+ status
												+ "','"+ acompAri.getCodAri().toString() 
												+ "','"+ periodo 
												+ "','"+ tipoAcompanhamento.getCodTa().toString()
												+ "','"+ acompAri.getAcompReferenciaAref().getCodAref().toString()
												+ "','"+ paginaAba + "')\" title=\"Editar\">";
												
											if(paginaAbaVisualizacao != null && !"".equals(paginaAbaVisualizacao)){
												enderecoAbaVisualizacaoSemFuncao = paginaAbaVisualizacao;
												String tipoPadraoExibicaoAbaVisualizacao = "2"; 
												enderecoAbaVisualizacaoSemFuncao += (enderecoAbaVisualizacaoSemFuncao.indexOf("&")>0 || enderecoAbaVisualizacaoSemFuncao.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaVisualizacao:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaVisualizacao);
											}
							        	} else{
							        	linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" 
																	+ status
																	+ "','"+ acompAri.getCodAri().toString() 
																	+ "','"+ periodo 
																	+ "','"+ tipoAcompanhamento.getCodTa().toString()
																	+ "','"+ acompAri.getAcompReferenciaAref().getCodAref().toString()
																	+ "','"+ enderecoAba + "')\" title=\"Editar\">";
							        		}
							        	}
																	
										linkAbrirIettFim = "</a>";
									}
																										
									if(menorNivel != -1){
										List filhos = itemDao.recuperaDescendentesImediatos(item, itensAcompanhamentos);
										if(filhos != null && filhos.size() > 0){
											Iterator itFilhos = filhos.iterator();
											ItemEstruturaIett filho = ((AtributoEstruturaListagemItens) itFilhos.next()).getItem();
											if(!idPagina.equals("proximosItens") && (filho.getNivelIett().intValue() > menorNivel)) {
												
												String codReferenciaAjax = "";
												if(referenciaOrgaoAtual != null) {
													codReferenciaAjax = referenciaOrgaoAtual.getCodAref().toString();
												} 
												
												//criação do link para abrir a arvore de itens do item														
												if(secretaria != null) {
													linkAbrirArvoreIettComeco = "<a href=\"javascript:carregarFilhosNo('" + item.getCodIett()+ "','"
																							+ secretaria.getCodOrg()+"','"+ enderecoAba +"','" 
																							+ periodo +"','"
																							+ codReferenciaAjax+"',"
																							+ situacaoEstaConfigurada +",'" 
																							+ nomeAbaVisualizacao 
																							+"')\">";
												 } else { 
													linkAbrirArvoreIettComeco = "<a href=\"javascript:carregarFilhosNo('" + item.getCodIett()+ "','','"
																													+ enderecoAba + "','"
																													+ periodo +"','"
																													+ codReferenciaAjax+"',"
																													+ situacaoEstaConfigurada +",'" 
																													+ nomeAbaVisualizacao 
																													+"')\">";
												}
												linkAbrirArvoreIettFim = "</a>";
											}
										}
									}
																			
		%>
						<!-- Seta o input hidden que será utilizado na função javascript expandirContrairArvore -->
						<input type="hidden" id="<%="inputHiddenFilhos_iett" + item.getCodIett() + nomeOrgaoSepararPorOrgao%>" value="0">
						
						<!-- começa a imprimir a linha do item -->
						<tr class="cor_sim"
							id="<%="iett" + item.getCodIett() + nomeOrgaoSepararPorOrgao%>"  
							onmouseover="javascript:destacaLinha(this,'over','')"
							onmouseout="javascript: destacaLinha(this,'out','cor_sim')"							
							bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
							<!-- coluna imprime se tiver algum marcador (anotação para o item) -->
							<td>
		<%							if(!"true".equals(status)){
										//ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item, usuario);
										ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item,listaCodigoIettms, usuario);
										if(marcador != null){
		%>
								<a
									href="javascript:abrirPopUpListaAnotacao(<%=item.getCodIett()%>)" title="Lembrete">
		<%								if(marcador.getCor() != null){ %> <img
											src='<%=_pathEcar%>/images/relAcomp/<%=marcador.getCor().getNomeCor().toLowerCase()%>_inf.png'
											border='0' alt=""> <%
										} else { %> <img
											src='<%=_pathEcar%>/images/relAcomp/branco_inf.png' border='0'
											alt=""> <%
										} %> </a>
		<%							}									
								} %>
							</td>
							<!-- termina de imprimir a coluna do marcador -->

							<!-- começa a imprimir a coluna que faz a identação de acordo com o nivel do item -->
							<td>
								<table>
									<tr bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
										<td nowrap>
											&nbsp;
		<%
								/* Identação pelo nível do item */
								int nivel = item.getNivelIett().intValue();
								for(int i = 1; i < nivel;i++) {
		%>							<img src="<%=request.getContextPath()%>/images/pixel.gif"
										width="22" height="9" alt="">
		<%
								}
		%>
								</td>
								<td valign="top" id="iconeExpandirIett<%=item.getCodIett() + nomeOrgaoSepararPorOrgao%>"> 
										
		<%						
								// Verificar se a imagem é de "mais"(+) ou "quadrado em branco"							
								String caminhoImagem = "";
								if(linkAbrirArvoreIettFim.equals("")) {
									//Quando nao puder expandir o item, a imagem mostrada um quadrado em branco
									caminhoImagem = "/images/square.gif";
								} else {
									//Quando puder expandir o item, a imagem mostrada sera a de +
									caminhoImagem = "/images/collapsed_button.gif";
								}								
				
		%>								
										<%=linkAbrirArvoreIettComeco%><img id="imagemMaisMenosIett<%=item.getCodIett() + nomeOrgaoSepararPorOrgao%>" src="<%=_pathEcar%><%=caminhoImagem%>" alt="" border="0"><%=linkAbrirArvoreIettFim%>
										</td>
										<td title="<%=item.getEstruturaEtt().getNomeEtt().trim()%>">
											<font color="<%=item.getEstruturaEtt().getCodCor4Ett()%>">
											<%
											String nomeIett = "".equals(aeIett.getDescricao()) ? "[Não Informado]" : aeIett.getDescricao();

											if (acompAri != null){												if(enderecoAbaVisualizacaoSemFuncao != null && !"".equals(enderecoAbaVisualizacaoSemFuncao)){
%>
												<a href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacaoSemFuncao%>', <%=acompAri.getCodAri()%>, <%=acompAri.getItemEstruturaIett().getCodIett()%>, 
																							<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">											 
												<%=nomeIett%>
												</a>
<%	
												} else{
													%>
												<a href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacao%>', <%=acompAri.getCodAri()%>, <%=acompAri.getItemEstruturaIett().getCodIett()%>, 
																							<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">											 
												<%=nomeIett%>
												</a>											
																						
												<%} 
											}
											else{%>
												<%=nomeIett%>
											<% }
												%>
											</font>
										</td>
									</tr>
								</table>
							</td>
							<!-- termina de imprimir a coluna que faz a identação de acordo com o nivel do item -->

							<!-- começa a imprimir as imagens para NivelPlanejamento -->
							<td nowrap>
								<%
									//TODO - Ponto de sujestão p/ melhoria
									Iterator itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(item).iterator();
									int contNivel = 0;
									while(itNiveis.hasNext()){
										NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean) itNiveis.next();
										out.print(imagemNivelPlanejamento.geraHtmlImg(request));
									}
								%>
							</td>
							<!-- termina de imprimir as imagens para NivelPlanejamento -->

							<!-- começa a imprimir a coluna com o nome do órgão responsável pelo item -->
							<td align="center">
								<%
									if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
										if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
											out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
										else
											out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
									} //else {
										/* Se não possuir orgao procura orgao do pai */
										/*ItemEstruturaIett itemAux = item;
										while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
											itemAux = itemAux.getItemEstruturaIett();
										}
										if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
											if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
												out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
											else
												out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
										}*/
									//}
								%>
							</td>
							<!-- termina de imprimir a coluna com o nome do órgão responsável pelo item -->
							<td></td>
							<!-- começa a imprimir a coluna de anotação 
							<td>
								<% if(!"true".equals(status)){ %>
								<a style="display: none; " href="javascript:abrirPopUpInserirAnotacao(<%=item.getCodIett()%>)"
									title="Inserir anotação"> <img
									src="<%=_pathEcar%>/images/relAcomp/icon_anotacoes.png"
									border="0" alt=""> </a>
								<% } %>
							</td>
							-->
							<!-- termina de imprimir a coluna de anotação -->
							
							<!-- começa a imprimir a coluna de ação -->
							
							<td align="center">
							<% 
							
							//INÍCIO DA LÓGICA DE PERMISSÃO DE REGISTRO DE PARECER
							boolean usuarioLogadoEmiteParecer = false;
							boolean permissaoLapis = false;
							
							if(permissaoAdministradorAcompanhamento) {
								if(ehSeparadoPorOrgao)	
									//TODO - Ponto de sujestão p/ melhoria
									permissaoLapis = estruturaAcessoDao.temPermissoesAcessoAcompPorOrgao(tipoAcompanhamento, seguranca.getGruposAcesso(), secretaria, usuario);
								else 
									permissaoLapis = true;
							}																				
																				
							//Dao do objeto principal(ItemEstUsutpfuac) onde veremos a permissão do usuário/grupo 
							ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
							//TODO
							//Esses ciclos considerados são os agrupados 
							Iterator itPeriodosAcao = periodosConsideradosAgrupados.iterator();
							
							//Retorna um AREF na chave do Map e um ARI referente ao item no valor do MAP.
							Map mapAcao = null;
							if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
								//TODO - Ponto de sujestão p/ melhoria
								//mapAcao = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsideradosAgrupados, listaReferenciasOrgaoAtualLapis, item);
								mapAcao = mapAcaoSeparaOrgao;
						    } else {
						    	//TODO - Ponto de sujestão p/ melhoria
						    	//mapAcao = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsideradosAgrupados, item);
						    	mapAcao = mapAcaoGeral;
						    	
						    }
				
							if(itPeriodosAcao.hasNext()) {
								//Pega só o ciclo selecionado (Aref), que é o primeiro
								acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
								
								//Verifica se o Map possui conteúdo.
								if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia.toString()+item)) {
									
								
									AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia.toString()+item);
									
									//Pega os Arels do Ari selecionado 
									List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ariAcao, tpfaOrdenadosPorEstrutura);
									Iterator itRelatorios = relatorios.iterator();
									
									//Itera nos relatórios(Arels).
									while(itRelatorios.hasNext()){												
										AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
										
										//TODO - Ponto de sujestão p/ melhoria
										//Usuário ou Grupo de acesso cadastrado em uma determinada função de acompanhamento de um item. 
										ItemEstUsutpfuacIettutfa itemEstUsu = itemEstUsuDao.buscarOtimizada(item.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
										 
										//Verifica se a permissão é de grupo ou usuário
										if(itemEstUsu!=null) {
											
											//Verifica se a permissão é de usuário, se for compara o usuário logado com o usuário do arel neste tipo de acompanhamento. 
											if (itemEstUsu.getUsuarioUsu() != null) {
												usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
											} else if (itemEstUsu.getSisAtributoSatb() != null) {//Verifica se a permissão é de grupo, se for busca os usuário do grupo e verifica se o usuário logado esta nesta lista de usuários. Logo concluímos que o usuário faz parte do grupo de acesso analisado.    
												if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
													Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();//A instrução: "itemEstUsu.getSisAtributoSatb()", desta linha quer dizer: obtem o grupo de acesso do usuário. Depois obtem a lista de usuários deste grupo de acesso.  
													while (itUsuarios.hasNext()) {//Itera na lista de usuários do grupo.
														UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
														if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){ //verifica se o usuário logado está na lista dos usuários do grupo de acesso.
															usuarioLogadoEmiteParecer = true;
															break;
														}
													}
												}
											}
										}	
										if(usuarioLogadoEmiteParecer==true)
											break;											
									}//fecha while relatórios																
								}
							}

							// Não coloca o lápis para o primeiro nível da estrutura 
							if((usuarioLogadoEmiteParecer || permissaoLapis) && item.getNivelIett().intValue()>0 && acompAri != null && !"true".equals(status)) { %>
								<!-- ADICIONAR A IMAGEM DO LÁPIS -->
								<%=linkAbrirIettComeco%><img src="<%=_pathEcar%>/images/icon_alterar.png" border="0" alt=""></a>  
							<%} %>
							</td>
							<!-- termina de imprimir a coluna de ação -->
							
							<!-- começa a imprimir os pareceres -->
							<%		Iterator itPeriodos =  periodosConsideradosAgrupados.iterator();
							
									Map map = null;
									
									//map utilizado para recuperar o Ari
									if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
										map = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsideradosAgrupados, listaReferenciasOrgaoAtualCarinha,  item);
								    } else {
								    	//map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsideradosAgrupados, item);
								    	map = mapAcaoGeral;
								    }
									
									while(itPeriodos.hasNext()) { 
										acompReferencia = (AcompReferenciaAref) itPeriodos.next(); 
										if(map.isEmpty()) {
							%>
							<td align="center">&nbsp;</td>
							<%										
										} else if(!map.containsKey(acompReferencia.toString()+item)) {
										//} else if(!map.containsKey(acompReferencia)) {
							%>
							<td align="center" valign="middle">
								<p title="Não foi solicitado acompanhamento">N/A</p>
							</td>
							<%
										} else {
											flag = "acompanhado"; 
											//AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);
											//ari = (AcompReferenciaItemAri) map.get(acompReferencia);
											ari = (AcompReferenciaItemAri) map.get(acompReferencia.toString()+item);
							%>
							<td align="center" nowrap>
							<%			
											if((Dominios.NAO).equals(exigeLiberarAcompanhamento) || ari.getStatusRelatorioSrl().equals(statusLiberado)){
												if(!"true".equals(status)){
													
										
													if(enderecoAbaVisualizacaoSemFuncao != null && !"".equals(enderecoAbaVisualizacaoSemFuncao)){
%>
								<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"
									href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacaoSemFuncao%>', <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>, 
																							<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">
<%	
													} else{
													%>
								<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"
									href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacao%>', <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>, 
																							<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">
													
													<% 	
													}
														itensSessaoVisualizar.add(ari.getCodAri().toString());
														request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );
														if(usuarioLogadoEmiteParecer || permissaoLapis) {
															itensSessao.add(ari.getCodAri().toString());
															request.setAttribute("itensSessao",itensSessao );
														}
												}
												//TODO - Alterado 20/03/2012
												List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
												Iterator itRelatorios = relatorios.iterator();
												
												//TODO - Ponto de sujestão p/ melhoria - código da linha abaixo foi retirado do laço de repetição
												//List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
	
												String imagePath = "";
												String aval = "";
												while(itRelatorios.hasNext()){												
													AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
													
													// Comentado por José André . Merge por Patricia Pessoa
													//if(validaPermissao.permissaoConsultaParecerIETTGrupos(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa(),Long.valueOf(codTipoAcompanhamento),seguranca)){
													
													//if(validaPermissao.permissaoLeituraAcompanhamento(ari, seguranca.getUsuario(), seguranca.getGruposAcesso())){
													if(listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())) {	
														
														UsuarioUsu usuarioImagem = null;  
									            		String hashNomeArquivo = null;
														
														// Por Rogério (05/10/2007)
														// Modificada a forma de busca da imagem relacionada com a Cor.
														// Referente ao Mantis #7442
	
														boolean imageError = false;
														if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
															Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
															TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
																													
															imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
															
															if( imagePath != null && imagePath.trim().length()>0) {
																
																usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
										    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
										    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
																
																/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
															    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
															    aval += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
															} else {
																if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
																	aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
																	aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
																	aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
																} else {
																	imageError = true;
																}
															}
														} else {
															imageError = true;
														}
																												
														// Verifica se o parecer é obrigatorio ou opcional
														List listTipoAcompFuncAcomp = new ArrayList(acompReferencia.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
														boolean ehObrigatorio = true;
														if(listTipoAcompFuncAcomp != null) {
															Iterator tipoFuncAcompIt = listTipoAcompFuncAcomp.iterator();
															while(tipoFuncAcompIt.hasNext()) {
																TipoAcompFuncAcompTafc tipoAcompFuncAcompTafc = (TipoAcompFuncAcompTafc)tipoFuncAcompIt.next();
																if(	relatorio.getTipoFuncAcompTpfa().getCodTpfa().equals(tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa())
																	&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc() != null 
																	&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc().equals(Dominios.OPCIONAL)) {
																	ehObrigatorio = false;
																}
															}
														}
														
														if( imageError && ehObrigatorio) {
															aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
															aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
															aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
														}													
													}																																								
												}
												
												out.println(aval);
																									
												if(!"true".equals(status)){
												%> </a>	<%
												}
											} else {
												if((Dominios.NAO).equals(exigeLiberarAcompanhamento) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
													if(!"true".equals(status)){
														if(enderecoAbaVisualizacaoSemFuncao != null && !"".equals(enderecoAbaVisualizacaoSemFuncao)){
															%>
																<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"
																	href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacaoSemFuncao%>', <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>, 
																																						<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">
															<%	
														} else{
															%>
																<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"
																	href="#" onclick="javascript:aoClicarConsultarExibicaoAba(form, '<%=enderecoAbaVisualizacao%>', <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>, 
																															<%=situacaoEstaConfigurada%>, '<%=nomeAbaVisualizacao%>')">
																												
															<% 	
														}
														itensSessaoVisualizar.add(ari.getCodAri().toString());
														request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );					 
														if(usuarioLogadoEmiteParecer || permissaoLapis) {
															itensSessao.add(ari.getCodAri().toString());
															request.setAttribute("itensSessao",itensSessao );
														}
													}  
									%>
									<p title="Não liberado">N/L</p> 
												<%  if(!"true".equals(status)){  %> 
														</a>
												<%	}
											} else {
											flag = "";
							%> &nbsp; <%
											}
										}
							%>
							</td>
							<!-- termina de imprimir os pareceres -->
							<%
								}
							}
							if("true".equals(status)){ 
								%> <td align="center"> <%
								if((usuarioLogadoEmiteParecer || permissaoLapis) && item.getNivelIett().intValue()>0 && acompAri != null) {
									String idOrgao = "";
									//adiciona o codigo do orgao ao checkbox quando for separado por orgao
									if (tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")){
										
										if(secretaria == null){
											nomeOrgaoSepararPorOrgao = "_orgX";
											idOrgao = ConstantesECAR.ZERO;
										} else {
											idOrgao = secretaria.getCodOrg().toString(); 
										}
										%>
											<input type="checkbox" class="form_check_radio"	id="<%=idOrgao%>" value="<%=item.getCodIett() + nomeOrgaoSepararPorOrgao%>">
									<%} else { %>
											<input type="checkbox" class="form_check_radio"	id="<%=idOrgao%>" value="<%=item.getCodIett()%>">
									<%}
								}
								%> </td> <%
							}
							%>
							<input type="hidden" name="item_<%=contador%>" id="item_<%=contador%>" value="<%=item.getCodIett()%>">
				
						</tr>
						<!-- termina de imprimir a linha do item -->
							<% 
							// Aqui termina a impressão dos filhos
							
							contador++;
							
						}// fim do while(itItens.hasNext())
						
						//Setando hidden com os itens da listagem para os botões Avançar/Retroceder
						String strCodArisControle = "";
						Iterator itCodArisControle = itensSessao.iterator();
						
						while(itCodArisControle.hasNext()){
							String codAri = (String) itCodArisControle.next();
							strCodArisControle += codAri + "|";
						}
											
						//Setando hidden com os itens da listagem para os botões Avançar/Retroceder de Visualizar parecer
						String strCodArisControleVisualizar = "";
						Iterator itCodArisControleVisualizar = itensSessaoVisualizar.iterator();
						
						while(itCodArisControleVisualizar.hasNext()){
							String codAri = (String) itCodArisControleVisualizar.next();
							strCodArisControleVisualizar += codAri + "|";
						}
						
						%>
						<script language="Javascript" type="text/javascript">
								document.form.codArisControle.value = "<%=strCodArisControle%>";
								document.form.codArisControleVisualizacao.value = "<%=strCodArisControleVisualizar%>";								
						</script>
						<%						
								//request.setAttribute("codArisControle", itensSessao);								
						%>
						<tr class="linha_subtitulo2">
							<td colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								&nbsp;
							</td>
						</tr>

					</table>
				</div>
				<%
				
					//quando o tipo de acompanhamento nao for separado por orgao, sempre vai aparecer a mensagem de que nao possui nenhum registro
					// quando for separado por orgao, a mensagem só aparece quando uma referencia tiver sido criada para itens sem orgao ou para um orgao especifico 
					} else if(referenciaOrgaoAtual != null){
					
					
					
					if(primeiro  && !"true".equals(status)) {
					
				%>
				
				
				<br>																					
				<table border="0" width="100%">
															
					<tr>
						<td class="texto" align="right">
						<util:barraLinks					
							_pathEcar="<%= _pathEcar%>"
							verificaMonitoramento="<%=new Boolean(true) %>"
							request="<%=request%>"
							/>
						</td>
					</tr>
					
				</table>
				
					<%} %>
				
				<div id="subconteudo" align="right">
				
				<!-- O hidden  numeroColunasTabela é usado na exibição dos filhos no Ajax(botão carregando)-->
					<input type="hidden" name="numeroColunasTabela" id="numeroColunasTabela" value="<%=	periodosConsideradosAgrupados.size() + 7%>">
					
					<table id="tabelaItemEstrutura<%=nomeOrgaoSepararPorOrgao %>" border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- TÍTULO DA TABELA -->
						<tr class="linha_titulo">
							<td colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<%
								if(tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals(Dominios.SIM)) {
									if(secretaria != null) {
								 		out.println(secretaria.getDescricaoOrg());
								 	} else {
								 		out.println(configuracao.getLabelAgrupamentoItensSemOrgao());	
								 	}
								}
							 	 
								%>
							</td>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsideradosAgrupados.size() + 7%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
											

					</table>
					
					</div>
				
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto" align="center">
							<b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b>
						</td>
					</tr>
				</table>
				<br>
				<%
			
				
					
				}
				
				if(primeiro)
					primeiro = false;
				
					
				}
			}
			
			if (session.getAttribute("mapItensReferencia") != null){
				session.removeAttribute("mapItensReferencia");
			}
			
			session.setAttribute("mapItensReferencia", mapItensReferencia);
						
			if("true".equals(status) && contador < 0){
				List orgaos2 = (List) session.getAttribute("orgaosPrimeiraChamada");
				
				String scriptCombo2 = _disabled + " onchange=\"reloadAlterarReferenciaRelatorio();\"";
		%>
				<br>
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto" align="center">
							<b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td class="texto">
							<b>Selecione o orgão responsável:</b>
							<%
							if (orgaoResponsavel.getCodOrg() != null) {
%>
							<combo:ComboTag nome="orgaoResponsavel"
								objeto="ecar.pojo.OrgaoOrg" label="siglaOrg" value="codOrg"
								order="siglaOrg" colecao="<%=orgaos2%>"
								selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
								scripts="<%=scriptCombo2%>" textoPadrao="Todos" />
							<%
							}else{
%>
							<combo:ComboTag nome="orgaoResponsavel"
								objeto="ecar.pojo.OrgaoOrg" label="siglaOrg" value="codOrg"
								order="siglaOrg" colecao="<%=orgaos2%>"
								scripts="<%=scriptCombo2%>" textoPadrao="Todos" />
							<%
							}
					%>
						</td>
					</tr>
				</table>
				<% 
			}
		} else {
	%>
			<!--  Mensagem para alertar que o tipoAcompanhamento é obrigatório  -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="texto" align="center">
							<%=_msg.getMensagem("posicaoGeral.validacao.tipoAcompanhamento.obrigatorio")%>
						</td>
					</tr>
				</table>
				<% 
		}
	%>
			</form>
			
			<br>
			<!--  Rodapé com o tempo para processar a página -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			<% Date dataFinal = Data.getDataAtual(); %>
			<table class="form">
				<tr>
					<td>
						<%
						long df = dataFinal.getTime();
						long di = dataInicio.getTime();
						%>
						<b>Tempo para processar esta página:</b>
						<%=Data.parseDateHourSegundos( new Date(df - di) )%>
						mm.ss.SSS
					</td>
					<td>&nbsp;
					</td>
				</tr>
			</table>
			

<%
	}
	
%>	
	

	
<%	
} catch (ECARException e) {
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
		<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-33177253-3']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
		
	</body>	
	<%@ include file="/include/ocultarAguarde.jsp"%>
	<%@ include file="/include/estadoMenu.jsp"%>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>