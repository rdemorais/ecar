<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />


<tr>
	<td class="label"><%=_obrigatorio%> Empresa</td>
	<td>
	<%if ( textoSite.getEmpresaEmp() != null ) {%>
		<combo:ComboTag 
			nome="empresaEmp"
			objeto="ecar.pojo.EmpresaEmp" 
			label="razaoSocialEmp" 
			value="codEmp" 
			order="razaoSocialEmp" 
			selected="<%=textoSite.getEmpresaEmp().getCodEmp().toString()%>"
			scripts="<%=_disabled%>"
		/>		
	<%} else {%>
		<combo:ComboTag 
			nome="empresaEmp"
			objeto="ecar.pojo.EmpresaEmp" 
			label="razaoSocialEmp" 
			value="codEmp" 
			order="razaoSocialEmp" 
			scripts="<%=_disabled%>"
		/>		
	<%}%>
	</td>
</tr>


<tr>
	<td class="label"><%=_obrigatorio%> Idioma</td>
	<td>
	<%if ( textoSite.getIdiomaIdm() != null ) {%>
		<combo:ComboTag 
			nome="idiomaIdm"
			objeto="ecar.pojo.IdiomaIdm" 
			label="nomeIdm" 
			value="codIdm" 
			order="nomeIdm" 
			selected="<%=textoSite.getIdiomaIdm().getCodIdm().toString()%>"
			scripts="<%=_disabled%>"
		/>		
	<%} else {%>
		<combo:ComboTag 
			nome="idiomaIdm"
			objeto="ecar.pojo.IdiomaIdm" 
			label="nomeIdm" 
			value="codIdm" 
			order="nomeIdm" 
			scripts="<%=_disabled%>"
		/>		
	<%}%>
	</td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Descrição/ Assunto</td>
	<td><input type="text" name="descricaoUsoTxts" size="63" maxlength="60" value="<%=Pagina.trocaNull(textoSite.getDescricaoUsoTxts())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Texto</td>
	<td>
		<textarea name="textoTxts" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(textoSite.getTextoTxts())%></textarea>
	</td>
</tr>

<tr>
	<td class="label"> E-mail do Responsável</td>
	<td>
		<input type="text" name="emailResponsavelTxts" size="55" maxlength="50" value="<%=Pagina.trocaNull(textoSite.getEmailResponsavelTxts())%>" <%=_disabled%>>
	</td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoTxts" valorSelecionado="<%=textoSite.getIndAtivoTxts()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>	
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<%@ include file="../../include/estadoMenu.jsp"%>
	