<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaSisAtributoIettSatb"/>
<jsp:directive.page import="ecar.dao.ExportacaoRelatorioItemEstruturaDao"/>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %> 
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="ecar.intercambioDados.montador.Montador"%>
<%@ page import="ecar.intercambioDados.ControladorIntercambioDados"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>


<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.AtributoLivre" %>
<%@ page import="ecar.taglib.util.Input"%>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@page import="ecar.servlet.geraFilhosIettCadastro.ItemEstruturaCadastroHtml"%>
<%@page import="ecar.bean.intercambioDados.TelaExportacaoBean"%>
<%@page import="ecar.bean.intercambioDados.CaminhoArquivoExportacaoBean"%>
<%@page import="ecar.intercambioDados.recurso.txt.RegistroTXT"%>
<%@page import="comum.database.DaoUtil"%>
<%@page import="ecar.pojo.PontoCriticoPtc"%>

<%@page import="ecar.intercambioDados.montador.Montador"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>	
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">


</script>

</head>

<body>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 
<div id="conteudo">
<% 		
try{

	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemPrincipal;
	EstruturaDao estruturaDao = new EstruturaDao(request);
	TelaExportacaoBean telaBean = new TelaExportacaoBean();
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	long codIettPrincipal = Pagina.getParamLong(request, "codIettPrincipal");
	List lEstruturas;
	
	if (codIettPrincipal != 0) {
		itemPrincipal = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettPrincipal));
		lEstruturas = estruturaDao.getSetEstruturasItem(itemPrincipal);
	} else {
		itemPrincipal = null;
		lEstruturas = estruturaDao.getEstruturaPrincipal();
	}

	ExportacaoRelatorioItemEstruturaDao exporDao = new ExportacaoRelatorioItemEstruturaDao(request);
		
	List estruturaSelecionada = new ArrayList();
		
	String codEttSel = Pagina.getParamStr(request, "codEttSelecionado");
	EstruturaEtt estruturaEtt = null;
	if (codEttSel != null && !"".equals(codEttSel)){
		estruturaEtt = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, new Long(codEttSel));
		estruturaSelecionada.add(estruturaEtt);
	}
	else{
		estruturaEtt = (EstruturaEtt)lEstruturas.get(0);
		estruturaSelecionada.add(estruturaEtt);
	}	
	
	
		
//	List listArquivosExportados = exporDao.gerarArquivosExportacaoTxt(
//		(List) session.getAttribute("listEstruturas"), new ecar.dao.ConfiguracaoDao(request).getConfiguracao());
		
	boolean ehVirtual = false;
	String exportar = Pagina.getParamStr(request, "hidAcao");
	if (exportar != null && exportar.equalsIgnoreCase("exportar")){
	if (((EstruturaEtt)estruturaSelecionada.get(0)).getVirtual().booleanValue()){
		
		//listArquivosExportados = exporDao.gerarArquivosExportacaoVirtualTxt((EstruturaEtt)estruturaSelecionada.get(0), configuracao);
		itemPrincipal = new ItemEstruturaIett();
		ehVirtual = true;
	}
	else{	
		//listArquivosExportados = exporDao.gerarArquivosExportacaoTxt(estruturaSelecionada, new ecar.dao.ConfiguracaoDao(request).getConfiguracao(), itemPrincipal);
		Montador montador = new Montador (null, Montador.TIPO_MOVIMENTO_EXPORTACAO);
		ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();

		telaBean = ctrl.gerarArquivosCadastro(estruturaSelecionada,itemPrincipal,seguranca);
		session.setAttribute("telaBean", telaBean);
		
	}
	}else{
		telaBean = (TelaExportacaoBean) session.getAttribute("telaBean");
	}

%>
			
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
	
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
						<%
						ItemEstruturaCadastroHtml itemEstruturaCadastroHtml = new ItemEstruturaCadastroHtml (itemPrincipal, estruturaEtt, request);
						%>
						<!-- Árvore de Localização -->	
						<div id="arvoreLocalizacao">
							<%
								out.println(itemEstruturaCadastroHtml.geraConteudoArvoreLocalizacao("estrutura").replace("\\", ""));
							%>	
						</div>
						<div id="abasEstrutura">
							<%
								out.println(itemEstruturaCadastroHtml.geraConteudoAbasEstrutura("estrutura", false).replace("\\", ""));
							%>
						</div>									
						<!-- Nome da estrutura selecionada -->
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr id="trNomeEstrutura" class="linha_titulo_estrutura" bgcolor="<%=estruturaEtt.getCodCor1Ett()%>" cellpadding="0" cellspacing="0">
								<td id="nomeEstrutura" colspan="2">
									<%
									out.println(estruturaEtt.getNomeEtt());
									%>
								</td>
							</tr>	

					<display:table id="displayTag" name="<%=telaBean.getCaminhosArquivoComHash(request)%>"  export="false" excludedParams="hidAcao">
					<display:column property="nomeEstrutura" title="Estrutura" sortable="true" group="1"/>
					<display:column property="nomeFuncao" title="Função"  sortable="true" group="2"/>
					<display:column property="nomeArquivo" title="Arquivo"  sortable="true" href="../../DownloadFile" paramId="RemoteFile" paramProperty="caminhoFisicoSemEncode" />
<!-- 				<display:setProperty name="caminhoArquivoExportacaoBean.usarEncode" value="N"></display:setProperty>  -->
					
				</display:table>

														
								<input type="hidden" name="codEttSelecionado" id="codEttSelecionado" value="<%=codEttSel%>">
						
								<input type="hidden" name="codEttImprimir" value="">
								<input type="hidden" name="codIettPaiImprimir" value="">
								
								
								<%@ include file="../../include/estadoMenu.jsp"%>
								<input type=hidden name="hidAcao" value="">
								<!-- usado na inclusao de um item de nivel 1 -->
								<input type=hidden name="codEtt" value="">
								<!-- usado na consulta de um item -->
								<input type=hidden name="codIett" value="">		
								<input type=hidden name="nomeCheckBox" value="">			
								<!-- usado na inclusao de um item de qualquer nivel -->
								<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParamLong(request, "codIettPrincipal")%>">		 	
							
							</table>
						</td>			
					</tr>
				</table>			
			</td>
		</tr>
	
	</table>
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