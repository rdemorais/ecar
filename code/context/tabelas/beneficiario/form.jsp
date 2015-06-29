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
	<td>
		<textarea name="nomeBnf" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(beneficiario.getNomeBnf())%></textarea>
	</td>
</tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoBnf" valorSelecionado="<%=beneficiario.getIndAtivoBnf()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
</tr>
		<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>	