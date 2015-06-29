
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.AcompRealFisicoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.Data" %>

<%@ page import="ecar.api.facade.*" %>


<%@page import="java.util.SortedMap"%>
<%@page import="java.util.TreeMap"%>
<%@page import="ecar.api.facade.AcompanhamentoItemEstrutura"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript">
function abrirPopUpGraficoRealizadoNoExercicio(codAri, codExe, codIettir){
	//Merge feito por Patricia da parte de Thaise
	abreJanela("popUpGraficoRealizadoNoExercicio.jsp?codAri=" + codAri + "&codExe=" + codExe + "&codIettir=" + codIettir, 800, 500);	
}

function abrirPopUpConsultaRealizadoPorLocal(form, codARF, nomeIndicador) {
	var janela = abreJanela('popUpConsultaRealizadoFisicoPorLocal.jsp?codARF=' + codARF + '&nomeIndicador=' + escape(nomeIndicador), 450, 400, 'popUpConsultaRealizadoFisicoPorLocal');
	janela.focus();
}

</script>

</head>
<body class="corpo_popup">

<div>

<%
try{
	Long codAri = Long.valueOf(Pagina.getParamStr(request, "codAri"));
	Long codExe = Long.valueOf(Pagina.getParamStr(request, "codExe")); 
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(AcompReferenciaItemAri.class, codAri);
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	//if ( !validaPermissao.permissaoConsultaParecerIETT( ari.getItemEstruturaIett().getCodIett() , seguranca ) )
	//{
	//	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	//}
	
	ExercicioExe exercicio = (ExercicioExe) new ExercicioDao(request).buscar(ExercicioExe.class, codExe);
	ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
%>

<H1><%=ari.getItemEstruturaIett().getEstruturaEtt().getNomeEtt()%>: <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(ari.getItemEstruturaIett(), ari.getItemEstruturaIett().getEstruturaEtt())%></H1>
<H1>Realizado no Ciclo: <%=exercicio.getDescricaoExe()%></H1>

<form name="form">

	<%
	
	List<String> meses = new ArrayList<String>();
	if(exercicio.equals(ari.getAcompReferenciaAref().getExercicioExe())){
		int mesRef = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue();
		int anoRef = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue();	
		meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio,mesRef,anoRef);
	}else{
		meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio);
	}
	
	AcompanhamentoItemEstrutura acompanhamento = new AcompanhamentoItemEstrutura(ari);
	List<EcarData> mesesItem = acompanhamento.getItemEstrutura().getMeses();
	//converte para o formato já utilizado nesse jsp para nao ter que reescreve-lo inteiro
	ItemEstrutura item = new ItemEstrutura(acompanhamento.getItemEstrutura().getRealObject());

	Exercicio exe = new Exercicio(exercicio);
	List<EcarData> mesesExercicio = exe.getMeses();
	
	EcarData dataInicioItem = new EcarData(item.getDataInicial());
	EcarData dataFimItem = new EcarData(item.getDataFinal());
	
	meses.clear();
	//pega os meses do item somente
	for(EcarData data: mesesExercicio){
		if(EcarData.pertenceAoIntervalo(data, dataInicioItem, dataFimItem)){
			meses.add(data.getMes() + "-" + data.getAno());
		}
	}
	
	%>

	<%
	
	    Iterator it = meses.iterator();
		SortedMap<String, Integer> map = new TreeMap<String, Integer>();	
		
		//List<String> months  = new ArrayList<String>();
		while(it.hasNext()){
			String mesAno = it.next().toString();
			String mes  = mesAno.split("-")[0];
			String ano  = mesAno.split("-")[1];
			if(map.get(ano) != null){
				int x = map.get(ano).intValue();
				map.put(ano, x+1);
			}else{
				map.put(ano, new Integer(1));
			}
		}
		
	%>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="<%=meses.size() + 5%>"><img src="../../images/pixel.gif"></td></tr>
		
		<tr class="linha_subtitulo">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<%
				//guarda o número de colunas renderizadas
				//depois comparamos com o numero de meses + 1 (coluna total)
				//para verificar se há a necessidade de mais colunas para não
				//deixar espaços vazios na tabela
				int numOfColunsRendered = 0; 
				for(String key: map.keySet()){
					//coloca o ano do exercício
					out.println("<td>" + key + "</td>");
					numOfColunsRendered++;
					int span = map.get(key).intValue() - 1;
					//determina como largura da coluna o numero
					//de meses do exercício - 1
					if(span > 0){
						out.println("<td colspan=\" " + span + "\"></td>" );
					}
					numOfColunsRendered += span;
				}
				
				if(numOfColunsRendered < (meses.size() + 1)){
					int colspan = (meses.size() + 1) - numOfColunsRendered;
					if(colspan <=0) colspan = 1;
					out.println("<td colspan=\" " + colspan + "\"></td>" );
				}
				
			%>	
		</tr>
		
		
		<tr class="linha_subtitulo">
			<td>Indicadores de Resultado</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<%
				Iterator ite = meses.iterator();
				while(ite.hasNext()){
					String mes = ite.next().toString().split("-")[0];
				%><td><%= Data.getAbreviaturaMes(Integer.valueOf(mes)) %></td><%
				}				
			%>			
			<td>Total</td>
		</tr>	
		<tr><td class="espacador" colspan="<%=meses.size() + 5%>"><img src="../../images/pixel.gif"></td></tr>		
		<%
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);		
		Iterator itIndicadores = indResultados.iterator();
		String grupoIndicador = "";
		while(itIndicadores.hasNext()){
			AcompRealFisicoArf indicador = (AcompRealFisicoArf) itIndicadores.next();
			String tipoQtde = indicador.getItemEstrtIndResulIettr().getIndTipoQtde();
			
			if(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
				if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
					grupoIndicador = indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
					%>
					<tr>
						<td class="titulo" colspan="<%=meses.size() + 5%>"><%=grupoIndicador%></td>
					</tr>
					<%
					
				}
			}
			
			Map mapMeses = acompRealFisicoDao.getQtdRealizadaExercicioPorMes(exercicio, indicador.getItemEstrtIndResulIettr());
			List valores = new ArrayList();
			%>
			
			<tr class="linha_subtitulo2">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
				<td>P</td>
				<!--  aqui vem previsto para o ano -->
				<%
				
			
				double totalPrevisto = 0.0;
				for(String datas: meses){
					String mes = datas.split("-")[0];
					String ano = datas.split("-")[1];
					EcarData ecarData = new EcarData(mes, ano);
					
					IndicadorResultado wrapperIndicador = new IndicadorResultado(indicador.getItemEstrtIndResulIettr());
					Previsto prev = wrapperIndicador.getPrevistoMensal(ecarData);
					
					String valor = "-";
					
					if(prev != null){
						valor = prev.getPrevistoFormatado();
						totalPrevisto += prev.getValorPrevisto();
					}
					%>
					
					<td> <%= valor %> </td>
			<% 
				}
				
				String totalPrevistoStr = "";
				if("Q".equals(tipoQtde)){
					totalPrevistoStr = Pagina.trocaNullComDecimal(totalPrevisto);
				}else{
					totalPrevistoStr = Pagina.trocaNullMoeda(totalPrevisto);
				}
				
				
			%>
			
			<td><%=totalPrevistoStr%></td>
			
			</tr>
			
			<tr class="linha_subtitulo2">
				<td><%=indicador.getItemEstrtIndResulIettr().getNomeIettir()%></td>
				<td><a href="javascript:abrirPopUpGraficoRealizadoNoExercicio(<%=ari.getCodAri()%>, <%=exercicio.getCodExe()%>, <%=indicador.getItemEstrtIndResulIettr().getCodIettir()%>)"><img src="../../images/relAcomp/icon_grafico.png" border="0"></a></td>
				<td>R</td>
				<%
				Iterator itMeses = meses.iterator();
				double total = 0;
				while(itMeses.hasNext()){
					boolean possuiValor = true;
					String mesAnoMap = itMeses.next().toString();
					Object m = mapMeses.get(mesAnoMap);
					if(m == null) {
						possuiValor = false;
					}
					
					String porLocal = indicador.getItemEstrtIndResulIettr().getIndRealPorLocal();
						
					if(possuiValor){
						Double valor = new Double(m.toString());
						valores.add(valor);
					%>
						<td>
							<%
							
							String strValor = "";
							if("Q".equals(tipoQtde))
								strValor = Pagina.trocaNullComDecimal(valor);
							else
								strValor = Pagina.trocaNullMoeda(valor);
							
							if("S".equals(porLocal)) {
								AcompRealFisicoArf arfMesAno = acompRealFisicoDao.buscarPorIettir(
										Long.valueOf(mesAnoMap.split("-")[0]),
										Long.valueOf(mesAnoMap.split("-")[1]),
										indicador.getItemEstrtIndResulIettr().getCodIettir());
							%>
								<a href="javascript:abrirPopUpConsultaRealizadoPorLocal(form, '<%=arfMesAno.getCodArf().toString()%>','<%=indicador.getItemEstrtIndResulIettr().getNomeIettir()%>')" title="Realizado por Local">
									<%=strValor%>
								</a>
								
							<%
							} else {
							%>
								<%=strValor%>
							<%
							}
							%>
						</td>
					<%
					} else {
						%><td> - </td><%
					}

				}	
			%>
									 
			<td>
				<%
				//Inverter a lista de valores para que o último exercício fique na primeira posição da lista
				//Isso é usado no método que soma os valores...
				Collections.reverse(valores); 
				total = acompRealFisicoDao.getSomaValoresArfs(indicador.getItemEstrtIndResulIettr(), valores).doubleValue();
				String strTotal = "";
				if("Q".equals(tipoQtde))
					strTotal = Pagina.trocaNullComDecimal(Double.valueOf(total));
				else
					strTotal = Pagina.trocaNullMoeda(Double.valueOf(total));
				%>
				
				<%=strTotal%> <%=new ItemEstrtIndResulDao(null).getUnidadeUsada(indicador.getItemEstrtIndResulIettr())%>
			</td>			
			</tr>
			<%
		}		
		%>
		
		<tr><td class="espacador" colspan="<%=meses.size() + 5%>"><img src="../../images/pixel.gif"></td></tr>		
	</table>

	<%
} catch(ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>		
</form>		
</div>
</body>
</html>
