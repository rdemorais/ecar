<%@ page import="java.util.List" %>
<script language="javascript">
//Função utilizada nos botões "Avançar" e "Retroceder".
function nextPrevious(codAri){
	document.form.codAri.value = codAri;
	document.form.action = "<%=_pathEcar%>/regAcompanhamento/elabAcompanhamento/datasLimites.jsp";
	document.form.submit();
}
</script>
<%
String avancar = "";
String retroceder = "";
if(request.getSession().getAttribute("codArisControleElab") != null){
	List codArisControle = (List) request.getSession().getAttribute("codArisControleElab");
	
	List itensControle = new ArrayList();
	for(int i = 0; i < codArisControle.size(); i++){
		String codLido = (String) codArisControle.get(i);
		AcompReferenciaItemAri ariSessao = (AcompReferenciaItemAri) new AcompReferenciaItemDao(null).buscar(AcompReferenciaItemAri.class, Long.valueOf(codLido));
		
		if (Dominios.SIM.equals(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa())){
			if (ariSessao.getAcompReferenciaAref().getDiaAref().equals(ari.getAcompReferenciaAref().getDiaAref()) &&
					ariSessao.getAcompReferenciaAref().getMesAref().equals(ari.getAcompReferenciaAref().getMesAref()) && 
					ariSessao.getAcompReferenciaAref().getAnoAref().equals(ari.getAcompReferenciaAref().getAnoAref())){
				itensControle.add(ariSessao);
			}
		} else {
			if(ariSessao.getAcompReferenciaAref().equals(ari.getAcompReferenciaAref())){
				itensControle.add(ariSessao);
			}
		}
	}
	
	int tam = itensControle.size();
	for(int i = 0; i < tam; i++){
		AcompReferenciaItemAri ariLido = (AcompReferenciaItemAri) itensControle.get(i);
		if(ariLido.equals(acompReferenciaItem)){
			if(i > 0){
				AcompReferenciaItemAri ariAnt = (AcompReferenciaItemAri) itensControle.get(i-1);
				retroceder = ariAnt.getCodAri().toString();
			}
			if(i < (tam-1)){
				AcompReferenciaItemAri ariPos = (AcompReferenciaItemAri) itensControle.get(i+1);
				avancar = ariPos.getCodAri().toString();
			}
			break;
		}
	}
}
%>

<table align="center">
	<tr>
<%
String desabilitarBotao = "disabled";

if(!"".equals(retroceder)){
	desabilitarBotao = "";
}
%>
		<td>
			<input type="button" name="btRetroceder" value="&lt;&lt; Retroceder" onclick="javascript:nextPrevious(<%=retroceder%>)" <%=desabilitarBotao%>>
		</td>
<%
desabilitarBotao = "disabled";
if(!"".equals(avancar)){
	desabilitarBotao = "";
}
%>
		<td>
			<input type="button" name="btAvancar" value="Avançar &gt;&gt;" onclick="javascript:nextPrevious(<%=avancar%>)" <%=desabilitarBotao%>>
		</td>
	</tr>
</table>
