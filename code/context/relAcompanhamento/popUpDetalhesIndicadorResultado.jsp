
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

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
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.Data" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript">
function abrirPopUpGraficoRealizadoNoExercicio(codAri, codExe, codIettir){
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
	if ( !validaPermissao.permissaoConsultaParecerIETT( ari.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	ExercicioExe exercicio = (ExercicioExe) new ExercicioDao(request).buscar(ExercicioExe.class, codExe);
	ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
	//ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
%>

<H1><%=ari.getItemEstruturaIett().getEstruturaEtt().getNomeEtt()%>:<%=ari.getItemEstruturaIett().getNomeIett()%></H1>
<H1>Realizado no Ciclo: <%=exercicio.getDescricaoExe()%></H1>

<form name="form">

	<%
	List meses = new ArrayList();
	if(exercicio.equals(ari.getAcompReferenciaAref().getExercicioExe())){
		int mesRef = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue();
		int anoRef = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue();	
		meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio,mesRef,anoRef);
	}else
		meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio);
	%>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="<%=meses.size() + 3%>"><img src="../images/pixel.gif"></td></tr>
		<tr class="linha_subtitulo">
			<td>Indicadores de Resultado</td>
			<td>&nbsp;</td>
			<%
			Iterator it = meses.iterator();
			while(it.hasNext()){
				String mes = it.next().toString().split("-")[0];
				%><td><%=Data.getAbreviaturaMes(Integer.valueOf(mes).intValue())%></td><%
			}				
			%>			
			<td>Total</td>
		</tr>	
		<tr><td class="espacador" colspan="<%=meses.size() + 3%>"><img src="../images/pixel.gif"></td></tr>		
		<%
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, true);		
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
						<td class="titulo" colspan="<%=meses.size() + 3%>"><%=grupoIndicador%></td>
					</tr>
					<%
					
				}
			}
			
			Map mapMeses = acompRealFisicoDao.getQtdRealizadaExercicioPorMes(exercicio, indicador.getItemEstrtIndResulIettr());
			List valores = new ArrayList();
			%>
			<tr class="linha_subtitulo2">
				<td><%=indicador.getItemEstrtIndResulIettr().getNomeIettir()%></td>
				<td><a href="javascript:abrirPopUpGraficoRealizadoNoExercicio(<%=ari.getCodAri()%>, <%=exercicio.getCodExe()%>, <%=indicador.getItemEstrtIndResulIettr().getCodIettir()%>)"><img src="../images/relAcomp/icon_grafico.png" border="0"></a></td>
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
								strValor = Pagina.trocaNullNumeroSemDecimal(valor);
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
					strTotal = Pagina.trocaNullNumeroSemDecimal(Double.valueOf(total));
				else
					strTotal = Pagina.trocaNullMoeda(Double.valueOf(total));
				%>
				
				<%=strTotal%> <%=new ItemEstrtIndResulDao(null).getUnidadeUsada(indicador.getItemEstrtIndResulIettr())%>
			</td>			
			</tr>
			<%
		}		
		%>
		<tr><td class="espacador" colspan="<%=meses.size() + 3%>"><img src="../images/pixel.gif"></td></tr>		
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
