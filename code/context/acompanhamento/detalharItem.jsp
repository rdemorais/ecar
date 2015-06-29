<jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
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
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.Collections" %>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>


<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script type="text/javascript">
			function recarregar() {
				document.forms[0].action = "detalharItem.jsp";
				var combo = document.getElementById("referenciaCombo");
				for(i = 0; i < combo.length; i++) {
					if(combo[i].selected) {
						document.form.referencia_hidden.value = referenciaCombo[i].value;
						document.form.recarregaCodArisControle.value = 'recarregaCodArisControle';
					}
				}
				document.forms[0].submit();
			}
			function linkDetalhado(link){
				document.forms[0].action = link;
				document.forms[0].submit();
			}
		</script>
	</head>

	<%@ include file="/cabecalho.jsp"%>
	<%@ include file="/divmenu.jsp"%>

	<body>
<%
try { %>
		<div id="conteudo">
		<%
		
		// Configuração	
		//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		String pathRaiz = configuracao.getRaizUpload();	
		
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		CorDao corDao = new CorDao(request);
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
		ItemEstUsutpfuacDao iettEstUsuTpfaDao = new ItemEstUsutpfuacDao(request);
		
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
		String strCodAri = Pagina.getParamStr(request, "codAri");
	
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		Boolean permissaoAlterar =  new Boolean(false);
		
		AcompReferenciaItemAri ari = null;
		List acompanhamentos = null;
		
		// existem casos em que ele perde o codigo de acompanhamento
		if(codTipoAcompanhamento == null || codTipoAcompanhamento.equals("")) { 
			ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
			codTipoAcompanhamento = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().toString();
		} 
		
		acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(new Long(codTipoAcompanhamento));
		
		Iterator acompanhamentosIt = acompanhamentos.iterator();
		while(acompanhamentosIt.hasNext()) {
			arefSelecionado = (AcompReferenciaAref)acompanhamentosIt.next();
			if((arefSelecionado.getCodAref().toString()).equals(mesReferencia)) {
				break;
			}
		} 	
		 
		
		
	
		if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
			itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			// se tiver codAri
			if(ari == null && !strCodAri.equals("")) { 
				ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
			} else if(ari == null) {
			// se nao tiver codAri
				ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
			}
		}
	
		List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
		
		List itensSessao = new ArrayList();
		String strCodArisControleAux = "";
		String recarregaCodArisControle = Pagina.getParamStr(request, "recarregaCodArisControle");
		if(recarregaCodArisControle != null && !recarregaCodArisControle.equals("")) {
			OrgaoDao orgaoDao = new OrgaoDao(request);
			SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
			
			Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
			UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
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
												}
											}
										}
									}
								}
								if(usuarioLogadoEmiteParecer==true)
									break;											
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
		
		// essa variavel é utilizada no include do cabecalho das paginas
		String telaAnterior = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia;
		// essa variavel eh apenas utilizada no include do titulo
		String tituloMonitoramento = "REGISTRO";
		// essa variavel eh apenas utilizada no include do cabecalho da tela
		boolean exibeCombo = true;
		String visaoDescricao = null;
		%>
		
		<%@ include file="tituloMonitoramento.jsp" %>
		
		<!-- imprime os botoes avançar e retroceder: levam o usuario respectivamente para o item posterior e anterior -->
		<% String paginaBtAvancRetroceder = "detalharItem.jsp"; %>
		<%@ include file="botoesAvancaRetrocede.jsp" %>
		
		<!-- o cabecalho da tela -->
		<%@ include file="cabecalhoTelaItem.jsp" %>	
		
		<form name="form" id="form" method="post" action="">
			<input type="hidden"  name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
			<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
			<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
			<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
			<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
			<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
			<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
			<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
			
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
			<% } else { %>
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
			<% } %>		
			<input type="hidden" name="recarregaCodArisControle" value="">

			<%				
			
			UsuarioUsu usuarioImagem = null;  
    		String hashNomeArquivo = null;
			
			boolean usuarioLogadoEmiteParecer = false;
			UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
			boolean primeiro = true;
			Date dataLimiteImprimir = new Date();
			//monta a variavel que imprime as funcoes de acompanhamento na ultima tabela da pagina
			//e guarda a data limite maior
			StringBuffer imprimeFuncTipoAcomp = new StringBuffer();
			List relatorios = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
			Iterator itRelatorios = relatorios.iterator();
			while(itRelatorios.hasNext()) {	
				String imagePath = "";
				String aval = "";
				String linkParecer = "";
				boolean imageError = false;
				AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
				TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );		
				imprimeFuncTipoAcomp.append("<tr class=\"tabelaDetalheItem\">");
				imprimeFuncTipoAcomp.append("<td width=\"20%\" align=\"left\"><strong>"+tpfa.getDescricaoTpfa()+"</strong></td>");
				
				ItemEstUsutpfuacIettutfa itemEstUsu 
						= itemEstUsuDao.buscar(itemEstrutura.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
				 
			//Verifica se a permissão é de grupo ou usuário
				if(itemEstUsu != null) {							 							
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
				}
				if(usuarioLogadoEmiteParecer) {
					linkParecer = "<a href=\"javaScript:linkDetalhado('situacao/relatorios.jsp')\" >";
				}
				
				if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
					Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
					imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
					if( imagePath != null ) {
						
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
						
						/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
					    aval += linkParecer + "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
					    aval += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
					} else {
						if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
							aval += linkParecer + "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
							aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
							aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
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
				}
				if(!linkParecer.equals(""))
					aval += "</a>";
				imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">"+aval+"</td>");
				imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">"+Data.parseDate(relatorio.getAcompReferenciaItemAri().getDataLimiteAcompFisicoAri())+"</td>");
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
			%>
		
			<!-- começa a imprimir a tabela com dados gerais do item -->
			<table class="tabelaDetalheItem" width="100%" cellpadding="0" cellspacing="0">
			<%
			AbaDao abaDao = new AbaDao(request);
			Aba abaAux = new Aba();
        	abaAux.setExibePosicaoAba("S");
			Iterator itAbas = abaDao.getListaAbasComAcesso(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(),  seguranca.getGruposAcesso()).iterator();
			
			//para cada aba imprime o label e o valor
			while (itAbas.hasNext()) {
				Aba aba = (Aba) itAbas.next();
				
				String labelAba = aba.getLabelAba();
				if( !(labelAba.trim()).equals("") ) {
			%>
					<tr class="tabelaDetalheItem">
			<%
					if(!aba.getNomeAba().equals("SITUACAO")) {
						if(aba.getNomeAba().equals("DATAS_LIMITES")) {
			%>				<td width="20%" align="left"><strong>Data Limite</strong></td>
			<%			} else {
			%>				<td width="20%" align="left"><strong><%=labelAba%></strong></td>
			<% 			}
						if(aba.getNomeAba().equals("DADOS_GERAIS")) {  
			%>				<td width="40%" align="left"><a href="javascript:linkDetalhado('dadosGerais/frm_con.jsp')"><%=itemEstrutura.getNomeIett()%></a></td>
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
			%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('restricoes/pontosCriticos.jsp');"><%=pontosCriticosNaoSolucionados.size() + " " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt())  + " Não Solucionados"%></a></td>
			<%				} else if(todosPontosCriticos != null && todosPontosCriticos.size() > 0) {
			%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('restricoes/pontosCriticos.jsp');"><%="Todos " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt()) + " Foram Solucionados"%></a></td>
			<%				} else {
			%>					<td width="40%" align="left"><a href="javascript:linkDetalhado('restricoes/pontosCriticos.jsp');"><%="Nenhum " + abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt())  + " Informado"%></a></td>
			<%				}
			%>				<td>&nbsp;</td>
			<%
						} else if(aba.getNomeAba().equals("SITUACAO_DATAS")) { 
			%>				<td width="40%" align="left">
								<a href="javascript:linkDetalhado('situacaoDatas/situacaoDatas.jsp')">Consultar</a>
							</td>
							<td>&nbsp;</td>
			<%			} else if(aba.getNomeAba().equals("GALERIA")) {
			%>				<td width="40%" align="left"><a href="javascript:linkDetalhado('galeria/galeria.jsp')">
			<%				if(itemEstrutura.getItemEstrutUploadIettups() != null && itemEstrutura.getItemEstrutUploadIettups().size() > 0) {
								out.print(itemEstrutura.getItemEstrutUploadIettups().size() + " Anexos Disponíveis");
							} else {
								out.print("Nenhum Arquivo Anexado");
							}
			%>				</a></td>
							<td>&nbsp;</td>
			<%			} else if(aba.getNomeAba().equals("REL_FISICO_IND_RESULTADO")) {
							List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari ,Dominios.TODOS, true);
			%>				<td width="40%" align="left">
							<a href="javascript:linkDetalhado('resultado/indicadoresResultado.jsp')">
			<%				if(indResultados != null && indResultados.size() > 0) {
								out.print("Informado");
							} else {
								out.print("Não Informado");
							}
			%>				</a></td>
							<td>&nbsp;</td>
			<%			} else if(aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
			%>				<td width="40%" align="left">
								<a href="javascript:linkDetalhado('situacaoIndicadores/situacaoIndicadores.jsp')">Consultar</a>
							</td>
							<td>&nbsp;</td>
 			<%			} else if(aba.getNomeAba().equals("FINANCEIRO")) { %> 
							<td width="40%" align="left">
								<a href="javascript:linkDetalhado('financeiro/financeiro.jsp')">Consultar</a>
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
								<a href="javascript:linkDetalhado('etapa/etapas.jsp')"><%=linkEtapas%></a>
							</td>
							<td>&nbsp;</td>
			<%			} else if(aba.getNomeAba().equals("DATAS_LIMITES")) { %> 
							<td width="40%" align="left">
								<a href="javascript:linkDetalhado('datasLimites/datasLimites.jsp')">
								<% if(dataLimiteImprimir != null && !dataLimiteImprimir.equals(new Date())) {
									out.print(Data.parseDate(dataLimiteImprimir));
								   } else {
								   	out.print("Nenhuma data limite foi informada.");
								   }
								%>
								</a>
							</td>
							<td>&nbsp;</td>
			<%			} else if(aba.getNomeAba().equals("EVENTOS")) { %> 
							<td width="40%" align="left">
								<a href="javascript:linkDetalhado('realizacoes/eventos.jsp')">
			<%					if(ari.getItemEstruturaIett().getItemEstrutAcaoIettas() != null && ari.getItemEstruturaIett().getItemEstrutAcaoIettas().size() > 0) {
									out.print("Informado");
								} else {
									out.print("Não Informado");
								}
			%>					</a>
							</td>
							<td>&nbsp;</td>
			<%			}
					}
			%>
					</tr>
			<%
				}
			}
			 %>
				<tr class="tabelaDetalheItem">
					<td width="20%" align="left"><strong>Liberado</strong></td>
					<td width="40%" align="left">
						<a href="javascript:linkDetalhado('datasLimites/datasLimites.jsp')">
					<%	StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
						if(ari.getStatusRelatorioSrl().equals(statusLiberado)) {
							out.print("Sim");
						} else {
							out.print("Não");
						}
					%>	</a>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<!-- termina de imprimir tabela com dados gerais do item -->
			<br>
			
			<!-- começa a imprimir tabela com os relatorios de acompanhamento -->
			<table class="tabelaDetalheItem" width="100%" cellpadding="0" cellspacing="0">
				<% out.println(imprimeFuncTipoAcomp); %>
			</table>
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
</html>