<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Área</td>
	<td>
		<%
		String strArea = "";
		if(destaqueSubArea.getDestaqueAreaDtqa() != null){
			strArea = destaqueSubArea.getDestaqueAreaDtqa().getCodDtqa().toString();
		}
		%>
		<combo:ComboTag 
				nome="destaqueAreaDtqa"
				objeto="ecar.pojo.DestaqueAreaDtqa" 
				label="nomeDtqa" 
				value="codDtqa" 
				order="nomeDtqa"
				scripts="<%=_disabled%>"
				selected="<%=strArea%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Identificação</td>
	<td><input type="text" name="identificacaoDtqsa" size="35" maxlength="30" value="<%=Pagina.trocaNull(destaqueSubArea.getIdentificacaoDtqsa())%>" <%=_disabled%>></td>
</tr> 

<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>
	<td>
		<textarea name="descricaoDtqsa" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(destaqueSubArea.getDescricaoDtqsa())%></textarea>		
	</td>	
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Quantidade Max. de Itens</td>
	<td><input type="text" name="qtdMaxItensDtqsa" size="5" maxlength="3" value="<%=Pagina.trocaNull(destaqueSubArea.getQtdMaxItensDtqsa())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Tipo de Ordenação</td>
	<td>
		<%
		String strOrdenacao = "";
		if(destaqueSubArea.getDestaqueTipoOrdemDtqto() != null){
			strOrdenacao = destaqueSubArea.getDestaqueTipoOrdemDtqto().getCodDtqto().toString();
		}
		%>
		<combo:ComboTag 
				nome="destaqueTipoOrdemDtqto"
				objeto="ecar.pojo.DestaqueTipoOrdemDtqto" 
				label="identificacaoDtqto" 
				value="codDtqto" 
				order="identificacaoDtqto"
				scripts="<%=_disabled%>"
				selected="<%=strOrdenacao%>"
		/>		
	</td>
</tr> 
<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
