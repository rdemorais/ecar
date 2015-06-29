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
	<td><input type="text" name="codigoIdentAre" size="15" maxlength="10" value="<%=Pagina.trocaNull(area.getCodigoIdentAre())%>" <%=_disabled%>></td>
</tr> 

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td><input type="text" name="nomeAre" size="35" maxlength="30" value="<%=Pagina.trocaNull(area.getNomeAre())%>" <%=_disabled%>></td>
</tr> 

<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoAre" valorSelecionado="<%=area.getIndAtivoAre()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
