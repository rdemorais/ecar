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
		<td><input type="text" name="nomeFonr" size="35" maxlength="30" value="<%=Pagina.trocaNull(fonteRecurso.getNomeFonr())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaFonr" size="15" maxlength="10" value="<%=Pagina.trocaNull(fonteRecurso.getSiglaFonr())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Orçamentária</td>
		<td>
			<input type="radio" class="form_check_radio" name="indOrcamentoFonr" value="S" <%=Pagina.isChecked(fonteRecurso.getIndOrcamentoFonr(), "S")%> <%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indOrcamentoFonr" value="N" <%=Pagina.isChecked(fonteRecurso.getIndOrcamentoFonr(), "N")%> <%=_disabled%>>Não
			
			<%
				if (ehPesquisa){
			%>
			 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indOrcamentoFonr)">
				<br>
			<%
				}
			%>
			
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sistema</td>
		<td>
		<%if ( fonteRecurso.getConfigSisExecFinanCsef() != null ) {%>
				<combo:ComboTag 
						nome="configSisExecFinanCsef"
						objeto="ecar.pojo.ConfigSisExecFinanCsef" 
						label="nomeCsef" 
						value="codCsef" 
						order="nomeCsef" 
						selected="<%=fonteRecurso.getConfigSisExecFinanCsef().getCodCsef().toString()%>"
						scripts="<%=_disabled%>"
				/>		
		<%} else {%>
				<combo:ComboTag 
						nome="configSisExecFinanCsef"
						objeto="ecar.pojo.ConfigSisExecFinanCsef" 
						label="nomeCsef" 
						value="codCsef" 
						order="nomeCsef"
						scripts="<%=_disabled%>"
				/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Início de Validade</td>
		<td>
			<input type="text" name="dataIniValidadeFonr" size="15" maxlength="10" value="<%=Pagina.trocaNullData(fonteRecurso.getDataIniValidadeFonr())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIniValidadeFonr, '')">
			</c:if>
			
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Fim de Validade</td>
		<td>
			<input type="text" name="dataFimValidadeFonr" size="15" maxlength="10" value="<%=Pagina.trocaNullData(fonteRecurso.getDataFimValidadeFonr())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFimValidadeFonr, '')">
			</c:if>
			
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sequência</td>
		<td>
			<input type="text" name="sequenciaFonr" size="5" maxlength="2" value="<%=Pagina.trocaNull(fonteRecurso.getSequenciaFonr())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoFonr" valorSelecionado="<%=fonteRecurso.getIndAtivoFonr()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>