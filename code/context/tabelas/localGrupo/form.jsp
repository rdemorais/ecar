<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="identificacaoLgp" size="45" maxlength="40" value="<%=Pagina.trocaNull(localGrupo.getIdentificacaoLgp())%>" <%=_disabled%>></td>
	</tr>
	
	<tr>
		<td class="label">Sigla</td>
		<td><input type="text" name="siglaLgp" size="10" maxlength="10" value="<%=Pagina.trocaNull(localGrupo.getSiglaLgp())%>" <%=_disabled%>></td>
	</tr>
	
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoLgp" valorSelecionado="<%=localGrupo.getIndAtivoLgp()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
			</td>
		</tr>
		<tr><td class="label">&nbsp;</td></tr>

<%@ include file="../../include/estadoMenu.jsp"%>