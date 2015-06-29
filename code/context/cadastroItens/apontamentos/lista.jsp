
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ApontamentoApt" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.ApontamentoDao" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
  
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %> 
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@page import="ecar.dao.EstruturaDao"%><html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_APONTAMENTO);
%>

<script>

function aoClicarBtn2(form){
	if(validarExclusao(form, "excluir")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.apontamentos.exclusao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
		form.submit();
	}
}

</script>


<script language="javascript">

function aoClicarConsultar(form, cod){
	form.cod.value = cod;
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 

function mudaTela(cod){
	document.form.cod.value = cod;
	//document.form.action = "../pontosCriticos/frm_con.jsp";
	document.form.action = "../pontosCriticos/lista.jsp";
	document.form.submit();	
}


</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body> 
<div id="conteudo"> 

<% 
try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	String codIettLista = Pagina.getParamStr(request, "codIett");
	ItemEstruturaIett itemEstrutura = null;
	if(codIettLista!=null && !codIettLista.equals("")) {
		itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettLista));
	}

	if ( !validaPermissao.permissaoConsultaIETT(Long.valueOf(codIettLista), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	PontoCriticoPtc pontoCritico = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "codPtc")));
	ApontamentoDao apontamentoDao = new ApontamentoDao(request);
	
	String codAba = Pagina.getParamStr(request, "codAba");

	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoApontamento);
	
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


//ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

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
								<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
							<!-- ############### Árvore de Estruturas  ################### -->
							
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>" 
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
								contextPath="<%=request.getContextPath()%>"
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado")%>"
								/>
							<!-- ############### Barra de Links  ################### -->
							
							<br>
							<div id="subconteudo">							 
							
							<input type="hidden" name="autorizarMail" value="N">
							<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
							<input type=hidden name="hidAcao" value="">
							<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
							<input type=hidden name="codPtc" value="<%=pontoCritico.getCodPtc()%>">
							<input type=hidden name="cod" value=""> 
							<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
							
							<!-- ############### Cabeçalho de Restrições  ################### -->
							<%@ include file="cabecalho.jsp"%>
							
							 
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_titulo" align="right">
									<td>
										<%if(permissaoAlterar.booleanValue()){%>
											<input type="button" value="Adicionar " class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
											<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarBtn2(document.form)">
										<%}%>
									</td>
								</tr>
							</table>
								<!-- ############### Listagem  ################### -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador" colspan=4><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_subtitulo">
									<td width="5%">
										<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
									</td>
									<td>Data</td>
									<td>Autor</td>
									<td>Texto</td>
								</tr>
								<tr><td class="espacador" colspan=4><img src="../../images/pixel.gif"></td></tr>
								<%
									/* Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela */
									Set apontamentos = pontoCritico.getApontamentoApts();
									if(apontamentos != null ){ 
												Iterator itApt = apontamentos.iterator();
												while(itApt.hasNext()){
													ApontamentoApt apontamento = (ApontamentoApt) itApt.next();
													%>
													<tr class="linha_subtitulo2"> 
														<td>
															<input type="checkbox" class="form_check_radio" name="excluir" value="<%=apontamento.getCodApt()%>">
														</td>
														<td>	
															<a href="javascript:aoClicarConsultar(document.form,<%=apontamento.getCodApt()%>)">
															<%=Pagina.trocaNullData(apontamento.getDataInclusaoApt())%></a>
														</td>
														<td><%=apontamento.getUsuarioUsu().getNomeUsuSent()%></td>																	
														<td><%=apontamento.getTextoApt()%></td>										
													</tr>	
													<%
												}  
											}			
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
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table> 
 



<table>
</div>
<br>
</div>

</form>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>
