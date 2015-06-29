<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Área de Contato</td>
	<td>
		<%
		String strArea = "";
		if(contatoMail.getContatoAreaCtta() != null){
			strArea = contatoMail.getContatoAreaCtta().getCodCtta().toString();
		}
		%>
		<combo:ComboTag 
				nome="contatoAreaCtta"
				objeto="ecar.pojo.ContatoAreaCtta" 
				label="nomeCtta" 
				value="codCtta" 
				order="nomeCtta"
				scripts="<%=_disabled%>"
				selected="<%=strArea%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>
	<td>
		<textarea name="nomeCttm" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(contatoMail.getNomeCttm())%></textarea>		
	</td>	
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> E-mail</td>
	<td><input type="text" name="emailCttm" size="55" maxlength="50" value="<%=Pagina.trocaNull(contatoMail.getEmailCttm())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Categoria</td>
	<td>
		<%
		String strCategoria = "";
		if(contatoMail.getContatoMailCategoriaCttm() != null){
			strCategoria = contatoMail.getContatoMailCategoriaCttm().getCodCttmc().toString();
		}
		%>
		<combo:ComboTag 
				nome="contatoMailCategoriaCttm"
				objeto="ecar.pojo.ContatoMailCategoriaCttm" 
				label="descricaoCttmc" 
				value="codCttmc" 
				order="descricaoCttmc"
				scripts="<%=_disabled%>"
				selected="<%=strCategoria%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> É Origem?</td>
	<td>
		<util:comboSimNaoTag nome="indOrigemCttm" valorSelecionado="<%=contatoMail.getIndOrigemCttm()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
