<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome da Área</td>
	<td><input type="text" name="nomeCtta" size="35" maxlength="30" value="<%=Pagina.trocaNull(contatoArea.getNomeCtta())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Assunto de Retorno</td>
	<td><input type="text" name="assuntoRetornoCtta" size="45" maxlength="40" value="<%=Pagina.trocaNull(contatoArea.getAssuntoRetornoCtta())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label">Texto de Retorno</td>
	<td>
		<textarea name="textoRetornoCtta" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" onkeydown="return validaTamanhoLimite(this, 2000);" onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(contatoArea.getTextoRetornoCtta())%></textarea>		
	</td>	
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Resposta Automática</td>
	<td>
		<util:comboSimNaoTag nome="indEmailRespostaCtta" valorSelecionado="<%=contatoArea.getIndEmailRespostaCtta()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Usuário Logado</td>
	<td>
		<util:comboSimNaoTag nome="indLogadoCtta" valorSelecionado="<%=contatoArea.getIndLogadoCtta()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoCtta" valorSelecionado="<%=contatoArea.getIndAtivoCtta()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
