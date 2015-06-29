<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Identificação</td>
	<td><input type="text" name="identificacaoDtqa" size="35" maxlength="30" value="<%=Pagina.trocaNull(destaqueArea.getIdentificacaoDtqa())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td><input type="text" name="nomeDtqa" size="25" maxlength="20" value="<%=Pagina.trocaNull(destaqueArea.getNomeDtqa())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>
	<td>
		<textarea name="descricaoDtqa" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(destaqueArea.getDescricaoDtqa())%></textarea>		
	</td>	
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Número de Colunas</td>
	<td><input type="text" name="qtdColunasDtqa" size="5" maxlength="3" value="<%=Pagina.trocaNull(destaqueArea.getQtdColunasDtqa())%>" <%=_disabled%>></td>
</tr> 
<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
