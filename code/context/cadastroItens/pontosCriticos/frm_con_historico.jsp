
<jsp:directive.page import="ecar.dao.EstruturaDao"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %> 

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
 
<%@ page import="comum.util.Util" %>


<%@page import="ecar.pojo.historico.HistoricoPontoCriticoPtc"%><html lang="pt-br">
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
<%@ include file="validacao.jsp"%>
function aoClicarVoltar(form){
	form.action = 'lista.jsp';
	form.submit();	
} 
function mudaTela(cod){
	document.form.codPtc.value = cod;
	document.form.action = "../apontamentos/lista.jsp";
	document.form.submit();	
}

function aoClicarVoltar(form){
	form.action = 'lista_historico.jsp';
	form.submit();	
} 

</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	String[] codigos = Pagina.getParamArray(request, "excluirPontoCritico");
	String[] tipos = Pagina.getParamArray(request, "tipos");
	String dataInicio = Pagina.getParamStr(request, "dtInicio");
	String dataFim = Pagina.getParamStr(request, "dtFim");


	
	String[] cods = Pagina.getParamArray(request, "codHist");
	PontoCriticoDao ptcDao = new PontoCriticoDao(null);
	
	HistoricoPontoCriticoPtc pontoCriticoAntes = ptcDao.getHistorico(Long.valueOf(cods[0]));
	HistoricoPontoCriticoPtc pontoCriticoDepois = ptcDao.getHistorico(Long.valueOf(cods[1]));
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	
	estruturaFuncao = estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	EstruturaDao estruturaDao = new EstruturaDao(request);
	List atributosAntes = pontoCriticoAntes.getAtributoEstrutura();// estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
	List atributosDepois = pontoCriticoDepois.getAtributoEstrutura();//estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
<form  name="form" method="post" >
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>"> 
<input type="hidden" name="codAba" value="<%=codAba%>">
<input type="hidden" name="hidAcao" value="">
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
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
 
							<util:barraLinksItemEstrutura 
										estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
										selectedFuncao="<%=codAba%>"
										codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
										contextPath="<%=request.getContextPath()%>"
										idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>																		
							
							<%
								
								
								Boolean permissaoAlterar = new Boolean(true);
								boolean alterarPontoCritico = false;
								String _disabledCampo = "";
								String _readonlyCampo = "";
							%>
							
							<br><br>
								
								<%if (codigos != null) for(int i = 0; i <  codigos.length; i++){%> 
									<input type="hidden" name="excluirPontoCritico" value="<%=codigos[i]%>">
								<%}%>
								<%if (tipos != null) for(int i = 0; i < tipos.length; i++){%> 
									<input type="hidden" name="tipos" value="<%=tipos[i]%>">
								<%}%>
								<input type="hidden" name="dtInicio" value="<%=dataInicio%>">
								<input type="hidden" name="dtFim" value="<%=dataFim%>">
								  
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
										<td td valign="top">
											<table>
											<%@ include file="form_antes.jsp"%>
											</table>
										</td>
										<td class="espacador">
											<img src="<%=request.getContextPath()%>/images/pixel.gif">
										</td>
										<td td valign="top">
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
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>