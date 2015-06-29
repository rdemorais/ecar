<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="ecar.dao.TipoFuncAcompDao"/>
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/>
<jsp:directive.page import="ecar.pojo.UsuarioAtributoUsua"/>
<jsp:directive.page import="ecar.pojo.Cor"/>
<jsp:directive.page import="ecar.dao.CorDao"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.dao.PontoCriticoDao"/>
<jsp:directive.page import="java.util.Collection"/>
<jsp:directive.page import="ecar.pojo.StatusRelatorioSrl"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.dao.AcompRealFisicoDao"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaPrevisaoDao"/>
<jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<jsp:directive.page import="ecar.pojo.TipoAcompanhamentoTa"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoPtc"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{  
	
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
	ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	CorDao corDao = new CorDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
	ValidaPermissao validaPermissao = new ValidaPermissao();

	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && "S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	if(!"".equals(strCodAri)) {
		acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
	String strCodRegd =  Pagina.getParamStr(request, "codRegd");

	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String periodo = Pagina.getParamStr(request, "periodo");
 	String funcaoSelecionada = "RESUMO";
	
%>


<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
 
<script language="javascript">
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.forms[0].clicouAba.value = "S";
	document.form.submit();
}
function recarregar(){
	document.forms[0].action = "detalharItem.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri, codRegd){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].codRegd.value = codRegd;
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}


</script>
 
</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body>
<div id="conteudo"> 

	<%@ include file="../../form_visualizar.jsp" %>
	
<!--  %@ include file="/titulo_tela.jsp"%-->
<form  name="form" method="post" >	

<%

%>



<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="linkControle" value="S">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="codRegd" value="">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<!-- Campos para manter a seleção em Posição Geral -->

<input type="hidden" name="hidAcao" value="">

<div id="subconteudo">

	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			&nbsp;
			</td>
		</tr>
	</table>

	<%             
	
			// Configuração	
			//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
			String pathRaiz = configuracao.getRaizUpload();	
	
			TipoAcompanhamentoTa tipoAcompanhamento = acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa();
			List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
		                                                                 
            boolean usuarioLogadoEmiteParecer = false;
        	Boolean permissaoAlterar =  new Boolean(false);
            boolean primeiro = true;
            Date dataLimiteImprimir = new Date();
            ItemEstUsutpfuacDao iettEstUsuTpfaDao = new ItemEstUsutpfuacDao(request);
            //monta a variavel que imprime as funcoes de acompanhamento na ultima tabela da pagina
            //e guarda a data limite maior
            StringBuffer imprimeFuncTipoAcomp = new StringBuffer();
            List relatoriosDesordenados = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(acompReferenciaItem, tpfaOrdenadosPorEstrutura);
            
            //Ordena de acordo com a Data Limite das funções de acompanhamento (As funções com datas mais próximas serão exibidas primeiro);
            List relatorios = acompReferenciaItemDao.ordenaRelatorioFuncaoAcompByDataLimite(relatoriosDesordenados);
            Iterator itRelatorios = relatorios.iterator();
            while(itRelatorios.hasNext()) { 
                String imagePath = "";
                String aval = "";
                String linkParecer = "";
                String labelDtliberado = "";
                boolean imageError = false;
                
                
                AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
                
                if (listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())
                		&& validaPermissao.permissaoLeituraAcompanhamento(relatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
                	
                	UsuarioUsu usuarioImagem = null;  
            		String hashNomeArquivo = null;
                	
	                TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );        
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
									listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
								} else{
									if(relatorio.getTipoFuncAcompTpfaUsuario()!=null){
										ItemEstUsutpfuacIettutfa itemEstUsuSuperior = 
											iettEstUsuTpfaDao.buscar(acompReferenciaItem.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
										if(itemEstUsuSuperior!=null && itemEstUsuSuperior.getUsuarioUsu()!=null){
											usuarioLogadoEmiteParecer = itemEstUsuSuperior.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
											permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);								
										}
										if(!permissaoAlterar.booleanValue()){
											listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
										}
									} else{
										listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
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
	                if(usuarioLogadoEmiteParecer) {
	                    	linkParecer = "<a href=\"javaScript:trocaAba('../situacao/relatorios.jsp?tela=V'"+  " ,'" + strCodAri + "' ,'" + strCodRegd + "')\" >";
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
	                imprimeFuncTipoAcomp.append("<td width=\"40%\" align=\"left\">Data Limite: "+Data.parseDate(tipoFuncAcompDao.getDataLimiteDoTipoFuncaoDeAcomp(relatorio))+"</td>");
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
            
            // Iterator itAbas = lista.iterator();
            Iterator itAbas = abaDao.getListaAbasComAcesso(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(),  seguranca.getGruposAcesso()).iterator();
            
            //para cada aba imprime o label e o valor
            boolean abaSituacao = false;
            while (itAbas.hasNext()) {
                Aba aba = (Aba) itAbas.next();
                if(!aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
                
					String labelAba = aba.getLabelAba();
					boolean mostraAba = false;
					boolean existeFuncao = false;
					
					//Exibe o label das funções de acordo com o que foi cadastrado na tela "Exibição de Abas". Caso a aba faça referência a uma função e a função exista para
			        //a estrutura, o sistema irá utilizar o label da função que está associada à estrutura em questão.
			        if(aba.getFuncaoFun()!= null){
			        	Set listaFuncoes = acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
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
			                        if(aba.getNomeAba().equals("DATAS_LIMITES")) {                        	
			            %>              <td width="20%" align="left"><strong><%=labelAba%></strong></td>
			            <%          } else if(!aba.getNomeAba().equals("RESUMO")){
			            %>              <td width="20%" align="left"><strong><%=labelAba%></strong></td>
			            <%          } 
			                        if(aba.getNomeAba().equals("DADOS_GERAIS")) {  
			                        	
			            %>             
			            				<td width="40%" align="left"><a href="javascript:trocaAba('../dadosGerais/dadosGerais.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
			            					<%=itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(itemEstrutura, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt())%>
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
			            <%          } else if(aba.getNomeAba().equals("PONTOS_CRITICOS")) {
			                            PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			                            Collection todosPontosCriticos = pontoCriticoDao.getAtivos(itemEstrutura);
			                            Collection pontosCriticosNaoSolucionados = pontoCriticoDao.getPontosCriticosNaoSolucionados(itemEstrutura);
			                            if(pontosCriticosNaoSolucionados != null && pontosCriticosNaoSolucionados.size() > 0) {
			            %>                  <td width="40%" align="left"><a href="javascript:trocaAba('../../restricoes/pontosCriticos.jsp?tela=V','<%=strCodAri%>','<%=strCodRegd%>');"><%=pontosCriticosNaoSolucionados.size() + " " + abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt())  + " Não Solucionados"%></a></td>
			            <%              } else if(todosPontosCriticos != null && todosPontosCriticos.size() > 0) {
			            %>                  <td width="40%" align="left"><a href="javascript:trocaAba('../../restricoes/pontosCriticos.jsp?tela=V','<%=strCodAri%>','<%=strCodRegd%>');"><%="Todos " + abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt()) + " Foram Solucionados"%></a></td>
			            <%              } else {
			            %>                  <td width="40%" align="left"><a href="javascript:trocaAba('../../restricoes/pontosCriticos.jsp?tela=V','<%=strCodAri%>','<%=strCodRegd%>');"><%="Sem " + abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt())%></a></td>
			            <%              }
			            %>              <td>&nbsp;</td>
			            <%
			                        } else if(aba.getNomeAba().equals("SITUACAO_DATAS")) { 
			            %>              <td width="40%" align="left">
			                                <a href="javascript:trocaAba('../situacaoDatas/situacaoDatas.jsp','<%=strCodAri%>','<%=strCodRegd%>')">Consultar</a>
			                            </td>
			                            <td>&nbsp;</td>
			            <%          } else if(aba.getNomeAba().equals("GALERIA")) {
			            %>              <td width="40%" align="left"><a href="javascript:trocaAba('../galeria/galeria.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
			            <%              if(itemEstrutura.getItemEstrutUploadIettups() != null && itemEstrutura.getItemEstrutUploadIettups().size() > 0) {
			                                out.print(itemEstrutura.getItemEstrutUploadIettups().size() + " Anexos Disponíveis");
			                            } else {
			                                out.print("Nenhum Arquivo Anexado");
			                            }
			            %>              </a></td>
			                            <td>&nbsp;</td>
			            <%          } else if(aba.getNomeAba().equals("REL_FISICO_IND_RESULTADO")) {
			                            List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompReferenciaItem ,Dominios.TODOS, true);
			            %>              <td width="40%" align="left">
			                            <a href="javascript:trocaAba('../resultado/indicadoresResultado.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
			            <%              if(indResultados != null && indResultados.size() > 0) {
			                                out.print("Informado");
			                            } else {
			                                out.print("Não Informado");
			                            }
			            %>              </a></td>
			                            <td>
			                                <% if(dataLimiteImprimir != null && !dataLimiteImprimir.equals(new Date())) {
			                                    out.print(Data.parseDate(dataLimiteImprimir));
			                                   } else {
			                                    out.print("Nenhuma data limite foi informada.");
			                                   }
			                                %>
			                            </td>
			            <%          } else if(aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
			            %>              <td width="40%" align="left">
			                                <a href="javascript:trocaAba('../situacaoIndicadores/situacaoIndicadores.jsp','<%=strCodAri%>','<%=strCodRegd%>')">Consultar</a>
			                            </td>
			                            <td>&nbsp;</td>
			           <%          } else if(aba.getNomeAba().equals("FINANCEIRO")) { %> 
			                            <td width="40%" align="left">
			                                <a href="javascript:trocaAba('../financeiro/financeiro.jsp','<%=strCodAri%>')">
											<%ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
												List listaExercicios = itemEstPrevDao.getListaExerciciosItemEstruturaPrevisao(acompReferenciaItem.getItemEstruturaIett());
												if (listaExercicios.size()==0){
													%>Nenhum Dado Cadastrado<%
												}else{
			                                		%>Consultar<%
			                                	}%>
											</a>
			                            </td>
			                            <td>&nbsp;</td>
			            <%          } else if(aba.getNomeAba().equals("ETAPA")) { 
			                            List estruturaEtapas = estruturaDao.getEstruturasEtapas(acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
			                            String linkEtapas = "";
			                            if(estruturaEtapas != null && estruturaEtapas.size() > 0) {
			                                linkEtapas = "Informado";
			                            } else {
			                                linkEtapas = "Não Informado";
			                            }
			            %>              <td width="40%" align="left">
			                                <a href="javascript:trocaAba('../etapas/etapas.jsp','<%=strCodAri%>')"><%=linkEtapas%></a>
			                            </td>
			                            <td>&nbsp;</td>
			            <%          } else if(aba.getNomeAba().equals("DATAS_LIMITES")) { %> 
			                            <td width="40%" align="left">
			                               <a href="javascript:trocaAba('../datasLimites/datasLimites.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
			                    			<%  StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
			                        			if(acompReferenciaItem.getStatusRelatorioSrl().equals(statusLiberado)) {
			                            			out.print("Liberado");
			                        			} else {
			                            			out.print("Não Liberado");
			                        			}
			                    			%>
			                    			</a>
			                            </td>
			                            <td>&nbsp;</td>
			            <%          } else if(aba.getNomeAba().equals("EVENTOS")) { %> 
			                            <td width="40%" align="left">
			                                <a href="javascript:trocaAba('../eventos/eventos.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
			            <%                  if(acompReferenciaItem.getItemEstruturaIett().getItemEstrutAcaoIettas() != null && acompReferenciaItem.getItemEstruturaIett().getItemEstrutAcaoIettas().size() > 0) {
			                                    out.print("Informado");
			                                } else {
			                                    out.print("Não Informado");
			                                }
			            %>                  </a>
			                            </td>
			                            <td>&nbsp;</td>
			            <%          } else if (aba.getNomeAba().equals("GRAFICO_DE_GANTT")) {
						            	Date dataAtual = Data.getDataAtual();
										String dataBaseStr = Data.getAno(dataAtual) + "/" + 
											(Data.getMes(dataAtual)+1) + "/" +
											Data.getDia(dataAtual);
										Date dataBase = new Date(dataBaseStr);
										boolean pontosCriticosSolucionados = false;
										if (acompReferenciaItem.getItemEstruturaIett().getPontoCriticoPtcs() != null && acompReferenciaItem.getItemEstruturaIett().getPontoCriticoPtcs().size() > 0){					
											Iterator itPontosCriticos = acompReferenciaItem.getItemEstruturaIett().getPontoCriticoPtcs().iterator();
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
						<%				if (pontosCriticosSolucionados) {
	      				%>					<a href="javascript:trocaAba('../graficoGantt/graficoGantt.jsp','<%=strCodAri%>','<%=strCodRegd%>')">
	      				<%					out.print("Visualizar");
	      				%>					</a>
	      				<%				} else {
	      									out.print("Não há dados cadastrados para a data base selecionada.");
	      								}
	       				%>				</td>
	       								<td>&nbsp;</td>
	       				<% 			}
		                    }else{                    
		                    	if (mostraAba){
		                        	abaSituacao = true;
		                        }
		                    }
						}
	            %>
	                    </tr>
	            <%
	                }
                }
            }
             %>
                
            </table>
            <!-- termina de imprimir tabela com dados gerais do item -->
            <br>
            
            <!-- começa a imprimir tabela com os relatorios de acompanhamento -->

        <%  if(abaSituacao){%>
                <table class="tabelaDetalheItem" width="100%" cellpadding="0" cellspacing="0">
                    <% out.println(imprimeFuncTipoAcomp); %>
                </table>
        <%  }%>

</div>
</form>
<br>
</div>
</body>

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

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>

</html>