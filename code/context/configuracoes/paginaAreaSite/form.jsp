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
	<td><input type="text" name="nomePas" size="35" maxlength="30" value="<%=Pagina.trocaNull(paginaAreaSite.getNomePas())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Texto</td>
	<td>
		<textarea name="textoPas" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(paginaAreaSite.getTextoPas())%></textarea>
	</td>
</tr>
		<td class="label"><%=_obrigatorio%> Link na capa</td>
		<td>
			<input type="radio" class="form_check_radio" name="indCapaPas" value="S" <%=Pagina.isChecked(paginaAreaSite.getIndCapaPas(), "S")%> <%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indCapaPas" value="N" <%=Pagina.isChecked(paginaAreaSite.getIndCapaPas(), "N")%> <%=_disabled%>>N&atilde;o
			<%
			if (ehPesquisa) {
			%>				
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indCapaPas)">
			<br>						
			<% 
			}
			%>
				
		</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> URL</td>
	<td><input type="text" name="urlPas" size="90" maxlength="100" value="<%=Pagina.trocaNull(paginaAreaSite.getUrlPas())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Título para URL</td>
	<td><input type="text" name="tituloPas" size="35" maxlength="30" value="<%=Pagina.trocaNull(paginaAreaSite.getTituloPas())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Cor</td>
	<td><input type="text" name="corPas" size="25" maxlength="20" value="<%=Pagina.trocaNull(paginaAreaSite.getCorPas())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Seq&uuml;&ecirc;ncia</td>
	<td><input type="text" name="seqApresentacaoPas" size="10" maxlength="5" value="<%=Pagina.trocaNull(paginaAreaSite.getSeqApresentacaoPas())%>" <%=_disabled%>></td>
</tr>
		<tr><td class="label">&nbsp;</td></tr>
	
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>