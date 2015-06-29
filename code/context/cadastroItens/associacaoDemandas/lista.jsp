
<jsp:directive.page import="ecar.pojo.ObjetoDemanda"/>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/>
<jsp:directive.page import="ecar.pojo.DemAtributoDema"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.dao.VisaoDao"/>
<jsp:directive.page import="ecar.pojo.VisaoDemandasVisDem"/>
<jsp:directive.page import="ecar.pojo.VisaoDemandasGrpAcesso"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.UsuarioDao" %> 

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="comum.util.Data" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">
function getDadosPesquisa(codigo, descricao){
	document.form.codRegd.value = codigo;
	document.form.hidAcao.value = "incluir";
	document.form.action = "ctrl.jsp";
	document.form.submit();
}

function popup_pesquisaDemanda(pojo, funcaoGetDadosPesquisa, parametros) {
	var jaIncluidos = "";
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	
	while (i < el.length) {		
		if (el.item(i) != null) {
			if (el.item(i).type == "checkbox" && el.item(i).name == "excluir") {
				n++;
			}
		}
		i++;
	}
	
	var total = 0;
	if (n > 0) {
		if (n > 1) {
			total = document.form.excluir.length;
			
			i = 0;
			for (i = 0; i < total; i++) {
				jaIncluidos += document.form.excluir[i].value + "|";
			}
		} else {
			total = 1;
			jaIncluidos += document.form.excluir.value + "|";
		}
	}
	
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';
    if (jaIncluidos != "")
		return abreJanela('popup_pesquisaRegDemanda.jsp?hidPojo=' + pojo + '&jaIncluidos=' + jaIncluidos + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
	else
		return abreJanela('popup_pesquisaRegDemanda.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
}

function aoClicarConsultar(codigo){
	form.codRegd.value = codigo;
	form.action = "<%=_pathEcar%>/demandas/registro/frm_cons.jsp";
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
</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body> 
<div id="conteudo"> 

<% 
try{

/* lista de colunas de uma estrutura */
	List lColunas = new ArrayList();
/* uma coluna de uma estrutura */
	ObjetoDemanda coluna;
/* Iterador de colunas  da estrutura */
	Iterator itColunas;
/* Número de atributos demandas para formar o número de colunas */
	int numColunas = 0;

	boolean visaoAssociacaoDemandasConfigurada = true;
	boolean usuarioPermissaoVisaoConfigurada = true;
	
	AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
	VisaoDao visaoDao = new VisaoDao(request);
	//ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();
	//Verifica se tem alguma visão configurada para associação de demandas
	if (configuracaoCfg.getVisaoDemandasVisDem() != null) {
		if (visaoDao.getVisoesGrupoAcesso(seguranca.getUsuario(), true, request).contains(configuracaoCfg.getVisaoDemandasVisDem())){
			lColunas = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaListaDemandas(configuracaoCfg.getVisaoDemandasVisDem().getCodVisao());
			numColunas = lColunas.size();	
		} else {
			usuarioPermissaoVisaoConfigurada = false;
			//Usuário sem permissão de acesso à visão. Favor entrar em contato com o administrador do sistema.
		}	
	} else {
		visaoAssociacaoDemandasConfigurada = false;
		//Falta configurar uma visão. Favor entrar em contato com o administrador do sistema.
	}
	
	String paramCodIett = "";
	String paramCod = "";
	String codAba = "";
	
	paramCodIett = Pagina.getParamStr(request, "codIett");
	paramCod = Pagina.getParamStr(request, "cod");
	codAba = Pagina.getParamStr(request, "codAba");			
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(paramCodIett));
	
	if ( !validaPermissao.permissaoConsultaIETT( Long.valueOf(paramCodIett) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">

<%
boolean ehTelaListagem = false;
 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);

if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){
	%>
	
	<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
	<script language="javascript" src="../../js/menu_cadastro.js"></script>	

	<%
	}else{
	%>
	
	<script language="javascript" src="../../js/menu_retratil.js"></script>
	<%
	}
	%>
<%@ include file="../arvoreItens.jsp"%>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
					<%} %>
						<td width="100%" valign="top">
							
							<!-- ############### Árvore de Estruturas  ################### -->
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
							<!-- ############### Árvore de Estruturas  ################### -->
							 
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>" 
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
								contextPath="<%=request.getContextPath()%>"
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
							<!-- ############### Barra de Links  ################### -->
						
							<br><br>
							
							
							
							<%
							if (!visaoAssociacaoDemandasConfigurada){
								%>
								<table width="100%">
								<tr>		
									<td valign="top" align="center">
										<h1><%=_msg.getMensagem("itemEstrutura.regDemanda.associacao.semVisaoConfigurada")%></h1>
									</td>
								</tr>				
								</table>
								<%
							} else if (!usuarioPermissaoVisaoConfigurada){
								%>
								<table width="100%">
								<tr>		
									<td valign="top" align="center">
										<h1><%=_msg.getMensagem("itemEstrutura.regDemanda.associacao.usuarioSemAcessoVisaoConfigurada")%></h1>
									</td>
								</tr>				
								</table>
								<%
							} else {
								
								List listaDemandasVisaoConfigurada = new RegDemandaDao(request).getDemandasComPermissaoNosGruposAcessosUsuario(seguranca.getUsuario(), configuracaoCfg.getVisaoDemandasVisDem(), false);
								
								%>
								
								<table width="100%">
								<tr>		
									<td valign="top" align="left">
										<h1> Visão: <%=configuracaoCfg.getVisaoDemandasVisDem().getDescricaoVisao()%> </h1>
									</td>
								</tr>				
								</table>
	
								<div id="subconteudo">
								 
								
								<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
								<input type=hidden name="hidAcao" value="">
								<input type=hidden name="codAba" value="<%=codAba%>">
								<input type=hidden name="codRegd" value="">
								<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%="S"%>">
								
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
								
								//Campos de ordenação da listagem%>
								<input type="hidden" name="clCampo" value="<%=clCampo%>">
								<input type="hidden" name="clOrdem" value="<%=clOrdem%>">
								<input type="hidden" name="refazOrdenacao" value="">
							 
							
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_titulo" align="right">
										<td>
											<%if(permissaoAlterar.booleanValue()){%>
												<input type="button" value="Adicionar" class="botao" onclick="popup_pesquisaDemanda('ecar.popup.PopUpRegDemanda');"> 
												<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
											<%}%>
										</td>
									</tr>
								</table>
									<!-- ############### Listagem  ################### -->
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td class="espacador" colspan=<%=numColunas+1%>><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_subtitulo">
								
									<%
											/* desenha as colunas de uma estrutura */
											itColunas = lColunas.iterator();
											int numColuna = 1;
											String nomeCbCtrl = "excluirAll";			
											String strCheckBox = "<td nowrap=\"nowrap\" width=\"1%\" align=\"left\"><input type=\"checkbox\" name=\"" + nomeCbCtrl + "\" onclick=\"javascript:selectAll(document.form, '" + nomeCbCtrl + "');\"></td>";
											String strColunaVazia = "<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
											
											//strCheckBox
											//strColunaVazia
											
											while (itColunas.hasNext()){
												coluna = (ObjetoDemanda) itColunas.next();
										%>
											<%=strCheckBox%>			
											<td width="<%=coluna.iGetLargura()%>%" align="left">
											<a href="#" onclick="this.blur(); return sortTable('corpo1',  <%=numColuna%>, false);">											
											<%numColuna++;%>
											<%=coluna.iGetLabel()%>
											</a>
											</td>
										<%
												strCheckBox = "";
											}
										%>
									
									</tr>
									<tr><td class="espacador" colspan=<%=numColunas+1%>><img src="../../images/pixel.gif"></td></tr>
								<%
									
									RegDemandaDao regDemandaDao = new RegDemandaDao(request);
									UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
									
									List listaItemDemandas = new ArrayList();
									
									Iterator itItemDemandas = itemEstrutura.getItemRegdemandaIregds().iterator();
									
								    listaItemDemandas.addAll(itemEstrutura.getItemRegdemandaIregds());
											
									if ("refaz".equals(Pagina.getParamStr(request, "refazOrdenacao"))) {
										// refaz a ordenação
										regDemandaDao.classificarOrdenacao(clCampo, clOrdem, listaItemDemandas);
										//request.getSession().setAttribute("listaRegistros", listaItemDemandas);
									}
									
									if(listaItemDemandas != null && listaItemDemandas.size() > 0){ 
									
									%>
										<tbody id="corpo1">
									<%
									
										int cont = 0;
										String cor = new String(); 
										Iterator it = listaItemDemandas.iterator();
										boolean exibirLink = false;
										configuracaoCfg.getVisaoDemandasVisDem().getVisaoSituacaoDemandas().size();
										request.getSession().setAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA, configuracaoCfg.getVisaoDemandasVisDem());
										while(it.hasNext()){
											if (cont % 2 == 0){
												cor = "cor_nao";
											} else {
												cor = "cor_sim"; 
											}
											
											RegDemandaRegd regDemanda = (RegDemandaRegd) it.next();
											
											if (listaDemandasVisaoConfigurada != null && listaDemandasVisaoConfigurada.contains(regDemanda)){
												exibirLink = true;
											} else {
												exibirLink = false;
											}
											
								%>
											<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" > 
												<td width="4%">
													<input type="checkbox" class="form_check_radio" name="excluir" value="<%=regDemanda.getCodRegd()%>">
												</td>
												
												<%
													itColunas = lColunas.iterator();
												
													while (itColunas.hasNext()){
													
														coluna = (ObjetoDemanda) itColunas.next();
												%>
												
												<td valign="top" align="left" width="<%=coluna.iGetLargura()%>%">
													<%
													String informacaoAtbdem = "";
													
													if (coluna.iGetGrupoAtributosLivres() != null)  {
															Iterator itRegDem =  regDemanda.getDemAtributoDemas().iterator();
															while (itRegDem.hasNext()) {
																DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem.next();
																
																if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
																	if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
																	 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
																	 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
																	 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
																	 
																		informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao() +  configuracaoCfg.getSeparadorCampoMultivalor();
																	
																	} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
																		//se for do tipo imagem não faz nada, deixa em branco.
																		informacaoAtbdem = informacaoAtbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() +  "; ";
																	} 
																} 
															}
															if (informacaoAtbdem.length() > 0){
																informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - 2); 
															}
															if (exibirLink){
																%><a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
																<%=informacaoAtbdem%>
																</a><%
															} else {
																out.println(informacaoAtbdem);
															}
														} else {
															
															if (coluna.iGetNome().equals("localDemandaLdems")){
																if (exibirLink){
																	%><a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
																	<%=coluna.iGetValoresCodFk(regDemanda)%></a>
																<%
																} else {
																	out.println(coluna.iGetValor(regDemanda));
																}
															}else{
															
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
																if (exibirLink){
														 %>
																<a href="javascript:aoClicarConsultar(<%=regDemanda.getCodRegd()%>);">
																<%=valor%></a>
														<%
																} else {
																	out.println(valor);
																}
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
										<%}%>
									</table>
								<%}%>
								
							</div>
			
						</td>
					</tr>
	
				</table>
			</div>
		</div>
	</td>
</tr>
</table>
	
</form>
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
	<!-- ############### Listagem  ################### -->
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

