<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}	
}
</script>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="nomeRec" size="35" maxlength="100" value="<%=Pagina.trocaNull(recurso.getNomeRec())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaRec" size="15" maxlength="10" value="<%=Pagina.trocaNull(recurso.getSiglaRec())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Orçamentária</td>
		<td>
			<input type="radio" class="form_check_radio" name="indOrcamentoRec" value="S" <%=Pagina.isChecked(recurso.getIndOrcamentoRec(), "S")%> <%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indOrcamentoRec" value="N" <%=Pagina.isChecked(recurso.getIndOrcamentoRec(), "N")%> <%=_disabled%>>Não
			
			<%
				if (ehPesquisa){
			%>
			 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indOrcamentoRec)">
				<br>
			<%
				}
			%>
			
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Início de Validade</td>
		<td>
			<input type="text" name="dataIniValidadeRec" size="15" maxlength="10" value="<%=Pagina.trocaNullData(recurso.getDataIniValidadeRec())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIniValidadeRec, '')">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Fim de Validade</td>
		<td>
			<input type="text" name="dataFimValidadeRec" size="15" maxlength="10" value="<%=Pagina.trocaNullData(recurso.getDataFimValidadeRec())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFimValidadeRec, '')">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sequência</td>
		<td>
			<input type="text" name="sequenciaRec" size="5" maxlength="3" value="<%=Pagina.trocaNull(recurso.getSequenciaRec())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoRec" valorSelecionado="<%=recurso.getIndAtivoRec()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>