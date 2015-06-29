<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.pojo.AcompRefLimitesArl" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>  

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.bean.OrdenacaoDataTpfa" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>


<%@page import="comum.util.ConstantesECAR"%><html lang="pt-br"> 
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPadraoRegAcompanhamento.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">
function aoClicarConsultar(form, mesReferencia){
	form.mesReferencia.value = mesReferencia;
	document.form.action = "relAcompOpcoes.jsp";
	document.form.submit();
}

function aoClicarWalla(form){
	document.form.action = "<%=_pathEcar%>/relatorioBijari";
	document.form.submit();
}
</script>
 
</head>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 
<%
String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);

// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
if("".equals(codTipoAcompanhamento)) {
	
	List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
	
	if(!listTa.isEmpty()) {
		codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
	} 
}
%>
<body> 
<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

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
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
		chamarPagina="listaRelAcomp.jsp"
		tela="lista"
		caminho="<%=_pathEcar%>"
		gerarRelatorio="<%=Boolean.TRUE%>"
		exibirAbaFiltro="<%=Boolean.FALSE%>"
		
	/> 
	
	<div id="subconteudo">
	
	
	<form name="form" method="post">
	
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="mesReferencia" value="">
	
	
	<%
	try {
		ValidaPermissao validaPermissao = new ValidaPermissao();
		AcompReferenciaDao acompDao = new AcompReferenciaDao(null);
		TipoAcompanhamentoTa tipoAcomp = null;
		List lAcomp = null;
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request); 
		//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		boolean ehSeparadoPorOrgao = false; 
		
		if(codTipoAcompanhamento != null && !codTipoAcompanhamento.equals(""))
			tipoAcomp = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
		if(tipoAcomp != null && tipoAcomp.getIndSepararOrgaoTa() != null && tipoAcomp.getIndSepararOrgaoTa().equals("S")) {
			ehSeparadoPorOrgao = true;
			lAcomp = acompDao.getComboReferencia(tipoAcomp);
		} else {
			lAcomp = acompDao.getListAcompReferenciaByTipoAcompanhamento(tipoAcomp.getCodTa());
		}
		
		
		
		// agrupar os acompanhamentos pela sequencia de apresentação do tipo de acompanhamento
		// e, se existir, pelo órgão também.
		/*if(lAcomp != null) {
			Collections.sort(lAcomp,
				new Comparator() {
					public int compare(Object o1, Object o2) {
						AcompReferenciaAref aRef1 = (AcompReferenciaAref) o1;
						AcompReferenciaAref aRef2 = (AcompReferenciaAref) o2;
						
						String ordem1 = Util.completarZerosEsquerda(aRef1.getTipoAcompanhamentoTa().getSeqApresentacaoTa(), 3)
							 + Util.completarZerosEsquerda((aRef1.getOrgaoOrg() != null) ? aRef1.getOrgaoOrg().getCodOrg() : null, 8);
						
								String ordem2 = Util.completarZerosEsquerda(aRef2.getTipoAcompanhamentoTa().getSeqApresentacaoTa(), 3)
							 + Util.completarZerosEsquerda((aRef2.getOrgaoOrg() != null) ? aRef2.getOrgaoOrg().getCodOrg() : null, 8);
	
						return ordem1.compareToIgnoreCase(ordem2);
					}
				});
		}	*/
		
		
		
		
	
		
		String codPrimeiroAcomp = "";
		
		if(lAcomp != null && lAcomp.size() > 0){
			codPrimeiroAcomp = ((AcompReferenciaAref) lAcomp.get(0)).getCodAref().toString();
		}
		%>
		 
<!--		<table>-->
<!--			<tr>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=Executivo">Relatorio Executivo</a>-->
<!--				</td>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=Gerencial">Relatorio Gerencial</a>-->
<!--				</td>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=OperacionalProdutos">Relatorio Operacional Produtos</a>-->
<!--				</td>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=OperacionalProdutosAcoes">Relatorio Operacional Produtos Ações</a>-->
<!--				</td>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=OperacionalProdutosAcoesAtividades">Relatorio Operacional Produtos Ações Atividades</a>-->
<!--				</td>-->
<!--				<td>-->
<!--					<a href="${pageContext.request.contextPath}/relatorioBijari?tipoRelatorio=Indicadores">Relatorio Indicadores</a>-->
<!--				</td>-->
<!--			</tr>-->
<!--		</table>-->
<!--		 -->
		
<!--		<button value="Relatorio Bijari" onclick="javascript:aoClicarWalla(document.form)" > </button>-->
	<input type="hidden" name="primeiroAcomp" value="<%=codPrimeiroAcomp%>">

	<table width="100%" border="0" cellpadding="0" cellspacing="0">	
		<%
		
		List listOrdenacaoDataTpfa = tipoFuncAcompDao.getTpfaOfArlAndTipoAcompanhamentoOrderByDatas(Long.valueOf(codTipoAcompanhamento), lAcomp);

		Iterator itOrdenacaoDataTpfa;
	
		Iterator itAcomp = lAcomp.iterator();
	
		String orgaoAtual = "";
		
		boolean exibirCabecalho = true;
		String nomeOrgao = "";
		
		int cont = 0;
		String cor = new String(); 

		
		while (itAcomp.hasNext()) {
			if (cont % 2 == 0){
				cor = "cor_nao";
			} else {
				cor = "cor_sim"; 
			}
			
			//para identificar quando a referencia for consolidada = mais de uma referencia para o mesmo dia, mes e ano. 
			boolean ehReferenciaConsolidada = false;
			AcompReferenciaAref acompAref = (AcompReferenciaAref) itAcomp.next(); 
			
			String nomeReferencia = "";
		
			if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompAref).size() > 1){
				ehReferenciaConsolidada = true;
				nomeReferencia = acompAref.getDiaAref()+ "/" + acompAref.getMesAref() +"/"  + acompAref.getAnoAref() + " - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO;
			} else {
				nomeReferencia = acompAref.getNomeAref();
			}
			
			if(exibirCabecalho) {
			%>
				<tr class="linha_subtitulo2">
					<td colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
						&nbsp;
					</td>
				</tr>
					
				<tr>
					<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
						<img src="../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo" align="left">
					<td colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
						<%=acompAref.getTipoAcompanhamentoTa().getDescricaoTa()%>
					</td>
				</tr>
				<tr>
					<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
						<img src="../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_subtitulo">
					<td>Referência</td>
					<td>Dia/M&ecirc;s/Ano</td>
					<%
					itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator();
					while (itOrdenacaoDataTpfa.hasNext()) {
						OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa.next(); 
					%>
						<td align="center"><%=ordenacao.getLabel()%></td>
					<%				
					}
					%>
				</tr>
				<tr>
					<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
						<img src="../images/pixel.gif">
					</td>
				</tr>	
			<%
				exibirCabecalho = false;
			}
			%>
			<%
			String orgaoItem = "";
			if(acompAref.getOrgaoOrg() != null && !ehReferenciaConsolidada){
				orgaoItem = Pagina.trocaNull(acompAref.getOrgaoOrg().getDescricaoOrg());
			}
			
		/*	if(!orgaoAtual.equals(orgaoItem)){
				if( !"".equals(orgaoItem)){
					nomeOrgao = orgaoItem;
				} else {
					nomeOrgao = configuracao.getLabelAgrupamentoItensSemOrgao();
				}
				orgaoAtual = orgaoItem; */
			%>
				<!-- tr class="linha_titulo">
					<td colspan="</%=listOrdenacaoDataTpfa.size() + 4%>">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</%=nomeOrgao%>
					</td>
				</tr-->	
			<%
		//	} 
			
			
			
			%>				
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">
				<td>
					<a href="javascript:aoClicarConsultar(document.form,<%=acompAref.getCodAref()%>)"><%=nomeReferencia%></a>
				</td>
				<td><%=acompAref.getDiaAref()%>/<%=acompAref.getMesAref()%>/<%=acompAref.getAnoAref()%></td>
				<%
				itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator();
				
				List listAcompLimite = acompDao.getAcompRefLimitesOrderByFuncaoAcomp(acompAref);
				
				while (itOrdenacaoDataTpfa.hasNext()) {
					OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa.next();
					
					String strValueData = "";
					Iterator itAcompLimite = listAcompLimite.iterator();
					
					while(itAcompLimite.hasNext() && !ehReferenciaConsolidada){
						AcompRefLimitesArl acompLimite = (AcompRefLimitesArl) itAcompLimite.next();
						
						if(ordenacao.getTpfa() != null && acompLimite.getTipoFuncAcompTpfa().equals(ordenacao.getTpfa())) {
							strValueData = Data.parseDate(acompLimite.getDataLimiteArl());
							break;
						}
						else if(ordenacao.getTpfaFixo() != null) {
							if(ordenacao.getTpfaFixo().equals(OrdenacaoDataTpfa.FUNCAO_INICIO)) {
								strValueData = Data.parseDate(acompAref.getDataInicioAref());
								break;
							}
							else if(ordenacao.getTpfaFixo().equals(OrdenacaoDataTpfa.FUNCAO_LIMITE)) {
								strValueData = Data.parseDate(acompAref.getDataLimiteAcompFisicoAref());
								break;
							}
						}
					}
					if("".equals(strValueData)) {
						strValueData = "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;";
					}
				%>
					<td align="center"><%=strValueData%></td>
				<%
				}
				%>
			</tr>
	<%	
			cont++;
		} /* while (itAcomp.hasNext()) */
	%>
	
	</table>	
	</form>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>&nbsp;</td></tr>
	</table>
	<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
<%
	}
}
%>
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>

