<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Sigla UF</td>
	<td><input type="text" name="codUf" size="4" maxlength="2" value="<%=Pagina.trocaNull(uf.getCodUf())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
	<td><input type="text" name="descricaoUf" size="35" maxlength="30" value="<%=Pagina.trocaNull(uf.getDescricaoUf())%>" <%=_disabled%>></td>
</tr>
<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
