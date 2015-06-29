<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %><%@ page import="ecar.pojo.FonteRecursoFonr" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
%>

<html lang="pt-br"> 
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script>
function recarregar(form){
	form.action = "financeiro.jsp";
	form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>
</head>

<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<div id="conteudo"> 
	
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		chamarPagina="posicaoGeral.jsp"
	/> 

	<%@ include file="botoesAvancaRetrocede.jsp" %>
	
	<util:arvoreEstruturas 
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/> 
	
<br>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="FINANCEIRO"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/>

<br>

<form  name="form" method="post" >
	
<table border="0" width="100%">
	<tr>
		<td valign="bottom" class="texto" align="left">
			<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
			<b>Mês de referência: </b> <combo:ComboReferenciaTag
				nome="referencia"
				acompReferenciaItem="<%=acompReferenciaItem%>"
				scripts="onchange='recarregar(form)'"/>
		</td>
		<td align="right" valign="bottom">
			<util:barraSinaisTag 
				acompReferenciaItem="<%=acompReferenciaItem%>"
				caminho="<%=_pathEcar%>"
			/>
		</td>
	</tr>
</table>

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<!-- Campos para manter a seleção em Posição Geral -->

<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">

<%
	String recursoDesc1 = ("".equals(Pagina.trocaNull(configura.getRecursoDescValor1Cfg())) ? "Aprovado" : configura.getRecursoDescValor1Cfg());
	String recursoDesc2 = ("".equals(Pagina.trocaNull(configura.getRecursoDescValor2Cfg())) ? "Revisado" : configura.getRecursoDescValor2Cfg());
%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="linha_subtitulo">
<!--		<td>&nbsp;Fonte&nbsp;</td>-->
<!--		<td>&nbsp;Recurso&nbsp;</td>-->
<!-- 		<td>&nbsp;Categoria&nbsp;</td>
		<td>&nbsp;Fonte&nbsp;</td> -->
		<td>&nbsp;Recurso&nbsp;</td>
		<td>&nbsp;Fonte&nbsp;</td>
<!-- 		<td align=center>&nbsp;Aprovado&nbsp;</td> -->
<!-- 		<td align=center>&nbsp;Revisado&nbsp;</td> -->
		<td align=center>&nbsp;<%=recursoDesc1%>&nbsp;</td>
		<td align=center>&nbsp;<%=recursoDesc2%>&nbsp;</td>
<%

	int _colspan = 4;

	ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
	List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(acompReferenciaItem.getItemEstruturaIett(), acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
	Iterator it = lista.iterator();
	
	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	
	//String fonte = "";
	FonteRecursoFonr fonte = null;
	
	ItemEstruturaContaOrcamentoDao itemEstContaOrcDao = new ItemEstruturaContaOrcamentoDao(request);
	ItemEstruturaRealizadoDao itemEstRealizadoDao = new ItemEstruturaRealizadoDao(request);

	boolean mostrarValores[] = new boolean[6];

	String descricoes[] = new String[6];
	descricoes[0] = configura.getFinanceiroDescValor1Cfg();
	descricoes[1] = configura.getFinanceiroDescValor2Cfg();
	descricoes[2] = configura.getFinanceiroDescValor3Cfg();
	descricoes[3] = configura.getFinanceiroDescValor4Cfg();
	descricoes[4] = configura.getFinanceiroDescValor5Cfg();
	descricoes[5] = configura.getFinanceiroDescValor6Cfg();
	
	for(int i = 0; i < 6; i++){
		mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
		if(mostrarValores[i]){
			out.println("<td align=center>&nbsp;" + descricoes[i] + "&nbsp;</td>");
			_colspan++;
		}
	}
%>
	</tr>
<%
	
	if (it.hasNext()){
		double totFonAprovado = 0, totFonRevisado = 0;
		double totGerAprovado = 0, totGerRevisado = 0;
		double[] totFonValor = new double[6];
		double[] totGerValor = new double[6];
		
		/*Inicializar os valores...*/
		for(int i = 0; i < 6; i++){
			totFonValor[i] = 0;
			totGerValor[i] = 0;
		}
		
		while(it.hasNext()){
			itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
			
			/* ler EfItemEstContaEfiec */
			EfItemEstContaEfiec itemEstConta = 
					itemEstContaOrcDao.getItemEstruturaConta(
						acompReferenciaItem.getItemEstruturaIett(),
						acompReferenciaItem.getAcompReferenciaAref().getExercicioExe(),
						itemEstPrev.getFonteRecursoFonr(),
						itemEstPrev.getRecursoRec());
			
			/* verificar valores em Efier */
			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
					itemEstConta,
					acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());

//			if(!fonte.equals(itemEstPrev.getFonteRecursoFonr())){
//				if(!"".equals(fonte)){
			if(fonte != null && !fonte.equals(itemEstPrev.getFonteRecursoFonr())){
				if(!"".equals(fonte.getNomeFonr())){
%>
					<tr class="linha_subtitulo">
						<td>&nbsp;Total Categoria&nbsp;</td>
						<td>&nbsp;</td>
						<td align=right>&nbsp;<%=Util.formataMoeda(totFonAprovado)%></td>
						<td align=right>&nbsp;<%=Util.formataMoeda(totFonRevisado)%></td>
						<%
						for(int i = 0; i < 6; i++){
							if(mostrarValores[i])
								out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonValor[i]) + "</td>");
						}
						%>
					</tr>
<%
					/* somar nos valores do total geral */
					totGerAprovado = totGerAprovado + totFonAprovado;
					totGerRevisado = totGerRevisado + totFonRevisado;
					
					for(int i = 0; i < 6; i++){
						totGerValor[i] = totGerValor[i] + totFonValor[i];
					}
					
					/* zerar os valores do total da fonte */
					totFonAprovado = 0;
					totFonRevisado = 0;
					
					for(int i = 0; i < 6; i++){
						totFonValor[i] = 0;
					}
				}
%>
				<tr class="linha_subtitulo2">
					<td colspan="<%=_colspan%>">&nbsp;<%=itemEstPrev.getFonteRecursoFonr().getNomeFonr()%>&nbsp;</td>
				</tr>
<%
			}
			else if (fonte == null){
%>
			<tr class="linha_subtitulo2">
				<td colspan="<%=_colspan%>">&nbsp;<%=itemEstPrev.getFonteRecursoFonr().getNomeFonr()%>&nbsp;</td>
			</tr>
<%
			}
			//fonte = itemEstPrev.getFonteRecursoFonr().getNomeFonr();
			fonte = itemEstPrev.getFonteRecursoFonr();
			
			/* somar nos valores do total da fonte */
			totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
			totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
			
			boolean possuiAlgumValorParaMostrar = false;

			for(int i = 0; i < 6; i++){
				totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
				
				if(valores[i].doubleValue() != 0){
					possuiAlgumValorParaMostrar = true;
				}
			}
			
			if(itemEstPrev.getValorAprovadoEfiep().doubleValue() != 0 || itemEstPrev.getValorRevisadoEfiep().doubleValue() != 0){
				possuiAlgumValorParaMostrar = true;
			}
			
			if(possuiAlgumValorParaMostrar){
%>
			<tr class="linha_subtitulo2">
				<td>&nbsp;</td>
				<td>&nbsp;<%=itemEstPrev.getRecursoRec().getNomeRec()%></td>
				<td align=right>&nbsp;<%=Util.formataMoeda(itemEstPrev.getValorAprovadoEfiep().doubleValue())%></td>
				<td align=right>&nbsp;<%=Util.formataMoeda(itemEstPrev.getValorRevisadoEfiep().doubleValue())%></td>
				<%
				for(int i = 0; i < 6; i++){
					if(mostrarValores[i])
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(valores[i].doubleValue()) + "</td>");
				}
				%>
			</tr>
<%
			}
		}
		
		/* somar nos valores do total geral (a ultima fonte ainda nao foi somada) */
		totGerAprovado = totGerAprovado + totFonAprovado;
		totGerRevisado = totGerRevisado + totFonRevisado;
		
		for(int i = 0; i < 6; i++){
			totGerValor[i] = totGerValor[i] + totFonValor[i];
		}
%>	
		<tr class="linha_subtitulo">
			<td>&nbsp;Total Fonte&nbsp;</td>
			<td>&nbsp;</td>
			<td align=right>&nbsp;<%=Util.formataMoeda(totFonAprovado)%></td>
			<td align=right>&nbsp;<%=Util.formataMoeda(totFonRevisado)%></td>
			<%
			for(int i = 0; i < 6; i++){
				if(mostrarValores[i])
					out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonValor[i]) + "</td>");
			}
			%>
		</tr>
		<tr class="linha_subtitulo">
			<td>&nbsp;Total Geral&nbsp;</td>
			<td>&nbsp;</td>
			<td align=right>&nbsp;<%=Util.formataMoeda(totGerAprovado)%></td>
			<td align=right>&nbsp;<%=Util.formataMoeda(totGerRevisado)%></td>
			<%
			for(int i = 0; i < 6; i++){
				if(mostrarValores[i])
					out.println("<td align=right>&nbsp;" + Util.formataMoeda(totGerValor[i]) + "</td>");
			}
			%>
		</tr>
<%
	} else {
%>
		<tr>
			<td colspan=<%=_colspan%> class="subtitulo">&nbsp;<b>Nenhum dado cadastrado</b>&nbsp;</td>
		</tr>
<%
	}
%>
</table>

</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>