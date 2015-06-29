<%
if(acompReferenciaItem != null){
%>
<script language="javascript">
//Função utilizada nos botões "Avançar" e "Retroceder".
function nextPrevious(codAri,codIett){
	document.form.codAri.value = codAri;
	document.form.primeiroIettClicado.value = codIett;
	document.form.primeiroAriClicado.value = codAri;
	document.form.action = "avaliacoes.jsp?linkControle=S";
	document.form.submit();
}
</script>

<%
String retroceder = "";
String avancar = "";
if(!"".equals(Pagina.getParamStr(request, "codArisControle"))){
	String strCodArisControle = Pagina.getParamStr(request, "codArisControle");
	
	String[] codArisControle = strCodArisControle.split("\\|");
	
	List itensControle = new ArrayList();
	for(int i = 0; i < codArisControle.length; i++){
		String codLido = codArisControle[i];
		if(!"".equals(codLido)){

			AcompReferenciaItemAri ariSessao = (AcompReferenciaItemAri) new AcompReferenciaItemDao(null).buscar(AcompReferenciaItemAri.class, Long.valueOf(codLido));
			
			if (Dominios.SIM.equals(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa())){
				if (ariSessao.getAcompReferenciaAref().getDiaAref().equals(acompReferenciaItem.getAcompReferenciaAref().getDiaAref()) &&
						ariSessao.getAcompReferenciaAref().getMesAref().equals(acompReferenciaItem.getAcompReferenciaAref().getMesAref()) && 
						ariSessao.getAcompReferenciaAref().getAnoAref().equals(acompReferenciaItem.getAcompReferenciaAref().getAnoAref())){
					itensControle.add(ariSessao);
				}
			} else {
				if(ariSessao.getAcompReferenciaAref().equals(acompReferenciaItem.getAcompReferenciaAref())){
					itensControle.add(ariSessao);
				}
			}
		}
	}
	
	int tam = itensControle.size();
	for(int i = 0; i < tam; i++){
		AcompReferenciaItemAri ariLido = (AcompReferenciaItemAri) itensControle.get(i);
		if(ariLido.equals(acompReferenciaItem)){
			if(i > 0){
				AcompReferenciaItemAri ariAnt = (AcompReferenciaItemAri) itensControle.get(i-1);
				retroceder = ariAnt.getCodAri().toString() + "," + ariAnt.getItemEstruturaIett().getCodIett();
			}
			if(i < (tam-1)){
				AcompReferenciaItemAri ariPos = (AcompReferenciaItemAri) itensControle.get(i+1);
				avancar = ariPos.getCodAri().toString() + "," + ariPos.getItemEstruturaIett().getCodIett();
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
<%
}
%>