<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Descrição</td>
		<td><input type="text" name="descricaoPrdc" size="35" maxlength="30" value="<%=Pagina.trocaNull(periodicidade.getDescricaoPrdc())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Número de Meses</td>
		<td><input type="text" name="numMesesPrdc" size="5" maxlength="3" value="<%=Pagina.trocaNull(periodicidade.getNumMesesPrdc())%>" <%=_disabled%>></td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>