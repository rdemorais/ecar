<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
%>

<html lang="pt-br"> 
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
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
</script>
</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
	<br>
	
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="FINANCEIRO"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		<tr class="linha_titulo" align="left">
			<td>
				Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
			</td> 
		</tr>
		<tr class="linha_titulo" align="left">
			<td>
				M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
			</td> 
		</tr>
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	</table>

	<util:arvoreEstruturaElabAcomp
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
		contextPath="<%=_pathEcar%>"
		listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
		nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"
		abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/>

<form  name="form" method="post" >
	
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type=hidden name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">

<table class="" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="linha_titulo">
		<td colspan="2">&nbsp;Fonte&nbsp;</td>
		
		<td>&nbsp;Recurso&nbsp;</td>
		<td align=center>&nbsp;Aprovado&nbsp;</td>
		<td align=center>&nbsp;Revisado&nbsp;</td>
		
<%

	int _colspan = 4;

	ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
	List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(acompReferenciaItem.getItemEstruturaIett(), acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
	Iterator it = lista.iterator();
	
	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	
	String fonte = "";
	
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
			
			if(!fonte.equals(itemEstPrev.getFonteRecursoFonr())){
				if(!"".equals(fonte)){
%>
					<tr class="linha_subtitulo">
						<td colspan="2">&nbsp;Total Fonte&nbsp;</td>
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
			fonte = itemEstPrev.getFonteRecursoFonr().getNomeFonr();
			
			/* somar nos valores do total da fonte */
			totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
			totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
			
			for(int i = 0; i < 6; i++){
				totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
			}
%>
			<tr class="linha_subtitulo2">
				<td colspan="2">&nbsp;</td>
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
		
		/* somar nos valores do total geral (a ultima fonte ainda nao foi somada) */
		totGerAprovado = totGerAprovado + totFonAprovado;
		totGerRevisado = totGerRevisado + totFonRevisado;

		for(int i = 0; i < 6; i++){
			totGerValor[i] = totGerValor[i] + totFonValor[i];
		}
%>	
		<tr class="linha_subtitulo">
			<td colspan="2">&nbsp;Total Fonte&nbsp;</td>
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
		<tr class="linha_titulo">
			<td colspan="2">&nbsp;Total Geral&nbsp;</td>
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
			<td colspan="<%=_colspan%>" class="subtitulo">&nbsp;<b>Nenhum dado cadastrado</b>&nbsp;</td>
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
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>