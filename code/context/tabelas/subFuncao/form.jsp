<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label">Código</td>
		<td><input type="text" name="codigoIdentSare" size="15" maxlength="10" value="<%=Pagina.trocaNull(subArea.getCodigoIdentSare())%>" <%=_disabled%>></td>
	</tr> 
	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="nomeSare" size="65" maxlength="60" value="<%=Pagina.trocaNull(subArea.getNomeSare())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoSare" valorSelecionado="<%=subArea.getIndAtivoSare()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	
	<% /*  controla o estado do menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp" %>
