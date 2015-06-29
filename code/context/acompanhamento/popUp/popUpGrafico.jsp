<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>

<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.Cor" %>

<%@ page import="ecar.servlet.grafico.bean.PosicaoBean" %>

<%@ page import="comum.util.Data" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Pagina" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>

<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
Date dataInicio = Data.getDataAtual();
%>

<%@page import="ecar.login.SegurancaECAR"%>
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@page import="comum.util.ConstantesECAR"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function recarregar(){
	document.getElementById("aguarde").style.display="block";
	document.form.action = "popUpGrafico.jsp";
	document.form.submit();
}

</script>

<body class="corpo_popup">
<%@ include file="../../include/exibirAguarde.jsp"%>

<script type="text/javascript">
	document.getElementById("overlay").style.display="block";
	document.getElementById("aguarde").style.display="block";
</script>

<%
try{
%>
<H1>Gráfico de Evolução das Posições</H1>

<%
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	CorDao corDao = new CorDao(request);
	AbaDao abaDao = new AbaDao(request); 
//	List listFuncAcomp = new TipoFuncAcompDao(null).getTipoFuncAcompEmitePosicao();
	ValidaPermissao validaPermissao = new ValidaPermissao();
	SegurancaECAR seguranca = (ecar.login.SegurancaECAR) session.getAttribute("seguranca");
	
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) new TipoAcompanhamentoDao(request).buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
	boolean ehSeparadoPorOrgao = false;
	if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
		ehSeparadoPorOrgao = true;
	}
	
	List listFuncAcomp = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento, seguranca.getGruposAcesso());
	boolean mostraGrafico = false;
	String selected = "2";
	selected = Pagina.getParamStr(request, "tipoFuncAcompTpfa");

	if(!"".equals(Pagina.getParamStr(request, "tipoFuncAcompTpfa"))){
		selected = Pagina.getParamStr(request, "tipoFuncAcompTpfa");
		mostraGrafico = true;
	}
	
	String niveis = Pagina.getParamStr(request, "niveisPlanejamento");
	String niveisTmp[] = niveis.split(":");
	String niveisEscolhidos = "";
	List niveisPlanejamento = new SisGrupoAtributoDao(null).getAtributosOrdenados(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	Iterator it = niveisPlanejamento.iterator();
	List listNiveis = new ArrayList();
	
	while(it.hasNext()){
		SisAtributoSatb nivel = (SisAtributoSatb) it.next();
		for(int i = 0; i < niveisTmp.length; i++){
			if("".equals(niveis) || niveisTmp[i].equals(nivel.getCodSatb().toString())){
				niveisEscolhidos += nivel.getDescricaoSatb() + " ";
				listNiveis.add(nivel);
			}
			if(!"".equals(niveisTmp[i])){
				//listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(niveisTmp[i])));
				
			}
		}
	}
	
	if(listNiveis.size() > 0){
		request.setAttribute("listNiveis", listNiveis);
	}

	String orgao = Pagina.getParamStr(request, "orgaoEscolhido");
	String periodo = Pagina.getParamStr(request, "periodoEscolhido");
	String referencia = Pagina.getParamStr(request, "referencia");
	String referenciaEscolhida = "";
	List acompanhamentos = acompReferenciaDao.getListAcompReferencia();
	Iterator itAcomp = acompanhamentos.iterator();
	while(itAcomp.hasNext()){
		AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcomp.next();
		if(referencia != null && acompReferencia.getCodAref() != null){
			if(Long.valueOf(referencia).longValue() == acompReferencia.getCodAref().longValue()){
				referenciaEscolhida = acompReferencia.getDiaAref() + "/" + acompReferencia.getMesAref() + "/" + acompReferencia.getAnoAref() + " - " + acompReferencia.getNomeAref();
				break;
			}
		}
	}
	
	
%>

<form name="form" method="post" action="">
	<input type="hidden" name="codOrgao" value="<%=Pagina.getParamStr(request, "codOrgao")%>">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=niveis%>">
	<input type="hidden" name="orgaoEscolhido" value="<%=orgao%>">
	<input type="hidden" name="periodoEscolhido" value="<%=periodo%>">
	<input type="hidden" name="referencia" value="<%=referencia%>">
	<input type="hidden" name="semInformacaoNivelPlanejamento" id="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>">
	
	<table class="form">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<%
						if (abaDao.verificaNivelPlanejamento("P")){
						%>
							<td>Níveis: <b><%=niveisEscolhidos%></b></td>
						<%
						} 
						%>
						
						<td>Órgão: <b><%=orgao%></b></td>
					</tr>
					<tr>
						<td>Quantidade de ciclos: <b><%=periodo%></b></td>
					<!-- Retirada referencia escolhida -->
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							Função de Acompanhamento:
							<combo:ComboTag 
									nome="tipoFuncAcompTpfa"
									objeto="ecar.pojo.TipoFuncAcompTpfa" 
									label="labelTpfa" 
									value="codTpfa" 
									order="labelTpfa"
									filters="indAtivoAre=S"
									selected="<%=selected%>"
									scripts="onchange='javascript:recarregar()'"
									colecao="<%=listFuncAcomp%>"
							/>					
						</td>
						<td align="right"><input type="button" name="btImprimir" value="Imprimir" onclick="javascript:window.print();"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr> 
			<td>
			<% 
			if(mostraGrafico){
				request.getSession().removeAttribute("listAvaliacoes");

				List listAvaliacoes = acompReferenciaItemDao.getDadosEvolucaoPosicao(request);
				
				request.getSession().setAttribute("listAvaliacoes", listAvaliacoes);
				%>
				<table>
					<tr>
						<td valign="top" align="center">
							<img src="<%=_pathEcar%>/GraficoEvolucaoPosicoes?codOrgao=<%=Pagina.getParamStr(request, "codOrgao")%>&tipoFuncAcompTpfa=<%=Pagina.getParamStr(request, "tipoFuncAcompTpfa")%>">
						</td>
					</tr>
					<tr> 
						<td align="center" > 
							<font size="-2"> <%= "*  " + _msg.getMensagem("relAcompanhamento.graficoProjecao.corGraficoNaoConfigurada") %> </font> 
							<br > <br > 
						 </td>
					</tr>
					<tr>
						<td>
						<!-- Tabela com informações do gráfico -->
						<table border="1" cellpadding="0" cellspacing="0">
						
						  <tr>
							<td> Per&iacute;odos </td>
						
						
						
					<%	
							Map mapCoresConfiguradas = corDao.criarMapCodCorPosicoesGeraisGrafico();
							
							List listOrdemCores = corDao.ordenarCores(mapCoresConfiguradas.keySet());

							// Adiciona os títulos das cores no Gráfico 
							for (Iterator itCores = listOrdemCores.iterator(); itCores.hasNext();) {
								String codCorConfig = (String) itCores.next();
							
								if (codCorConfig.equals(Cor.BRANCO)) {
					%>
								<td align="center"><%=Cor.LABEL_BRANCO%></td>
					<%			} else if (codCorConfig.equals(Cor.NAO_LIBERADO)) { %>
								<td align="center"><%=Cor.LABEL_NAO_LIBERADO%></td>
					<%--			} else if (codCorConfig.equals(Cor.NAO_ACOMPANHADO)) { %>
								<td align="center"><%=Cor.LABEL_NAO_ACOMPANHADO%></td>
					--%>
 					<%			} else if (Util.ehValor(codCorConfig)){
									Cor corConfig = (Cor) corDao.buscar(Cor.class, new Long(codCorConfig) );
					%>
								<td align="center"><%=corConfig.getSignificadoCor()%></td>
					<%
								}// fim if ehValor
							}// fim for
				    %>
				    	  <td align="center">Total Geral</td>
				    	  
				    	  </tr>
					<%
						Iterator itAvaliacoes = listAvaliacoes.iterator();
				        
						while(itAvaliacoes.hasNext()) {
							PosicaoBean posicaoBean = (PosicaoBean)itAvaliacoes.next();
											
	
							StringBuffer serie = new StringBuffer(posicaoBean.getARef().getDiaAref())
							.append("/").append(posicaoBean.getARef().getMesAref())
							.append("/").append(posicaoBean.getARef().getAnoAref());
	
							
							
							if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(posicaoBean.getARef()).size() > 1){
								serie.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
							} else {
								serie.append(" - ").append(posicaoBean.getARef().getNomeAref());
							}	
								
							mapCoresConfiguradas = corDao.contadorDePosicoesPorCores(posicaoBean);
					%>
							<tr>
								<td><%=serie%></td>
					<%
							
							// Adiciona Porcentagens de cada cor
							BigDecimal bigDecimal = null; //new BigDecimal(String.valueOf(0)).setScale(1, BigDecimal.ROUND_HALF_UP);
							int total = 0;
							
							//Calcula  o total de itens acompanhados
							for (Iterator itCores = listOrdemCores.iterator(); itCores.hasNext();) {
								String codCorConfig = (String) itCores.next();
								if (!codCorConfig.equals(Cor.NAO_ACOMPANHADO)){
									total += ((Integer)mapCoresConfiguradas.get(codCorConfig)).intValue();
								}
							}
														

							for (Iterator itCores = listOrdemCores.iterator(); itCores.hasNext();) {
								String codCorConfig = (String) itCores.next();
								
								if (!codCorConfig.equals(Cor.NAO_ACOMPANHADO)){
									int quantidade = ((Integer)mapCoresConfiguradas.get(codCorConfig)).intValue();
									bigDecimal =  (new BigDecimal(String.valueOf( 100 * Util.trataDivisaoPorZero(quantidade, total ) ) ) ) .setScale(1, BigDecimal.ROUND_HALF_UP);
								
								
					%>
									<td class="bgBranco" align="right"><%= quantidade %> (<%=bigDecimal%>%)</td>
					<%
								}
							}// fim for
				    
				    		bigDecimal = (new BigDecimal(String.valueOf(  100 * Util.trataDivisaoPorZero(total, total) ))).setScale(1, BigDecimal.ROUND_HALF_UP);
				    		 
				    %>
				            <td class="bgBranco" align="right"><%= total %> (<%=bigDecimal%>%)</td>
				            </tr>
				    <%
						}// fim while itAvaliacoes
					%>
						</table>						
						</td>
					</tr>
				</table>
				<%
			}// fim if mostraGrafico
			else{
			%>
			<%-- 
			Trecho comentado pois estava fazendo com que a página entrasse em loop infinito de recarga. 
			Agora a página exibe a mensagem da tabela abaixo
			--%>
			<%--script language="javascript">recarregar();</script--%>
			<table>
				<tr>
					<td width="250px" style="text-align: center; color: #f00;" >
						<b >Selecione uma função de Acompanhamento</b>					
					</td>
				</tr> 
			
			</table>
			<%
			}
			%>			
			</td>
		</tr>
	</table>
	<%
	Date dataFinal = Data.getDataAtual();
	%>
	<table class="form">
		<tr>
			<td>
				<%
				long df = dataFinal.getTime();
				long di = dataInicio.getTime();
				%>
				<b>Tempo para processar esta página:</b> <%=Data.parseDateHourSegundos( new Date(df - di) )%> mm.ss.SSS
			<td>
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

<%@ include file="../../include/ocultarAguarde.jsp"%>
</body>

</html>

