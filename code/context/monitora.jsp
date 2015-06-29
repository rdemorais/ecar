<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collection" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.servlet.grafico.bean.PosicaoBean" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.Date" %>
<%@ include file="frm_global_sem_sentinela.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
Date dataInicio = Data.getDataAtual();
%>

<html lang="pt-br">
<head>
<%@ include file="include/meta.jsp"%>
<%@ include file="titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="js/focoInicial.js"></script>
<script language="javascript" src="js/frmPesquisa.js"></script>
<script language="javascript" src="js/validacoes.js"></script>
<script>
function recarregar(){
	document.form.action = "monitora.jsp";
	document.form.submit();
}

</script>

<body class="corpo_popup">

<%
try{
%>
<H1>Gráfico de Evolução das Posições</H1>

<%
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);

	List listFuncAcomp = new TipoFuncAcompDao(null).getTipoFuncAcompEmitePosicao();
	boolean mostraGrafico = false;
	String selected = "2";
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

	String codTipoAcompanhamento = null;
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
	
	if(!listTa.isEmpty()) {
		codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
	} 

	//String orgao = Pagina.getParamStr(request, "orgaoEscolhido");
	String orgao = "Todos";
	//String periodo = Pagina.getParamStr(request, "periodoEscolhido");
	String periodo = "3 Ciclos";
	String referencia = Pagina.getParamStr(request, "referencia");
	String referenciaEscolhida = "";
	List acompanhamentos = acompReferenciaDao.getListAcompReferencia();
	
	// obter a referencia mais atual
	if(acompanhamentos != null && acompanhamentos.size() > 0) {
		AcompReferenciaAref acompReferencia = (AcompReferenciaAref)acompanhamentos.get(0);
		referencia = acompReferencia.getCodAref().toString();
		referenciaEscolhida = acompReferencia.getMesAref() + "/" + acompReferencia.getAnoAref() + " - " + acompReferencia.getNomeAref();
		
		/* Busca coleção com ciclos a serem considereados */
		Collection periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(Long.valueOf(referencia), 3,  Long.valueOf(codTipoAcompanhamento));
		
		/* seta na sessão coleção de periodos, para ser utilizado no grafico */
		request.getSession().setAttribute("periodosConsiderados", periodosConsiderados);
	}
	/*
	Iterator itAcomp = acompanhamentos.iterator();
	while(itAcomp.hasNext()){
		AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcomp.next();
		if(referencia != null && acompReferencia.getCodAref() != null){
			if(Long.valueOf(referencia).longValue() == acompReferencia.getCodAref().longValue()){
				referenciaEscolhida = acompReferencia.getMesAref() + "/" + acompReferencia.getAnoAref() + " - " + acompReferencia.getNomeAref();
				break;
			}
		}
	}
	*/
%>

<form name="form" method="post" action="">
<!--	Niveis: <%=niveisEscolhidos%><br>-->
<!--	Orgao: <%=orgao%><br>-->
<!--	Quantidade de Ciclos: <%=periodo%><br>-->
<!--	Ciclo de referencia: <%=referenciaEscolhida%><br>-->
	<input type="hidden" name="codOrgao" value="<%=Pagina.getParamStr(request, "codOrgao")%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=niveis%>">
	<input type="hidden" name="orgaoEscolhido" value="<%=orgao%>">
	<input type="hidden" name="periodoEscolhido" value="<%=periodo%>">
	<input type="hidden" name="referencia" value="<%=referencia%>">
	
	<table class="form">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>Níveis: <b><%=niveisEscolhidos%></b></td>
						<td>Órgão: <b><%=orgao%></b></td>
					</tr>
					<tr>
						<td>Quantidade de ciclos: <b><%=periodo%></b></td>
						<td>Ciclo de referência: <b><%=referenciaEscolhida%></b></td>
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
							<img src='<%=_pathEcar%>/GraficoEvolucaoPosicoes?codOrgao=<%=Pagina.getParamStr(request, "codOrgao")%>&tipoFuncAcompTpfa=<%=Pagina.getParamStr(request, "tipoFuncAcompTpfa")%>'>
						</td>
					</tr>
					<tr>
						<td>
						<table border="1" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">Ciclos</td>
								<td align="center">Verde</td>
								<td align="center">Amarelo</td>
								<td align="center">Vermelho</td>
				            	<td align="center">Branco</td>
				            	<td align="center">Não Liberado</td>
				            	<td align="center">Total Geral</td>
							</tr>
						<%
						Iterator itAvaliacoes = listAvaliacoes.iterator();
				        
						while(itAvaliacoes.hasNext()) {
							PosicaoBean posicaoBean = (PosicaoBean)itAvaliacoes.next();

							int totalGreen = 0;
							int totalYellow = 0;
							int totalRed = 0;
							int totalWhite = 0;
							int totalNaoLiberado = 0;
							int totalNaoAcompanhado = 0;
							int totalGeral = 0;
							int totalParcial = 0;

							double percGreen = 0;
							double percYellow = 0;
							double percRed = 0;
							double percWhite = 0;
							double percNaoLiberado = 0;
							//double percNaoAcompanhado = 0;
							//double percTotalParcial = 0;
														
							String serie = posicaoBean.getARef().getNomeAref();
							
							Iterator itCoresPosicoes = posicaoBean.getCor().iterator();
							
							%>
							<tr>
								<td><%=serie%></td>
							<%
							
							while(itCoresPosicoes.hasNext()) {
								String cor = itCoresPosicoes.next().toString();
							
								if("N/L".equals(cor)) {
									totalNaoLiberado++;
								}
								//else if(cor.equals("N/A")) {
									//totalNaoAcompanhado++;
								//}
								else if("BRANCO".equals(cor)) {
									totalWhite++;
								}
								else if("1".equals(cor)) {
									totalGreen++;
								}
								else if("2".equals(cor)) {
									totalYellow++;
								}
								else if("3".equals(cor)) {
									totalRed++;
								}
							}
				            
				            //totalGeral = totalGreen + totalYellow + totalRed + totalWhite + totalNaoLiberado + totalNaoAcompanhado;
				            totalGeral = totalGreen + totalYellow + totalRed + totalWhite + totalNaoLiberado;
				            //totalParcial = totalGreen + totalYellow + totalRed + totalWhite + totalNaoLiberado;
				            double cem = 100.0;
				            
				             //Se acontecer de não haver total, os valores ficam com zero
				             //Isso foi feito para evitar divisões por zero.
				            if(totalGeral != 0){
					            percGreen = (totalGreen * cem) / totalGeral;
					            percYellow = (totalYellow * cem) / totalGeral;
					            percRed = (totalRed * cem) / totalGeral;
					            percWhite = (totalWhite * cem) / totalGeral;
					            percNaoLiberado = (totalNaoLiberado * cem) / totalGeral;
					            //percNaoAcompanhado = (totalNaoAcompanhado * cem) / totalGeral;
					            //percTotalParcial = percGreen + percYellow + percRed + percWhite + percNaoLiberado;
					        }
					        				          	
				            BigDecimal bigDecimalGreen = new BigDecimal(String.valueOf(percGreen)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            BigDecimal bigDecimalYellow = new BigDecimal(String.valueOf(percYellow)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            BigDecimal bigDecimalRed = new BigDecimal(String.valueOf(percRed)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            BigDecimal bigDecimalWhite = new BigDecimal(String.valueOf(percWhite)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            BigDecimal bigDecimalNaoLiberado = new BigDecimal(String.valueOf(percNaoLiberado)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            //BigDecimal bigDecimalNaoAcompanhado = new BigDecimal(String.valueOf(percNaoAcompanhado)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            
				            //BigDecimal bigDecimalTotalParcial = new BigDecimal(String.valueOf(percTotalParcial)).setScale(1, BigDecimal.ROUND_HALF_UP);
				            %>
				            	<td class="bgBranco" align="right"><%=totalGreen%> (<%=bigDecimalGreen.toString()%>%)</td>
				            	<td class="bgBranco" align="right"><%=totalYellow%> (<%=bigDecimalYellow.toString()%>%)</td>
				            	<td class="bgBranco" align="right"><%=totalRed%> (<%=bigDecimalRed.toString()%>%)</td>
				            	<td class="bgBranco" align="right"><%=totalWhite%> (<%=bigDecimalWhite.toString()%>%)</td>
				            	<td class="bgBranco" align="right"><%=totalNaoLiberado%> (<%=bigDecimalNaoLiberado.toString()%>%)</td>
				            	<td align="right"><%=totalGeral%> (100%)</td>
				            </tr>
				            <%
						}
						%>
						</table>						
						</td>
					</tr>
				</table>
				<%
			}
			else{
			%>
			<script language="javascript">recarregar();</script>
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
} catch (Exception e){
%>
	<%@ include file="excecao.jsp"%>
<%
}
%>
</body>

</html>

