
<jsp:directive.page import="comum.util.Util"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.PontoCriticoDao" %> 

<%@ page import="java.util.Date" %>
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ecar.pojo.ApontamentoApt" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.Calendar" %>

<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script>
function recarregar(){
	document.forms[0].action = "pontosCriticos.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<%
try{
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	Calendar dataAtual = Calendar.getInstance();
	
	dataAtual.clear(Calendar.HOUR);
    dataAtual.clear(Calendar.HOUR_OF_DAY);
    dataAtual.clear(Calendar.MINUTE);
    dataAtual.clear(Calendar.SECOND);
    dataAtual.clear(Calendar.MILLISECOND);
    dataAtual.clear(Calendar.AM_PM);
%>

<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
	<br>

	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="PONTOS_CRITICOS"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
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
	
	<form  name="form" method="post" action="pontosCriticos.jsp">
	
		<input type="hidden" name="codAri" value="<%=acompReferenciaItem.getCodAri()%>">
		<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
		<input type=hidden name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
				<b>Pontos Críticos</b>
				</td>
			</tr>
		</table>

		<table name="form" class="form" width="100%">
		<tr>
			<td>Selecione uma das opções abaixo:</td>
		</tr>
		<tr> 
			<td colspan=2>
				<%
				String paramPesquisa = Pagina.getParamStr(request, "tipoSelecao");
				if("".equals(paramPesquisa))
					paramPesquisa = "T";
				%>
				<input type="radio" class="form_check_radio" name="tipoSelecao" value="T" onclick="recarregar()" <%if("T".equals(paramPesquisa)){ out.println("checked");}%>> Todos
				<input type="radio" class="form_check_radio" name="tipoSelecao" value="N" onclick="recarregar()" <%if("N".equals(paramPesquisa)){ out.println("checked");}%>> Não Solucionados
				<input type="radio" class="form_check_radio" name="tipoSelecao" value="S" onclick="recarregar()" <%if("S".equals(paramPesquisa)){ out.println("checked");}%>> Solucionados								
			</td>
		</tr>
		<%
		if(acompReferenciaItem.getItemEstruturaIett().getPontoCriticoPtcs() != null && acompReferenciaItem.getItemEstruturaIett().getPontoCriticoPtcs().size() > 0){
			Collection pontosCriticos = Collections.EMPTY_LIST;
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			if("".equals(Pagina.getParamStr(request, "tipoSelecao")) || "T".equals(Pagina.getParamStr(request, "tipoSelecao")))
				pontosCriticos = pontoCriticoDao.getAtivos(acompReferenciaItem.getItemEstruturaIett()); 
			else{
				if("S".equals(Pagina.getParamStr(request, "tipoSelecao")))
					pontosCriticos = pontoCriticoDao.getPontosCriticosSolucionados(acompReferenciaItem.getItemEstruturaIett());
				if("N".equals(Pagina.getParamStr(request, "tipoSelecao")))
					pontosCriticos = pontoCriticoDao.getPontosCriticosNaoSolucionados(acompReferenciaItem.getItemEstruturaIett());
			}
			String corRelogio = "Branco";
			String descRelogio = "";		
			Iterator it = pontosCriticos.iterator();											
			while(it.hasNext()){
				PontoCriticoPtc pontoCritico = (PontoCriticoPtc) it.next();			
				%>
				<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
				<tr>
					<td class="label">Tipo/Assunto:</td>
					<td>
						<%
							if(pontoCritico.getSisAtributoTipo() != null) {
								out.print(Pagina.trocaNull(pontoCritico.getSisAtributoTipo().getDescricaoSatb()));
							}
						%>
					</td>
				</tr>
				<tr>
					<td class="label"><b>Descri&ccedil;&atilde;o:</b></td>
					<td><%=Util.normalizaQuebraDeLinhaHTML(pontoCritico.getDescricaoPtc())%></td>				
				</tr>			
				<tr>
					<td class="label">&Acirc;mbito:</td>
					<td>
						<%if("I".equals(pontoCritico.getIndAmbitoInternoGovPtc())){%>
							Interno
						<%}%>
						<%if("E".equals(pontoCritico.getIndAmbitoInternoGovPtc())){%>
							Externo
						<%}%>

					</td>					
				</tr>			
								<tr>
					<td class="label">Estado:</td>
					<%						
						String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());
						corRelogio = relogioAtual[0];
						descRelogio = relogioAtual[1];
					%>
					<td>
						<img src="../../images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>">
					</td>										
				</tr>
				<tr>
					<td class="label" valign="top">Data de Registro:</td>
					<td><%=Pagina.trocaNullData(pontoCritico.getDataIdentificacaoPtc())%></td>					
				</tr>			
				<tr>
					<td class="label" valign="top">Data Limite:</td><td>
					<% 
					Calendar dataLimite = Calendar.getInstance();
    	    		dataLimite.setTime(pontoCritico.getDataLimitePtc());
    	    	
    	    		dataLimite.clear(Calendar.HOUR);
    	    		dataLimite.clear(Calendar.HOUR_OF_DAY);
    	    		dataLimite.clear(Calendar.MINUTE);
    	    		dataLimite.clear(Calendar.SECOND);
    	    		dataLimite.clear(Calendar.MILLISECOND);
    	    		dataLimite.clear(Calendar.AM_PM);
    	    		
					if (dataAtual.after(dataLimite) &&	pontoCritico.getDataSolucaoPtc() == null) { 
					%>
					<span style="color:#FF3333;"><b>
					<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>				
					</b></span>
					<% } else { %>
					<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
					<% } %>
					</td>
				</tr>	
				
				<tr><td class="espacadorBranco" colspan=2><img src="../../images/pixel.gif"></td></tr>

				<tr>
					<td class="label" valign="top"> Data da Solução:</td>
					<td>
					<%
						if(pontoCritico.getDataSolucaoPtc() != null){
							out.println(Pagina.trocaNullData(pontoCritico.getDataSolucaoPtc()));
						}
					%>
					</td>					
				</tr>						
				<tr>
					<td class="label" valign="top">Sugestão de Solu&ccedil;&atilde;o:</td>
					<td>
					<%
						if(pontoCritico.getDescricaoSolucaoPtc() != null){
							out.print(Util.normalizaQuebraDeLinhaHTML(pontoCritico.getDescricaoSolucaoPtc()));
						}
					%>
					</td>					
				</tr>
				<tr>
					<td class="label" valign="top">
						Respons&aacute;vel pela Solu&ccedil;&atilde;o:
					</td>
					<td>
					<%
						if(pontoCritico.getUsuarioUsu() != null) {
							out.print(pontoCritico.getUsuarioUsu().getNomeUsu());
						}
					%>
					</td>
				</tr>
				<tr>
					<td class="label" valign="top">
						Respons&aacute;vel pela Inclus&atilde;o:
					</td>
					<td>
					<%
						if(pontoCritico.getUsuarioUsu() != null) {
							out.print(pontoCritico.getUsuarioUsuInclusao().getNomeUsu());
						}
					%>
					</td>
				</tr>
				<%
				if(pontoCritico.getApontamentoApts() != null && pontoCritico.getApontamentoApts().size() > 0){
					%>
					<tr><td class="espacadorBranco" colspan=2><img src="../../images/pixel.gif"></td></tr>						
					<tr>
					<td class="label" valign="top">Apontamentos:</td>
						<td>
							<table valign="top">
								<tr>
									<td><b>Data</b></td><td><b>Responsável</b></td><td><b>Comentários</b></td>
								<tr>
								<%
								Iterator itApontamentos = pontoCritico.getApontamentoApts().iterator();
								while(itApontamentos.hasNext()){
									ApontamentoApt apontamento = (ApontamentoApt) itApontamentos.next();
									%> 
									<tr>
										<td><%=Pagina.trocaNullData(apontamento.getDataInclusaoApt())%></td>
										<td><%=apontamento.getUsuarioUsu().getNomeUsuSent()%></td>
										<td><%=apontamento.getTextoApt()%></td>
									<tr>									
									<%
								}
								%>
							</table>
						</td>					
					</tr>							
	
					<%
				}
			}
		} else{
			%>
			<tr>
				<td coslpan="2">Nenhum Ponto Crítico existente</td>
			</tr>				
			<%
		}
		%>		
		</table>

		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label" align="left">
				&nbsp;
				</td>
			</tr>
		</table>
		
	</form>	
	
</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>