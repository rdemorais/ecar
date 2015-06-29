<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label" valign="top"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><textarea name="descricaoCri" rows="4"  cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(criterio.getDescricaoCri())%></textarea></td>
	</tr>
	
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoCri" valorSelecionado="<%=criterio.getIndAtivoCri()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
			</td>
		</tr>
		<tr><td class="label">&nbsp;</td></tr>
		
		<%@ include file="../../include/estadoMenu.jsp"%>
