<%@page import="ecar.api.facade.Estrutura"%>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="ecar.servlet.geraFilhosIettCadastro.ItemEstruturaCadastroHtml"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/ativarSortTable.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/sorttable.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script language="javascript" src="../../js/cookie.js"></script>

<script language="javascript">
var windowReference = null;

<%
String radConcluidoSession = (String) request.getSession().getAttribute("radConcluidoSession");
%>

window.setInterval("ordenacaoColunasListener()", 1000);

function aoClicarIncluirItem(form, codEtt){
	form.hidAcao.value = 'incluir';
	form.codEtt.value = codEtt;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_inc.jsp";
	form.submit();
}

function aoClicarExcluirItem(form, nomeCheckBox){
	if(validarExclusao(form, nomeCheckBox)){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.nomeCheckBox.value = nomeCheckBox;
		form.action = "ctrl.jsp";
		form.submit();	
	}
}	

function aoClicarImprimirItem(form, estrutura, itemPai){
	form.codEttImprimir.value = estrutura;
	form.codIettPaiImprimir.value = itemPai;
	//form.action = "<%=_pathEcar%>/cadastroItens/relatorios/frm_rel.jsp?imprimirEstrutura=S";
	form.action = "<%=_pathEcar%>/cadastroItens/relatorios/frm_rel.jsp";
	form.submit();
}	

function aoClicarConsultarItem2(form, codIett, idLinha){
	form.hidAcao.value = 'consultar';
	form.codIett.value = codIett;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_con.jsp";
	form.submit();
}

function gerarArquivos() {
	form.hidAcao.value = "exportar";
	form.action = "geracaoArquivos.jsp";
	form.submit();
}

function aoClicarExibir(form){
	form.action = "lista.jsp";
	form.submit();
}

function abrirPopup() {
	//window.open("popUpPesquisa.jsp","", 'width=600, height=600,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,scrollbars=yes');
	windowReference = window.open("ctrl.jsp?hidAcao=abrirPopUp","", 'width=600, height=600,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,scrollbars=yes');
	windowReference.opener = self;
	document.getElementById("popupoverlay").style.display='block';
}

function aoClicarDesassociarItem(form) {

	var estruturas = document.getElementsByName('itemSelecionado');
	var marcou = false;	

	
	for ( var i = 0; i < estruturas.length; i++) {
		
		if (estruturas[i].checked){
			marcou = true;
			break;
		}
	}

	if (marcou) {
		form.hidAcao.value = 'desassociar';
		form.action='ctrl.jsp';
		form.submit();
	} else {
		alert('Selecione pelo menos um item para desassociar.');
	}

}

function aoClicarExibirFiltro(form){
	form.action = "lista.jsp";
	form.recarregarArvore.value = 'Sim';
	form.ultimoIdLinhaDetalhado.value = '';
	form.hidFuncaoAjaxSelecionada.value = '';
	
	//if (document.getElementsByName('codIettPrincipal').length > 0){
	//	form.codIettPrincipal.value = '';
	//}
	if (document.getElementsByName('ultEttSelecionado').length > 0){
		form.ultEttSelecionado.value = '';
	}
	form.ultimoIdLinhaExpandido.value='';
	
	form.submit();
}

function habilitar(){ 
	document.getElementById("popupoverlay").style.display='none';	
}



</script>

</head>

<c:set value="<%=request.getParameter("msg")%>" var="msgConfirmacao"/>
<c:choose>
	<c:when test="${msgConfirmacao == null}">
		<body>
	</c:when>
	<c:otherwise>
		<body onload="alert ('${msgConfirmacao}')">
	</c:otherwise>
</c:choose>

<div id="popupoverlay"></div>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<%@ include file="../../include/exibirAguarde.jsp"%>

<div id="conteudo">
<% 
String ultimoIdLinhaDetalhadoLista = "";
try {
EstruturaDao estruturaDao = new EstruturaDao(request);
ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
if (Pagina.getParam(request, "titulo") != null) {
	session.setAttribute("titulo", Pagina.getParam(request, "titulo"));
}

%>	
	
	<div id="tituloTelaCadastro">
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%> 
	</div>
	
	<form name="form" method="post">
		
	<input type="hidden" name="ehTelaListagem" id="ehTelaListagem" value="S">
	<input type="hidden" name="parametroIncluir" id="parametroIncluir" value="">
	<input type="hidden" name="parametroExcluir" id="parametroExcluir" value="">
	<input type="hidden" name="parametroImprimir" id="parametroImprimir" value="">
	<input type="hidden" name="parametroGerarArquivos" id="parametroGerarArquivos" value="">
	<input type="hidden" name="parametroAssociarItem" id="parametroAssociarItem" value="">
	<input type="hidden" name="parametroDesassociarItem" id="parametroDesassociarItem" value="">
	<input type="hidden" name="recarregarArvore" id="recarregarArvore" value="">
		
<%	
	
	
	String radConcluido = "";
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	radConcluido = Pagina.getParamStr(request, "radConcluido");
	
	if ((radConcluidoSession == null || radConcluidoSession.equals("")) && (radConcluido == null || radConcluido.equals(""))){
		radConcluido = configuracao.getExibDefaultEstCfg();
	}
	
	if(radConcluido != null && !"".equals(radConcluido)){
		request.getSession().setAttribute("radConcluidoSession", radConcluido);
	} else if(radConcluidoSession != null && !"".equals(radConcluidoSession)){
		radConcluido = radConcluidoSession;
	}
	
	if("".equals(radConcluido) || radConcluido == null)
		radConcluido = configuracao.getExibDefaultEstCfg();		
%>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="linha_subtitulo_estrutura">
			<td align="left" valign="top">				
			</td>
			<td align="right" valign="top">Exibir:
				<input type="radio" class="form_check_radio" name="radConcluido" value="T" onclick="aoClicarExibirFiltro(form)" <%=Pagina.isChecked(radConcluido, "T")%>> Todos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="N" onclick="aoClicarExibirFiltro(form)"  <%=Pagina.isChecked(radConcluido, "N")%>> Não Concluídos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="C" onclick="aoClicarExibirFiltro(form)" <%=Pagina.isChecked(radConcluido, "C")%>> Concluídos				
			</td>					
		</tr>
	</table>
	
	

	<!-- Menu Cadastro -->

	<%
	
	
	boolean ehTelaListagem = true;
	EstruturaEtt estruturaEttSelecionada = null;
	EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);
	
	estruturaEttSelecionada = (EstruturaEtt) estruturaDaoArvoreItens.getEstruturaPrincipal().get(0); //lista das estruturas raizes
	
	//ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	
	if(configuracao.getIndExibirArvoreNavegacaoCfg() != null && configuracao.getIndExibirArvoreNavegacaoCfg().equals("S")){
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
	<%ultimoIdLinhaDetalhadoLista = ultimoIdLinhaDetalhado;%>
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>
			 
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(configuracao.getIndExibirArvoreNavegacaoCfg() != null && configuracao.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div id="conteudoCadastroEstruturaSemArvore">
			<%
			}
			%>
		
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (configuracao.getIndExibirArvoreNavegacaoCfg() != null && configuracao.getIndExibirArvoreNavegacaoCfg().equals("S")) {
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
	
						
						<%
						ItemEstruturaCadastroHtml itemEstruturaCadastroHtml = new ItemEstruturaCadastroHtml (null, estruturaEttSelecionada, request);
						
						%>
						<!-- Árvore de Localização -->	
						<div id="arvoreLocalizacao">
							<%if(ultimoIdLinhaDetalhadoLista.equals("")){
								out.println(itemEstruturaCadastroHtml.geraConteudoArvoreLocalizacao("estrutura").replace("\\", ""));
							}	
							%>	
						</div>
						<div id="abasEstrutura">
							<%if(ultimoIdLinhaDetalhadoLista.equals("")){
								out.println(itemEstruturaCadastroHtml.geraConteudoAbasEstrutura("estrutura", true).replace("\\", ""));
							}	
							%>
						</div>
						<%//Verifica a permissão para os botões de adicionar, excluir, imprimir, gerar arquivos, associar e desassociar
						ValidaPermissao validaPermissao = new ValidaPermissao();
						List lColunas = null;			
						List listaItensEstrutura = null; //Lista de ItemEstruturas filhas		
						//Regras de visualização dos botões "Adicionar", "Excluir" e 
						//"Imprimir" na tela de listagem do cadastro.
						String permissaoVisualizarBotaoIncluirItem = "none";
						String permissaoVisualizarBotaoExcluirItem = "none";
						String permissaoVisualizarBotaoImprimirItem = "none";
						String permissaoVisualizarBotaoGerarArquivos = "none";		
						String permissaoVisualizarBotaoAssociarItem = "none";
						String permissaoVisualizarBotaoDesassociarItem = "none";
						
						if(ultimoIdLinhaDetalhadoLista.equals("")){
							lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaEttSelecionada);
									
							if(lColunas != null && lColunas.size() > 0) {				
								listaItensEstrutura = itemDao.getItensFilho(null, estruturaEttSelecionada, lColunas);
							}
							else {
								listaItensEstrutura = itemDao.getItensFilho(null, estruturaEttSelecionada, "");
							}
							
							listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);
									
									//Verifica as permissões				
							if(estruturaEttSelecionada.isVirtual()){
								if(validaPermissao.permissaoAdicionarItem(estruturaEttSelecionada, seguranca.getGruposAcesso())){
									permissaoVisualizarBotaoAssociarItem = "";
								}
								if(validaPermissao.permissaoAdicionarItem(estruturaEttSelecionada, seguranca.getGruposAcesso())){
									permissaoVisualizarBotaoDesassociarItem = "";
								}
								request.getSession().setAttribute("estruturaVirtual",estruturaEttSelecionada);
							}else{
								if(validaPermissao.permissaoAdicionarItem(estruturaEttSelecionada, seguranca.getGruposAcesso())){
									permissaoVisualizarBotaoIncluirItem = "";
								}
								if((listaItensEstrutura!=null && listaItensEstrutura.size()>0)){
									permissaoVisualizarBotaoExcluirItem = "";
								}
								request.getSession().removeAttribute("estruturaVirtual");
							}
							
							if(validaPermissao.permissaoImprimirListagem(estruturaEttSelecionada, seguranca.getGruposAcesso()) && estruturaEttSelecionada.getIndExibirImprimirListagem().equals("S")){
								permissaoVisualizarBotaoImprimirItem = "";
							}
							
							if(validaPermissao.permissaoGerarArquivos(estruturaEttSelecionada, seguranca.getGruposAcesso()) && "S".equals(estruturaEttSelecionada.getIndExibirGerarArquivos())){
								permissaoVisualizarBotaoGerarArquivos = "";
							}
				
						}
						%>
						
												
						<!-- Nome da estrutura selecionada -->
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr id="trNomeEstrutura" class="linha_titulo_estrutura" bgcolor="<%=estruturaEttSelecionada.getCodCor1Ett()%>" cellpadding="0" cellspacing="0">
								<td id="nomeEstrutura" colspan="2">
									<%
									out.println(estruturaEttSelecionada.getNomeEtt());
									%>
								</td>
							</tr>	
												
						<!-- Barra de botões -->
						 			
						 	<%
							String nomeCbDep = "cbDep" + estruturaEttSelecionada.getCodEtt();
							%>
						 									
							<tr id="barraBotoes" class="linha_titulo_estrutura"  bgcolor="<%=estruturaEttSelecionada.getCodCor1Ett()%>" cellpadding="0" cellspacing="0">
								<td>
									<input type="button" id="botaoIncluirItem" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluirItem(document.form, <%=estruturaEttSelecionada.getCodEtt()%>)" style="display:<%=permissaoVisualizarBotaoIncluirItem%>"> 
									<input type="button" id="botaoExcluirItem" value="Excluir" class="botao" onclick="javascript:aoClicarExcluirItem(document.form, '<%=nomeCbDep%>')" style="display:<%=permissaoVisualizarBotaoExcluirItem%>">
									<input type="button" id="botaoAssociarItem" value="Associar" class="botao" onclick="javascript:abrirPopup();" style="display:<%=permissaoVisualizarBotaoAssociarItem%>">
									<input type="button" id="botaoDesassociarItem" value="Desassociar" class="botao" onclick="javascript:aoClicarDesassociarItem(this.form);" style="display:<%=permissaoVisualizarBotaoDesassociarItem%>">
								</td>
								<td align="right">						
									<input type="button" id="botaoGerarArquivos" value="Gerar Arquivos" class="botao" onclick="javascript:gerarArquivos()" style="display:<%=permissaoVisualizarBotaoGerarArquivos%>">						
									<input type="button" id="botaoImprimirItem" value="Imprimir" class="botao" onclick="javascript:aoClicarImprimirItem(document.form, <%=estruturaEttSelecionada.getCodEtt()%>, 0);" style="display:<%=permissaoVisualizarBotaoImprimirItem%>">
									<input type="hidden" name="imprimirEstrutura" value="S">
								</td>
							</tr>
						</table>
						<!-- Cabeçalho de título, só para lista de estruturas -->
						<!-- table border="0" cellpadding="0" cellspacing="0" width="100%"> 				
							<tr id="tituloCabecalho" class="linha_titulo">
								<td width="1%">&nbsp;</td>
								<td align="left">
									<font> </font> 
								</td>
							</tr>
						</table>
						
						

						<!-- Detalhamento dos itens clicados na árvore -->
						<!-- Preenche inicialmente esse DIV com a árvore de estruturas -->
						<div id="detalharArvoreItens">							
							<!-- Último item detalhado na árvore de cadastro. Inicialmente é vazio, depois fica sendo atualizado 
							pelo Ajax (classe ItemEstruturaCadastroHtml) -->
							<!-- input type="hidden" name="ultimoIdLinhaDetalhado" id="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado %>"--> 
							<!-- Os hiddens "codIettPrincipal" e "ultEttSelecionado" são atualizados via Ajax-->
							<!-- input type="hidden" name="codIettPrincipal" value=""> -->
							
							
							<%if (estruturaEttSelecionada != null && ultimoIdLinhaDetalhadoLista.equals("")){
								if (estruturaEttSelecionada.isVirtual()){
									out.println(itemEstruturaCadastroHtml.geraConteudoVirtualDetalharArvoreItens("estrutura").replace("\\", ""));
								} else {
									out.println(itemEstruturaCadastroHtml.geraConteudoIettDetalharArvoreItens("estrutura").replace("\\", ""));
								}
							}
							%>
							<!-- fim árvore -->						
						</div>
				
					</td>
				</tr>
				</table>

		</div>	<!-- fim conteudoCadastroEstrutura -->
		</td></tr>
		</table>		
		<input type="hidden" name="ultEttSelecionado" value="">		
		<input type="hidden" name="codEttImprimir" value="">
		<input type="hidden" name="codIettPaiImprimir" value="">
		<input type=hidden name="hidAcao" value="">
		<!-- usado na inclusao de um item de nivel 1 -->
		<input type=hidden name="codEtt" value="">
		<!-- usado na consulta de um item -->
		<input type=hidden name="codIett" value="">		
		<input type=hidden name="nomeCheckBox" value="">	
		
		<!-- usado para guardar o código da função de Dados Gerais. Merge da parte Milton. -->
		<input type="hidden" name="codAba" value="<%=new FuncaoDao(request).getCodFuncaoDadosGerais()%>">		
		
		<!-- Guarda os hiddens com os filhos de cada nó. -->
		<div id="hiddenFilhosExibir">
		</div>
		
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

<%

//Recupera o último item detalhado
if(!ultimoIdLinhaDetalhadoLista.equals("") ){ %>
	<script language="javascript">		
	aoClicarDetalharItem(form, '<%=ultimoIdLinhaDetalhadoLista%>' );
	</script>						
<%
}
%>