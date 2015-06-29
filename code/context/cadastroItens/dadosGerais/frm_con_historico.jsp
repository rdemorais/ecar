
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.pojo.historico.HistoricoItemEstruturaIett"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %> 

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
 
<%@ page import="comum.util.Util" %>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/mascaraMoeda.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script>
function aoClicarVoltar(form){
	form.action = 'lista_historico.jsp';
	form.submit();	
} 
</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">


<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	String[] cods = Pagina.getParamArray(request, "codHist");

	HistoricoItemEstruturaIett historicoItemEstruturaIettAntes = null;
	HistoricoItemEstruturaIett historicoItemEstruturaIettDepois = null;

	if (Long.valueOf(cods[0]).doubleValue() > Long.valueOf(cods[1]).doubleValue()){
		historicoItemEstruturaIettAntes = itemEstruturaDao.getHistorico(Long.valueOf(cods[1]));
		historicoItemEstruturaIettDepois = itemEstruturaDao.getHistorico(Long.valueOf(cods[0]));
	} else {
		historicoItemEstruturaIettAntes = itemEstruturaDao.getHistorico(Long.valueOf(cods[0]));
		historicoItemEstruturaIettDepois = itemEstruturaDao.getHistorico(Long.valueOf(cods[1]));
	}
		
	String codAba = Pagina.getParamStr(request, "codAba");
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	
	estruturaFuncao = estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	EstruturaDao estruturaDao = new EstruturaDao(request);
	List atributosAntes = historicoItemEstruturaIettAntes.getAtributoEstrutura();// estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
	List atributosDepois = historicoItemEstruturaIettDepois.getAtributoEstrutura();//estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
%>

<div id="conteudo">

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form  name="form" method="post" >

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
							<util:arvoreEstruturas 
								itemEstrutura="<%=itemEstrutura%>" 
								contextPath="<%=_pathEcar%>" 
								seguranca="<%=seguranca%>"
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido") %>"
							/> 
							 
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>"
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
								contextPath="<%=request.getContextPath()%>"
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
							<br><br>

							
							<input type="hidden" name="hidAcao" value="">
							<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
							
							<input type="hidden" name="codEtt" value="<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>">
							
							<input type="hidden" name="codAba" value="<%=codAba%>">
							
							<input type="hidden" name="dtInicio" value="<%=Pagina.getParamStr(request, "dtInicio")%>">
							
							<input type="hidden" name="dtFim" value="<%=Pagina.getParamStr(request, "dtFim")%>">
							
							<!-- input type="hidden" name="tipos" value=""--> 
							 
							<!-- usado para guardar a estrutura detalhada na tela de cadastro -->
							<input type="hidden" name="codIettPrincipal" value="<%=Pagina.getParam(request, "codIettPrincipal")%>">
							<input type="hidden" name="ultEttSelecionado" value="<%=Pagina.getParam(request, "ultEttSelecionado")%>">	 
							<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>">
							
							
							<% /* Controla o estado do Menu (aberto ou fechado) */%>
							<%@ include file="../../include/estadoMenu.jsp"%>
							  
							<%
							_disabled = "disabled";
							_readOnly = "readonly";
							%>
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
								<tr class="linha_titulo" align="center">
									<td>
										<input type="button" class="botao" value="Voltar" onclick="javascript:aoClicarVoltar(form);">
									</td>
								</tr>
							</table>
							
							<table name="form" class="form" width="100%">
								<tr>
									<td valign="top">
										<table>
										<%@ include file="form_antes.jsp"%>
										</table>
									</td>
									<td class="espacador">
										<img src="<%=request.getContextPath()%>/images/pixel.gif">
									</td>
									<td valign="top">
										<table>
										<%@ include file="form_depois.jsp"%>
										</table>
									</td>
									
								</tr>
							</table>
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
								<tr class="linha_titulo" align="center">
									<td>
										<input type="button" class="botao" value="Voltar" onclick="javascript:aoClicarVoltar(form);">
									</td>
								</tr>
							</table>
							
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
				

			

<%
	
	
	Boolean permissaoAlterar = new Boolean(true);
	String _disabledCampo = "";
	String _readonlyCampo = "";
%>




</form>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div> <!-- conteudo -->
</body>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>