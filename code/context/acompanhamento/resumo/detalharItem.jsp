<% 
String hidAcao = Pagina.getParam(request, "hidAcao");
//out.print("hidAcaoAto = " + hidAcao);
String funcaoSelecionada = "RESUMO";
%>

<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="ecar.pojo.AcompRefItemLimitesArli"/>
<jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<jsp:directive.page import="ecar.pojo.AcompRefLimitesArl"/>
<jsp:directive.page import="java.util.Comparator"/>
<jsp:directive.page import="ecar.dao.TipoAcompanhamentoDao"/>
<jsp:directive.page import="ecar.dao.EstruturaAcessoDao"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.pojo.PontoCriticoPtc"%>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>

<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.AcompRealFisicoDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@page import="ecar.permissao.ValidaPermissao"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.Collections" %>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>


<%@page import="comum.util.Util"%><html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script type="text/javascript">
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			function recarregar() {
				document.forms[0].action = "detalharItem.jsp";
				document.form.recarregaCodArisControle.value = 'recarregaCodArisControle';
				document.forms[0].submit();				
			}
			function linkDetalhado(link){
				document.forms[0].action = link;
				document.forms[0].submit();
			}
		</script>
	</head>

	<%@ include file="../../cabecalho.jsp"%>
	<%@ include file="../../divmenu.jsp"%>

	<body>
<% 
try { %>
		<div id="conteudo">
		<%
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		CorDao corDao = new CorDao(request);
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
		UsuarioUsu usuario = null;
		
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
		String strCodAri = Pagina.getParamStr(request, "codAri");
		String periodo = Pagina.getParamStr(request, "periodo");
	
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		
		AcompReferenciaItemAri ari = null;
		List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(new Long(codTipoAcompanhamento));
	
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
	
		List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
		
		ValidaPermissao validaPermissao = new ValidaPermissao();
		TipoAcompanhamentoTa tipoAcompanhamento = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa();
		List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
				
		
		List itensSessao = new ArrayList();
		String strCodArisControleAux = "";
		String recarregaCodArisControle = Pagina.getParamStr(request, "recarregaCodArisControle");
		if(recarregaCodArisControle != null && !recarregaCodArisControle.equals("")) {
			OrgaoDao orgaoDao = new OrgaoDao(request);
			SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
			
			Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
			usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
			OrgaoOrg orgaoResponsavel = new OrgaoOrg();
			
			List listNiveis = new ArrayList();
			String[] niveis = niveisPlanejamento.split(":");
			for(int n = 0; n < niveis.length; n++){
				String codNivel = niveis[n];
				
				if(!codNivel.equals("")){
					listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
				}
			}
			
			Long codArefReferencia = Long.valueOf(mesReferencia);				
			Collection periodosConsiderados = new ArrayList();
			int qtdePeriodosAnteriores = 1;
					
			if(codArefReferencia.intValue() > 0) {
				periodosConsiderados = acompReferenciaDao.getPeriodosAnterioresOrdenado(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento), false);
			}
			
			Long codIettPai = itemEstruturaDao.getAscendenteMaximo(itemEstrutura).getCodIett();
			
			Object itens[] = ariDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
									periodosConsiderados, listNiveis, orgaoResponsavel, 
									usuario, seguranca.getGruposAcesso(), 
									Long.valueOf(codTipoAcompanhamento), codIettPai,
									isSemInformacaoNivelPlanejamento, null, null, -2, 1, null);
			List itensAcompanhamentos = new ArrayList((Collection)itens[0]);
			
			if(!(Pagina.getParamStr(request, "orgaoResponsavel")).equals("")){
				orgaoResponsavel = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
			}
				
			/* Se for encontrado algum periodo, imprime lista de itens */
			if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
				Iterator itensAcompanhamentosIt = itensAcompanhamentos.iterator();
				while(itensAcompanhamentosIt.hasNext()) {
					AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itensAcompanhamentosIt.next();
					ItemEstruturaIett item = aeIett.getItem();
					
					//INÍCIO DA LÓGICA DE PERMISSÃO DE REGISTRO DE PARECER																				
					boolean usuarioLogadoEmiteParecer = false;
					//Dao do objeto principal(ItemEstUsutpfuac) onde veremos a permissão do usuário/grupo 																		
					Iterator itPeriodosAcao = periodosConsiderados.iterator();
					Map  mapAcao = ariDao.criarMapPeriodoAri(periodosConsiderados, item);

					if(itPeriodosAcao.hasNext()) {
						//Pega só o ciclo selecionado (Aref), que é o primeiro
						AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
						if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
							AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
							
							//Pega os Arels do Ari selecionado 
							List relatorios = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ariAcao, tpfaOrdenadosPorEstrutura);
							Iterator itRelatorios = relatorios.iterator();
							
							while(itRelatorios.hasNext()){												
								AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
								
								ItemEstUsutpfuacIettutfa itemEstUsu = itemEstUsuDao.buscar(item.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
								 
								if (listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())){
								
									//Verifica se a permissão é de grupo ou usuário							 							
									if (itemEstUsu.getUsuarioUsu() != null) {
										usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
									} else if (itemEstUsu.getSisAtributoSatb() != null) {
										if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
											Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
											while (itUsuarios.hasNext()) {
												UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
												if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
													usuarioLogadoEmiteParecer = true;
													break;
												}
											}
										}
									}
									if(usuarioLogadoEmiteParecer==true)
										break;											
								}
							}//fecha while relatórios																
							if(usuarioLogadoEmiteParecer) {
								itensSessao.add(ariAcao.getCodAri().toString());
							}
								
						}
					}
				}
			}
			//Setando hidden com os itens da listagem para os botões Avançar/Retroceder
			Iterator itCodArisControle = itensSessao.iterator();
			while(itCodArisControle.hasNext()){
				String codAri = (String) itCodArisControle.next();
				strCodArisControleAux += codAri + "|";
			}
		}
		
		String telaAnterior = "";
		// essa variavel é utilizada no include do cabecalho das paginas
		if(mesReferencia != null && !mesReferencia.equals("")) {
				telaAnterior = "../posicaoGeral.jsp?codTipoAcompanhamento=" 
							+ codTipoAcompanhamento +"&mesReferencia="+mesReferencia
							+ "&periodo=" + periodo + "&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
		
		} else {
		
			if(ari != null) {
				telaAnterior = "../posicaoGeral.jsp?codTipoAcompanhamento=" 
							+ codTipoAcompanhamento +"&mesReferencia="+ari.getAcompReferenciaAref().getCodAref() 
							+ "&periodo=" + periodo + "&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
			}				
		}
		
		// essa variavel eh apenas utilizada no include do titulo
		String tituloMonitoramento = "REGISTRO";
		funcaoSelecionada = "RESUMO";
		// essa variavel eh apenas utilizada no include do cabecalho da tela
		boolean exibeCombo = false;
		String visaoDescricao = null;
		%>
		
		<%@ include file="../tituloMonitoramento.jsp" %>

<%
	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		}
	}

	tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
	//verifica se o usuário pode "Gerar Ciclo de Acompanhamento"
	boolean permissaoAdministradorAcompanhamento = new EstruturaAcessoDao(null).temPermissoesAcessoAcomp(tipoAcompanhamento, seguranca.getGruposAcesso());
	String itemDoNivelClicadoVoltar = ari.getItemEstruturaIett().getCodIett().toString();															
 %>
		
		
	<!-- NAO FOI USADO O FORM POR CAUSA DA PARTE DE CONTROLE DOS BOTÕES AVANÇA/RETROCEDE -->		
		
		
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
		
	String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");		
	Boolean exibirAbasAcompanhamento = Boolean.FALSE;
	
	if(formaVisualizacao.equalsIgnoreCase("geral")) {
		exibirAbasAcompanhamento= Boolean.TRUE;
	} 							
%>

<input type="hidden" id="hidFormaVisualizacaoEscolhida" name="hidFormaVisualizacaoEscolhida" value="<%=formaVisualizacao %>">

		<!-- ABA DE TIPO DE ACOMPANHAMENTO -->
		<util:barraLinkTipoAcompanhamentoTag
				codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
				exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>" 
				chamarPagina = "posicaoGeral.jsp"
				caminho="<%=_pathEcar%>" 
				gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
				request = "<%=request%>"
       	/>
<% } %>		
		
		<!-- imprime os botoes avançar e retroceder: levam o usuario respectivamente para o item posterior e anterior -->
		<% String paginaBtAvancRetroceder = "detalharItem.jsp"; 
		
		// CODIGO ADICIONADO PARA GERENCIAMENTO DE BOTÃO AVANÇAR/RETROCEDER - SABE-SE SE O USUARIO CLICOU OU NÃO NA ABA E A PARTIR DAÍ DEFINISSE O ENDEREÇO DA PAGINA PARA RETROCEDER/AVANAÇAR
		TipoAcompanhamentoDao taDaoFormRegistro = new TipoAcompanhamentoDao(request);
		TipoAcompanhamentoTa tipoAcompanhamentoFormRegistro = (TipoAcompanhamentoTa) taDaoFormRegistro.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
		AbaDao abaDaoFormRegistro = new AbaDao(request);
		String usuarioClicouAba =  Pagina.getParamStr(request, "clicouAba");
		String tipoPadraoExibicaoAba =  Pagina.getParamStr(request, "tipoPadraoExibicaoAba");
		String enderecoAbaVisualizacaoFormRegistro = null;
		if (usuarioClicouAba!=null && usuarioClicouAba.equals("S")) {
			enderecoAbaVisualizacaoFormRegistro = abaDaoFormRegistro.pesquisaEnderecoAbaRegistro(tipoAcompanhamentoFormRegistro, seguranca.getGruposAcesso(), Integer.parseInt(tipoPadraoExibicaoAba), "RESUMO");
		} else {
			enderecoAbaVisualizacaoFormRegistro = abaDaoFormRegistro.pesquisaEnderecoAbaRegistro(tipoAcompanhamentoFormRegistro, seguranca.getGruposAcesso(), Integer.parseInt(tipoPadraoExibicaoAba), null);
		}
		paginaBtAvancRetroceder = _pathEcar + "/" + enderecoAbaVisualizacaoFormRegistro;
		
		%>


		<!-- ABA DE LISTAS -->
		<util:barraLinksRegAcomp
			acompReferenciaItem="<%=ari%>"  
			selectedFuncao="RESUMO"
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			abaSuperior="<%= Dominios.SIM %>"
			listaGeral="<%=telaAnterior %>"
			telaDoItem = "R"
			caminho="<%=_pathEcar%>"
		/> 
		
		
		<%@ include file="../botoesAvancaRetrocede.jsp" %>
		<!-- o cabecalho da tela -->
		<%@ include file="../cabecalhoTelaItem.jsp" %>	
		
		<% usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(); %>
		
		<!-- ABAS DO ITEM -->
		<util:barraLinksRegAcomp
			acompReferenciaItem="<%=ari%>"  
			selectedFuncao="RESUMO"
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			caminho="<%=_pathEcar%>"
		/> 
		<br>
		
		<form name="form" id="form" method="post" action="">
				
			<table class="barrabotoes" cellpadding="0" cellspacing="0">			
				<tr>
					<td align="left"> &nbsp; </td>
				</tr>
			</table>

			<input type="hidden" name="hidAcao" value="<%=hidAcao%>">
			<input type="hidden"  name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
			<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
			<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
			<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
			<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
			<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
			<input type="hidden" name="periodo" value='<%=Pagina.getParamStr(request, "periodo")%>'>
			<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
			<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
			<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
			<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
			
			<%if (!Pagina.getParamStr(request, "codAri").equals("")){ %>
			<input type="hidden" name="codAri" id="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
			<% } else if (ari!=null){  %>
			<input type="hidden" name="codAri" id="codAri" value="<%=ari.getCodAri()%>">
			<%
			} 
			%>
			
			<!-- Campo necessário para botões de Avança/Retrocede -->
			<% if(strCodArisControleAux != null && !strCodArisControleAux.equals("")) { %>
				<input type="hidden" name="codArisControle" value="<%=strCodArisControleAux%>">
			<% 
				String teste = strCodArisControleAux;
			
			} else { %>
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
				
			<% 
				String teste = Pagina.getParamStr(request, "codArisControle");
			} %>		
			<input type="hidden" name="recarregaCodArisControle" value="">

			<%																				
			
			// Configuração	
			//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
			String pathRaiz = configuracao.getRaizUpload();	
			
			boolean usuarioLogadoEmiteParecer = false;
			Boolean permissaoAlterar =  new Boolean(false);
			usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
			boolean primeiro = true;
			ItemEstUsutpfuacDao iettEstUsuTpfaDao = new ItemEstUsutpfuacDao(request);
			
			Date dataLimiteImprimir = new Date();
			//monta a variavel que imprime as funcoes de acompanhamento na ultima tabela da pagina
			//e guarda a data limite maior
			
			StringBuffer imprimeFuncTipoAcomp = new StringBuffer();
			List relatoriosDesordenados = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
			
			//Ordena de acordo com a Data Limite das funções de acompanhamento (As funções com datas mais próximas serão exibidas primeiro);
			List relatorios = ariDao.ordenaRelatorioFuncaoAcompByDataLimite(relatoriosDesordenados);
			Iterator itRelatorios = relatorios.iterator();
			
			/*Captura um objeto relatório para recuperar a lista de Datas Limites */
			AcompRelatorioArel relatorio  = null;
			
			while(itRelatorios.hasNext()) {	
			
				String imagePath = "";
				String aval = "";
				String linkParecer = "";
				String labelDtliberado = "";
				boolean imageError = false;
				relatorio = (AcompRelatorioArel) itRelatorios.next();
				
				
				TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );		
				
				if (listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())){
				
					UsuarioUsu usuarioImagem = null;  
            		String hashNomeArquivo = null;
					
					imprimeFuncTipoAcomp.append("<tr class=\"tabelaDetalheItem\">");
					imprimeFuncTipoAcomp.append("<td width=\"20%\" align=\"left\"><strong>"+tpfa.getDescricaoTpfa()+"</strong></td>");
					
					ItemEstUsutpfuacIettutfa itemEstUsu 
							= itemEstUsuDao.buscar(itemEstrutura.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
					
				 
					//Verifica se a permissão é de grupo ou usuário
					if(itemEstUsu != null) {							 							
						if (itemEstUsu.getUsuarioUsu() != null) {
							usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
							
							permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);

							//adiciona a parte de buscar superiores
							if(!permissaoAlterar.booleanValue()){
								List listaEstruturas = null;
								if(relatorio.getIndLiberadoArel() == null || "N".equals(relatorio.getIndLiberadoArel())) {
									listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
								} else{
									if(relatorio.getTipoFuncAcompTpfaUsuario()!=null){
										ItemEstUsutpfuacIettutfa itemEstUsuSuperior = 
											iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
										if(itemEstUsuSuperior!=null && itemEstUsuSuperior.getUsuarioUsu()!=null){
											usuarioLogadoEmiteParecer = itemEstUsuSuperior.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
											permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);								
										}
										if(!permissaoAlterar.booleanValue()){
											listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
										}
									} else{
										listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
									}
								}
								if(listaEstruturas != null) {
									Iterator itEstruturas = listaEstruturas.iterator();	
									while (itEstruturas.hasNext()){
										ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
								        if(itemEst != null) {							 							
											if (itemEst.getUsuarioUsu() != null) {
												usuarioLogadoEmiteParecer = itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
												permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);
												if(permissaoAlterar.booleanValue()){
													break;	
												}
											} else if (itemEst.getSisAtributoSatb() != null && itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {					
												Iterator itUsuarios = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
												while (itUsuarios.hasNext()) {
													UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
													if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
														usuarioLogadoEmiteParecer = true;
														permissaoAlterar = new Boolean(true);
														break;
													}
												}					        	
									        }
								        } 
									}				
								}		
							}
							
							
							
						} else if (itemEstUsu.getSisAtributoSatb() != null) {
							if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
								Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
								while (itUsuarios.hasNext()) {
									UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
									if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
										usuarioLogadoEmiteParecer = true;
										break;
									}if(usuarioLogadoEmiteParecer) {
										linkParecer = "<a href=\"javaScript:linkDetalhado('../situacao/relatorios.jsp')\" >";
									}
									
								}
							}
						}
					}	
				
				
					if(usuarioLogadoEmiteParecer) {
						linkParecer = "<a href=\"javaScript:linkDetalhado('../situacao/relatorios.jsp')\" >";
					}
					
					if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
						Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
						imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
						labelDtliberado = " - Liberado em: " + Data.parseDate( relatorio.getDataUltManutArel() );
						if( imagePath != null ) {
							
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
	    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
	    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
						    aval += linkParecer + "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
						    aval += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
						    aval += labelDtliberado;
						} else {
							if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
								aval += linkParecer + "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
								aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
								aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
								aval += labelDtliberado;
							} else {
								imageError = true;
							}									
						}
					} else {
						imageError = true;
					}
					if(imageError) {
						aval += linkParecer + "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
						aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
						aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
						aval += labelDtliberado;
					}
					if(!linkParecer.equals(""))
						aval += "</a>";
					
	
					imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">"+aval+"</td>");
	
					/*Coluna Datas Limite */
					imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">Data Limite: "+ Data.parseDate( tipoFuncAcompDao.getDataLimiteDoTipoFuncaoDeAcomp(relatorio) ) +"</td>"); 
					//imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">"+ Data.parseDate(relatorio.getAcompReferenciaItemAri().getDataLimiteAcompFisicoAri()) +"</td>");
					imprimeFuncTipoAcomp.append("</tr>");
					
					//pega a maior data limite para imprimir na ultima linha da tabela de detalhamento do item
					if(primeiro) {
						dataLimiteImprimir = relatorio.getAcompReferenciaItemAri().getDataLimiteAcompFisicoAri();
						primeiro = false;
					} else {
						if(dataLimiteImprimir.before(relatorio.getAcompReferenciaItemAri().getDataLimiteAcompFisicoAri()))
							dataLimiteImprimir = relatorio.getAcompReferenciaItemAri().getDataLimiteAcompFisicoAri();
					} 
				}	
			}
			%>
		
			<!-- começa a imprimir a tabela com dados gerais do item -->
			<table class="tabelaDetalheItem" width="100%" cellpadding="0" cellspacing="0">
			<%
			AbaDao abaDao = new AbaDao(request);
			Aba abaAux = new Aba();
        	abaAux.setExibePosicaoAba("S");
        	       	
			// Iterator itAbas = lista.iterator();
			Iterator itAbas = abaDao.getListaAbasComAcesso(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(),  seguranca.getGruposAcesso()).iterator();
			
			//para cada aba imprime o label e o valor
			boolean abaSituacao = false;
			while (itAbas.hasNext()) {
				Aba aba = (Aba) itAbas.next();
				if(!aba.getNomeAba().equals("SITUACAO_INDICADORES") && !aba.getNomeAba().equals("DATAS_LIMITES")) {
				
					//if("DATAS_LIMITES".equals(aba.getNomeAba())){
					//	aba.setLabelAba("Data Limite - Físico");
					//}
					
					String labelAba = aba.getLabelAba();
					boolean mostraAba = false;
					boolean existeFuncao = false;
					//Exibe o label das funções de acordo com o que foi cadastrado na tela "Exibição de Abas". Caso a aba faça referência a uma função e a função exista para
			        //a estrutura, o sistema irá utilizar o label da função que está associada à estrutura em questão.
			        if(aba.getFuncaoFun()!= null){
			        	Set listaFuncoes = ari.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
			        	Iterator itListaFuncoes = listaFuncoes.iterator();
			        	while(itListaFuncoes.hasNext()){
			        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
			        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
			        			labelAba = funcao.getLabelEttf();
			        			existeFuncao = true;
			        			break;
			        		}
			        	}
			        }
			        mostraAba = (aba.getFuncaoFun() != null && existeFuncao == true) || (aba.getFuncaoFun() == null && !aba.getLabelAba().equals(""));
	
					if( !(labelAba.trim()).equals("") ) {
				%>
						<tr class="tabelaDetalheItem">
				<%		if(mostraAba){
							if(!aba.getNomeAba().equals("SITUACAO")) {
								//if(aba.getNomeAba().equals("DATAS_LIMITES")) {
					%>				<!-- td width="20%" align="left"><strong>< % =labelAba % ></strong></td-->
					<%			//} else 
								if(!aba.getNomeAba().equals("RESUMO")){
					%>				<td width="20%" align="left"><strong><%=labelAba%></strong></td>
					<% 			}
								if(aba.getNomeAba().equals("DADOS_GERAIS")) {  
					%>				<td width="40%" align="left"><a href="javascript:linkDetalhado('../dadosGerais/frm_con.jsp')">
										<%=itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(itemEstrutura, ari.getItemEstruturaIett().getEstruturaEtt())%>
									</a></td>
									<td width="40%" align="left">
					<%
									String datas = ""; // Para exibir o intervalo entre a data inicial e final caso existam  
									if (itemEstrutura.getDataInicioIett()!=null && ( !itemEstrutura.getDataInicioIett().equals("") )) {
										
										if (itemEstrutura.getDataTerminoIett()!= null && (!itemEstrutura.getDataTerminoIett().equals("")) ) {
											datas += "De " + Data.parseDate(itemEstrutura.getDataInicioIett()); 
											datas += " a " + Data.parseDate(itemEstrutura.getDataTerminoIett());
										} else {
											Iterator itEttats = itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().iterator();
											String dataInicial = "Data Inicial: ";
											
											while (itEttats.hasNext()){
												EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEttats.next();
												if (estruturaAtributoEttat.iGetNome().equals("dataInicioIett")){
													dataInicial = estruturaAtributoEttat.iGetLabel() + ": ";
												}
											}
											
											datas += dataInicial + Data.parseDate(itemEstrutura.getDataInicioIett());
										}
									} else if (itemEstrutura.getDataTerminoIett()!= null && (!itemEstrutura.getDataTerminoIett().equals(""))) { // Só entra aqui se não tiver data Inicial
										Iterator itEttats = itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().iterator();
										String dataFinal = "Data Final: ";
										
										while (itEttats.hasNext()){
											EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEttats.next();
											if (estruturaAtributoEttat.iGetNome().equals("dataTerminoIett")){
												dataFinal = estruturaAtributoEttat.iGetLabel() + ": ";
											}
										}
										
										datas += dataFinal + Data.parseDate(itemEstrutura.getDataTerminoIett());
									}
					 %>					
									
									<%=datas %>&nbsp; </td>
					<%				
								} else if(aba.getNomeAba().equals("PONTOS_CRITICOS")) {
									PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
									Collection todosPontosCriticos = pontoCriticoDao.getAtivos(itemEstrutura);
									Collection pontosCriticosNaoSolucionados = pontoCriticoDao.getPontosCriticosNaoSolucionados(itemEstrutura);
									if(pontosCriticosNaoSolucionados != null && pontosCriticosNaoSolucionados.size() > 0) {
					%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('../restricoes/pontosCriticos.jsp');"><%=pontosCriticosNaoSolucionados.size() + " " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt())  + " Não Solucionados"%></a></td>
					<%				} else if(todosPontosCriticos != null && todosPontosCriticos.size() > 0) {
					%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('../restricoes/pontosCriticos.jsp');"><%="Todos " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt()) + " Foram Solucionados"%></a></td>
					<%				} else {
					%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('../restricoes/pontosCriticos.jsp');"><%="Sem " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt())%></a></td>
					<%				}
					%>				<td>&nbsp;</td>
					<%
								} else if(aba.getNomeAba().equals("SITUACAO_DATAS")) { 
					%>				<td width="40%" align="left">
										<a href="javascript:linkDetalhado('../situacaoDatas/situacaoDatas.jsp')">Consultar</a>
									</td>
									<td>&nbsp;</td>
					<%			} else if(aba.getNomeAba().equals("GALERIA")) {
					%>				<td width="40%" align="left"><a href="javascript:linkDetalhado('../galeria/galeria.jsp')">
					<%				Set itensEstrutUploadIettupsAtivos = itemEstrutura.getItemEstrutUploadIettupsAtivos();
									if(itensEstrutUploadIettupsAtivos != null && itensEstrutUploadIettupsAtivos.size() > 0) {
										out.print(itensEstrutUploadIettupsAtivos.size() + " Anexos Disponíveis");
									} else {
										out.print("Nenhum Arquivo Anexado");
									}
					%>				</a></td>
									<td>&nbsp;</td>
					<%			} else if(aba.getNomeAba().equals("REL_FISICO_IND_RESULTADO")) {
									List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari ,Dominios.TODOS, true);
					%>				<td width="40%" align="left">
									<a href="javascript:linkDetalhado('../resultado/indicadoresResultado.jsp')">
					<%				if(indResultados != null && indResultados.size() > 0) {
										out.print("Informado");
									} else {
										out.print("Não Informado");
									}
					%>				</a></td>
									<td>
									<% if(dataLimiteImprimir != null && !dataLimiteImprimir.equals(new Date())) {
										out.print("Data Limite: "+Data.parseDate(dataLimiteImprimir));
									   } else {
									   	out.print("Nenhuma data limite foi informada.");
									   }
									%>
									</td>
					<%			} else if(aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
					%>				<td width="40%" align="left">
										<a href="javascript:linkDetalhado('../situacaoIndicadores/situacaoIndicadores.jsp')">Consultar</a>
									</td>
									<td>&nbsp;</td>
		 			<%			} else if(aba.getNomeAba().equals("FINANCEIRO")) { %> 
									<td width="40%" align="left">
										<a href="javascript:linkDetalhado('../financeiro/financeiro.jsp')">
										<%ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
											List listaExercicios = itemEstPrevDao.getListaExerciciosItemEstruturaPrevisao(ari.getItemEstruturaIett());
											if (listaExercicios.size()==0){
												%>Nenhum Dado Cadastrado<%
											}else{
		                                		%>Consultar<%
		                                	}%>
										</a>
									</td>
									<td>&nbsp;</td>
					<%			} else if(aba.getNomeAba().equals("ETAPA")) { 
									List estruturaEtapas = estruturaDao.getEstruturasEtapas(ari.getItemEstruturaIett().getEstruturaEtt());
									String linkEtapas = "";
									if(estruturaEtapas != null && estruturaEtapas.size() > 0) {
										linkEtapas = "Informado";
									} else {
										linkEtapas = "Não Informado";
									}
					%> 				<td width="40%" align="left">
										<a href="javascript:linkDetalhado('../etapa/etapas.jsp')"><%=linkEtapas%></a>
									</td>
									<td>&nbsp;</td>
					<%			//} else if(aba.getNomeAba().equals("DATAS_LIMITES")) { 
					%> 
									<!-- td width="40%" align="left">
										<a href="javascript:linkDetalhado('../datasLimites/datasLimites.jsp')">
										< %	StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
											if(ari.getStatusRelatorioSrl().equals(statusLiberado)) {
												out.print("Liberado");
											} else {
												out.print("Não Liberado");
											}
										% >	</a>
									</td>
									<td>&nbsp;</td-->
					<%			} else if(aba.getNomeAba().equals("EVENTOS")) { %> 
									<td width="40%" align="left">
										<a href="javascript:linkDetalhado('../realizacoes/eventos.jsp')">
					<%					if(ari.getItemEstruturaIett().getItemEstrutAcaoIettas() != null && ari.getItemEstruturaIett().getItemEstrutAcaoIettas().size() > 0) {
											out.print("Informado");
										} else {
											out.print("Não Informado");
										}
					%>					</a>
									</td>
									<td>&nbsp;</td>
					<%			} else if (aba.getNomeAba().equals("GRAFICO_DE_GANTT")) {
									Date dataAtual = Data.getDataAtual();
									String dataBaseStr = Data.getAno(dataAtual) + "/" + 
										(Data.getMes(dataAtual)+1) + "/" +
										Data.getDia(dataAtual);
									Date dataBase = new Date(dataBaseStr);
									boolean pontosCriticosSolucionados = false;
									if (ari.getItemEstruturaIett().getPontoCriticoPtcs() != null && ari.getItemEstruturaIett().getPontoCriticoPtcs().size() > 0){					
										Iterator itPontosCriticos = ari.getItemEstruturaIett().getPontoCriticoPtcs().iterator();
										if(itPontosCriticos != null) {
										    while (itPontosCriticos.hasNext()) {
									    		PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPontosCriticos.next();						    		
									    		if ((pontoCritico.getIndExcluidoPtc() == null || pontoCritico.getIndExcluidoPtc().equals("N")) &&
									    				pontoCritico.getIndAtivoPtc().equals("S")) {
									    			if ((pontoCritico.getDataSolucaoPtc() == null) || (pontoCritico.getDataSolucaoPtc() != null && pontoCritico.getDataSolucaoPtc().compareTo(dataBase) <= 0)){
									    				pontosCriticosSolucionados = true;
									    				break;
									    			}
									    		}
									    		
									    	}
										}
									}
					%>				<td>
					<%					if (pontosCriticosSolucionados) {
					%>						<a href="javascript:linkDetalhado('../graficoGantt/graficoGantt.jsp')">
					<%						out.print("Visualizar");
					%>						</a>
					<%
										} else {
											out.print("Não há dados cadastrados para a data base selecionada.");
										}
					%>				</td>
									<td>&nbsp;</td>
					<% 			}
							}else{
								abaSituacao = true;
							}
						}				
				%>
						
				<%
					}
				}
			}
			 %>

			</table>
			<!-- termina de imprimir tabela com dados gerais do item -->
			<br>
			
			<!-- começa a imprimir tabela com os relatorios de acompanhamento -->
			
			<%if(abaSituacao){%>
				<table class="tabelaDetalheItem" width="100%" cellpadding="0" cellspacing="0">
					<%= imprimeFuncTipoAcomp %>
				</table>
			<%}%>
			<!-- termina de imprimir tabela com os relatorios de acompanhamento -->
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
	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp"%>
	<%@ include file="../../include/mensagemAlert.jsp" %>
</html>