<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
	<INPUT type="hidden" name="indTabelaUso" value="<%=indTabelaUso%>">	
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>"/>
		
	<tr>
		<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><input type="text" name="descricaoSitd" size="30" maxlength="30" value="<%=Pagina.trocaNull(situacao.getDescricaoSitd())%>" <%=_disabled%>></td>	
	</tr>
	<tr>
		<td class="label"> Representa Conclusão</td>
		<td>
			<% if (situacao.getIndConclusaoSitd() != null || _comboSimNao == "Todos") {%>
			<util:comboSimNaoTag nome="indConclusaoSitd" valorSelecionado="<%=situacao.getIndConclusaoSitd()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>		
			<% } else { %>
			<util:comboSimNaoTag nome="indConclusaoSitd" valorSelecionado="N" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>	
			<% } %>	
		</td>
	</tr>
	<tr>
		<td class="label"> Representa Primeira Situação</td>
		<td>
			<% if (situacao.getIndPrimeiraSituacaoSitd() != null || _comboSimNao == "Todos") {%>
			<util:comboSimNaoTag nome="indPrimeiraSituacaoSitd" valorSelecionado="<%=situacao.getIndPrimeiraSituacaoSitd()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>		
			<% } else { %>
			<util:comboSimNaoTag nome="indPrimeiraSituacaoSitd" valorSelecionado="N" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>		
			<% } %>
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>