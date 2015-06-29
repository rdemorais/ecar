
<jsp:directive.page import="ecar.util.Dominios"/><%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
	
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<input type="hidden" name="codigo" value="<%=Pagina.trocaNull(poder.getCodPod()) %>">

	<tr>
		<td class="label"><%=_obrigatorio%> Nome Poder</td>
		<td>
			<input type="text" name="nomePod" size="15" maxlength="20" value="<%=Pagina.trocaNull(poder.getNomePod())%>" <%=_disabled%>></input>
		</td>
	</tr> 
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaPod" size="15" maxlength="10" value="<%=Pagina.trocaNull(poder.getSiglaPod())%>" <%=_disabled%>></td>
	</tr> 
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoPod" valorSelecionado="<%=poder.getIndAtivoPod()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	
	<tr><td class="label">&nbsp;</td></tr>
	
	<% /* controla o estado do Menu (aberto ou fechado ) */ %>
	<%@ include file="../../include/estadoMenu.jsp" %>
