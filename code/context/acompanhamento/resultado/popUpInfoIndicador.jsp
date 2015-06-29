<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %> 
<%@ page import="ecar.dao.ItemEstrutFisicoDao" %> 
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %> 

<%@ page import="ecar.dao.AbaDao" %>
<%@ page import="ecar.pojo.Aba" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<body class="corpo_popup">

<div>

<%
try{
	Long codIndicador = Long.valueOf(Pagina.getParamStr(request, "codIndicador"));
	ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) new ItemEstrtIndResulDao(request).buscar(ItemEstrtIndResulIettr.class, codIndicador);
	
	ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
	
	itemEstrutura = indicador.getItemEstruturaIett();
	
	ExercicioDao exercicioDao = new ExercicioDao(request);
	
	String permiteProjecao = "";
	if("S".equals(indicador.getIndProjecaoIettr()))
		permiteProjecao = "Sim";
	if("N".equals(indicador.getIndProjecaoIettr()))
		permiteProjecao = "Não";
	String eAcumulavel = "";
	if("S".equals(indicador.getIndAcumulavelIettr()))
		eAcumulavel = "Sim";
	if("N".equals(indicador.getIndAcumulavelIettr()))
		eAcumulavel = "Não";
	String ePublico = "";
	if("S".equals(indicador.getIndAcumulavelIettr()))
		ePublico = "Sim";
	if("N".equals(indicador.getIndAcumulavelIettr()))
		ePublico = "Não";
	String porLocal = "";
	if("S".equals(indicador.getIndRealPorLocal()))
		porLocal = "Sim";
	if("N".equals(indicador.getIndRealPorLocal()))
		porLocal = "Não";
	String formato = "";
	if("Q".equals(indicador.getIndTipoQtde()))
			formato = "Quantidade";
	if("V".equals(indicador.getIndTipoQtde()))
		formato = "Valor";
	String valorFinal = "";
	if("M".equals(indicador.getIndValorFinalIettr()))
		valorFinal = "Maior";
	if("U".equals(indicador.getIndValorFinalIettr()))
		valorFinal = "Ultimo";
	if("N".equals(indicador.getIndValorFinalIettr()))
		valorFinal = "Não se aplica";
	
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("REL_FISICO_IND_RESULTADO");
	aba = (Aba)((new AbaDao(request)).pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
	String nomeAbaSelecionada = "";
	
	if(itemEstrutura!=null){
		nomeAbaSelecionada = abaDao.getLabelAbaEstrutura(aba, itemEstrutura.getEstruturaEtt());
	}
	else{
		nomeAbaSelecionada = "Indicador de Resultado";
	}
%>
<H1><%=nomeAbaSelecionada %></H1>
<H1><%=indicador.getNomeIettir()%></H1>

<form name="form">
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form">
		<tr>
			<td class="label">Conceituação:</td><td><%=indicador.getConceituacao() %></td>
		</tr>
		<tr>
			<td class="label">Interpretação:</td><td><%=indicador.getInterpretacao() %></td>
		</tr>
		<tr>
			<td class="label">Método de Cálculo:</td><td><%=indicador.getMetodoCalculo() %></td>
		</tr>
		<tr>
			<td class="label">Fonte:</td><td><%=indicador.getFonteIettr() %></td>
		</tr>
		<tr>
			<td class="label">Informações Complementares:</td><td><%=indicador.getDescricaoIettir()%></td>
		</tr>
		<tr>		
			<td class="label">Unidade de Medida:</td><td><%=new ItemEstrtIndResulDao(null).getUnidadeUsada(indicador)%></td>
		</tr>
		<tr>
			<td class="label">Formato:</td><td><%=formato %></td>
		</tr>
		<tr>
			<td class="label">Projeção:</td><td><%=permiteProjecao%></td>
		</tr>
		<tr>
			<td class="label">Acumulável:</td><td><%=eAcumulavel%></td>
		</tr>
		<tr>
			<td class="label">Público:</td><td><%=ePublico%></td>
		</tr>
		<tr>
			<td class="label">Valor Final:</td><td><%=valorFinal%></td>
		</tr>
		<tr>
			<td class="label">Qtde informada realizada por local:</td><td><%=porLocal%></td>
		</tr>

		<tr>			
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<table align="center">
					<tr>
						<td colspan="2" align="center"><b>Quantidade Prevista</b></td>
					</tr>
		
					<tr>
						<td class="label"><b>Exercício</b></td>
						<td width="25%" align="center"><b>Quantidade Prevista</b></td>
						<td width="45%" class="texto"></td>
					</tr>
						
		<%
				StringBuffer validaQtdPrev = new StringBuffer();
				
				List listaExercicio = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
				Iterator itExercicio = listaExercicio.iterator();
				
				ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
				
				if (itExercicio.hasNext()) {
					while (itExercicio.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExercicio.next();
						Double qtdePrevista = itemEstrutFisicoDao.getQtdPrevistaExercicio(exercicio, indicador, null);						
												
		%>				
						<tr>
							<td class="label">
								<%=exercicio.getDescricaoExe()%>
							</td>
							<td align="center" class="texto">
								<%
								String sPrevisto;
								if("Q".equals(indicador.getIndTipoQtde())){
									sPrevisto=Pagina.trocaNullNumeroDecimal(qtdePrevista);
								}else{
									sPrevisto=Pagina.trocaNullMoeda(qtdePrevista);												
								}
								%>
								<%=sPrevisto%>
							</td>
							<td class="texto"></td>
						</tr>
		<%
						validaQtdPrev.append("form.qtdPrevistaIettf").append(exercicio.getCodExe().toString());
						validaQtdPrev.append(".value = Trim(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString());
						validaQtdPrev.append(".value); \n");
						validaQtdPrev.append("retiraPontos(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString()).append("); \n");
						validaQtdPrev.append("if(form.indTipoQtde[0].checked == true){ \n");
						validaQtdPrev.append("\t if(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString()).append(".value != \"\" && !isInteger(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString()).append(".value)){ \n");
						validaQtdPrev.append("\t\t alert(\"" + _msg.getMensagem("itemEstrutura.quantPrevista.validacao.indTipoQtde.valorInt") + "\"); \n");
						validaQtdPrev.append("\t\t form.qtdPrevistaIettf").append(exercicio.getCodExe().toString()).append(".focus(); \n");
						validaQtdPrev.append("\t\t return(false); \n");
						validaQtdPrev.append("\t }");
						validaQtdPrev.append("}");
						
						validaQtdPrev.append("if (Trim(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString());
						validaQtdPrev.append(".value) != \"\") { \n");
						validaQtdPrev.append("	\t if(!validaDecimal(form.qtdPrevistaIettf").append(exercicio.getCodExe().toString());
						validaQtdPrev.append(".value)){ \n");
						validaQtdPrev.append("	\t	\t alert(\"").append( _msg.getMensagem("itemEstrutura.quantPrevista.validacao.qtdPrevistaIettf.invalido"));
						validaQtdPrev.append("\"); \n");
						validaQtdPrev.append("	\t	\t form.qtdPrevistaIettf").append(exercicio.getCodExe().toString());
						validaQtdPrev.append(".focus(); \n");
						validaQtdPrev.append("	\t	\t return(false); \n");
						validaQtdPrev.append("	\t } \n");
						validaQtdPrev.append("} \n");
						validaQtdPrev.append("\n");
					}
				} else {
		%>
					<tr>
						<td class="texto"></td>
						<td align="center" class="texto" colspan="2">
							Não há exercícios.
						</td>
						<td class="texto"></td>
					</tr>
		<%
				}
		%>
			
				</table>
			</td>
		</tr>
		
		
		
		
		
		
		
		<tr>
			<td colspan="2" align="right"><input type="button" value="Fechar" class="botao" onclick="javascript:window.close();"></td>
		</tr>
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
