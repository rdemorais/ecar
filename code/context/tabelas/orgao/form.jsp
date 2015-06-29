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
		<td><input type="text" name="codigoIdentOrg" size="15" maxlength="10" value="<%=Pagina.trocaNull(orgao.getCodigoIdentOrg())%>" <%=_disabled%>></td>
	</tr> 
	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="descricaoOrg" size="40" maxlength="2000" value="<%=Pagina.trocaNull(orgao.getDescricaoOrg())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaOrg" size="10" maxlength="10" value="<%=Pagina.trocaNull(orgao.getSiglaOrg())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
		<util:comboSimNaoTag nome="indAtivoOrg" valorSelecionado="<%=orgao.getIndAtivoOrg()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">Poder</td>
		<td>
			<combo:ComboTag 
				nome="codPoderPod"
				objeto="ecar.pojo.PoderPod" 
				label="nomePod" 
				value="codPod" 
				order="nomePod"
				textoPadrao="<%=combo %>"				
				filters="indAtivoPod=S"
				scripts="<%=_disabled%>"
				selected="<%=( orgao.getPoderPod() != null && !"".equals(orgao.getPoderPod().getCodPod().toString()) ) ? orgao.getPoderPod().getCodPod().toString() : "" %>"
				
			/>
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>
	
	<% /* controla o estado do Menu (aberto ou fechado ) */ %>
	<%@ include file="../../include/estadoMenu.jsp" %>
