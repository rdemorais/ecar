
<jsp:directive.page import="ecar.util.Dominios"/><%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
	
	String codUsu = new String();
	String nomeUsu = new String();
	
	if (unidade.getUsuarioUsu() != null){
		codUsu = unidade.getUsuarioUsu().getCodUsu().toString();
		nomeUsu = unidade.getUsuarioUsu().getNomeUsuSent();
	}else{
		codUsu = "";
		nomeUsu = "";
	}
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label"><%=_obrigatorio%> Órgão</td>
		<td>
<%
			if ( unidade.getOrgaoOrg() != null ) {
%>
				<combo:ComboTag 
						nome="orgaoOrg"
						objeto="ecar.pojo.OrgaoOrg" 
						label="siglaOrg,descricaoOrg" 
						value="codOrg" 
						order="siglaOrg"
						filters="indAtivoOrg=S"
						selected="<%=unidade.getOrgaoOrg().getCodOrg().toString()%>"
						scripts="<%=_disabled%>"
				/>		
<%
			} else {
%>
				<combo:ComboTag 
						nome="orgaoOrg"
						objeto="ecar.pojo.OrgaoOrg" 
						label="siglaOrg,descricaoOrg" 
						value="codOrg" 
						order="siglaOrg"
						filters="indAtivoOrg=S"
						scripts="<%=_disabled%>"
				/>		
<%
			}
%>
		</td>
	</tr> 
	<tr>
		<td class="label">Código</td>
		<td><input type="text" name="codigoIdentUo" size="15" maxlength="10" value="<%=Pagina.trocaNull(unidade.getCodigoIdentUo())%>" <%=_disabled%>></td>
	</tr> 
	<tr>
		<td class="label"><%=_obrigatorio%> Nome Unidade</td>
		<td><input type="text" name="descricaoUo" size="40" maxlength="2000" value="<%=Pagina.trocaNull(unidade.getDescricaoUo())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaUo" size="10" maxlength="10" value="<%=Pagina.trocaNull(unidade.getSiglaUo())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Usuário Autorizador</td>
		<td>
			<input type="hidden" name="codUsu" value="<%=codUsu%>">
			<input type="text" name="nomeUsu" size="55" value="<%=nomeUsu%>" disabled>
			<input type="button" class="botao" name="pesquisarUsu" value="Pesquisar" onclick="popup_pesquisa('ecar.popup.PopUpUsuario')" <%=_disabled%>>
			<input type="button" class="botao" name="limparUsu" value="Limpar" onclick="javascript:limpar(form)">
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
		<util:comboSimNaoTag nome="indAtivoUo" valorSelecionado="<%=unidade.getIndAtivoUo()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	
	
	<tr>
		<td class="label">Administração</td>
		<td>
		<%
		String selecionado = unidade.getIndTipoAdministracaoUo();
		%> 
			<select name="indTipoAdmUo" <%=_disabled%>>
				<option value="" <%=(selecionado == null || "".equals(selecionado)) ? "selected" : ""%>><%=comboAdm %></option>
				<option value="<%=Dominios.TIPO_ADM_DIRETA %>" <%=((Dominios.TIPO_ADM_DIRETA).equals(selecionado)) ? "selected" : "" %>>Direta</option>
				<option value="<%=Dominios.TIPO_ADM_INDIRETA %>" <%=((Dominios.TIPO_ADM_INDIRETA).equals(selecionado)) ? "selected" : "" %>>Indireta</option>
			</select>		
		</td>	
	</tr>
	
	<tr>
		<td class="label">Orçamento</td>
		<td>
		<%
		selecionado = unidade.getIndTipoOrcamentoUo();
		%>
			<select name="indTipoOrcUo" <%=_disabled%>>
				<option value="" <%=(selecionado == null || "".equals(selecionado)) ? "selected" : ""%>><%=comboOrc%></option>
				<option value="<%=Dominios.TIPO_ORC_PROPRIO %>" <%=((Dominios.TIPO_ORC_PROPRIO).equals(selecionado)) ? "selected" : "" %>>Próprio</option>
				<option value="<%=Dominios.TIPO_ORC_FISCAL %>" <%=((Dominios.TIPO_ORC_FISCAL).equals(selecionado)) ? "selected" : "" %> >Fiscal</option>
				<option value="<%=Dominios.TIPO_ORC_INVESTIMENTO %>" <%=((Dominios.TIPO_ORC_INVESTIMENTO).equals(selecionado)) ? "selected" : "" %> >Investimento</option>
			</select>		
		</td>	
	</tr>
	
	
	<tr><td class="label">&nbsp;</td></tr>
	
	<% /* controla o estado do Menu (aberto ou fechado ) */ %>
	<%@ include file="../../include/estadoMenu.jsp" %>
