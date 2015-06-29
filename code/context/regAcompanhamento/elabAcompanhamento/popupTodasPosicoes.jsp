<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.AcompRelatorioArel" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="java.util.List" %>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<%@ include file="../../frm_global.jsp"%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/popUp.js"></script>
<script>

</script>
</head>
<body>
<br>
<%
try{
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));
	TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) new TipoFuncAcompDao(request).buscar(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParamStr(request,"codTpfa")));
	
	String ocultarObservacoesParecer = configuracaoCfg.getIndOcultarObservacoesParecer();
	
%>
	<H1>Relação de Todas as Posições Emitidas
	<br>
	<%=funcao.getLabelPosicaoTpfa()%>
	</H1>
<%
	List arels = acompReferenciaItemDao.getUltimosAcompanhamentosItem(acompReferenciaItem, funcao, null);

	if(arels != null && arels.size() > 0){
%>
	<table class="form">
		<tr>
			 <td class="label" colspan="2">&nbsp;</td>
		</tr> 
		<%
		String periodo = "";
		String mesAno = "";
		String situacao = "";
		String cor = "";
		String avaliacao = "";
		String observacao = "";

		Iterator itArels = arels.iterator();
		while(itArels.hasNext()){					
			AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
					
			if(arel.getAcompReferenciaItemAri() != null &&
			arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
				periodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
				mesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" + arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
			}
													
			if(arel.getSituacaoSit() != null){
				situacao = arel.getSituacaoSit().getDescricaoSit();
			}
			else{
				situacao = "N/I";
			}
			
			if(arel.getCor() != null){
				cor = "<img src=\"" + "../../images/" + new CorDao(request).getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + 
					  "\" align=\"top\">";
			}
			else {
				cor = "N/I";
			}
						
			if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
				avaliacao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
			}
			else {
				avaliacao = "N/I";
			}
						
			if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
				observacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
			}
			else {
				observacao = "N/I";
			}
			%>
				
			<tr>
				<td class="label">Per&iacute;odo</td>
				<td><%=periodo%></td>
			</tr>
			<tr>
				<td class="label">Mês/Ano</td>
				<td><%=mesAno%></td>
			</tr>
			<tr>
				 <td class="label">Situa&ccedil;&atilde;o:</td>
				 <td><%=situacao%></td>
			</tr> 	
			<tr>
				 <td class="label">Cor:</td>
				 <td><%=cor%></td>
			</tr> 
			<tr>
				 <td class="label">Avalia&ccedil;&atilde;o:</td>
				 <td><%=avaliacao%></td>
			</tr> 
			
			<%
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
			%>
			
				<tr>
					 <td class="label">Observa&ccedil;&otilde;es:</td>
					 <td><%=observacao%></td>
				</tr>
			
			<%
			}
			%>
			
			<tr>
				 <td class="label" colspan="2">&nbsp;</td>
			</tr> 
	<%
		}
	%>
	</table>
	<%
	}
	else {
		out.println("Não existem registros informados.");
	}
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception  e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

