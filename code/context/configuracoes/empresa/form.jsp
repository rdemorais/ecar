<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disbled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 ConfiguracaoCfg conf = (ConfiguracaoCfg)session.getAttribute("configuracao");
 String pathRaiz = conf.getRaizUpload(); 
 %>


<%@page import="comum.util.FileUpload"%><util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Razão Social</td>
		<td><input type="text" name="razaoSocialEmp" size="55" maxlength="50" value="<%=Pagina.trocaNull(empresa.getRazaoSocialEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaEmp" size="25" maxlength="20" value="<%=Pagina.trocaNull(empresa.getSiglaEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">CNPJ/CPF</td>
		<td><input type="text" name="cnpjCpfEmp" size="19" maxlength="14" value="<%=Pagina.trocaNull(empresa.getCnpjCpfEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Inscrição Estadual</td>
		<td><input type="text" name="inscrEstadualEmp" size="20" maxlength="15" value="<%=Pagina.trocaNull(empresa.getInscrEstadualEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Inscrição Municipal</td>
		<td><input type="text" name="inscrMunicipalEmp" size="20" maxlength="15" value="<%=Pagina.trocaNull(empresa.getInscrMunicipalEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Endereço</td>
		<td><input type="text" name="enderecoEmp" size="55" maxlength="50" value="<%=Pagina.trocaNull(empresa.getEnderecoEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Complemento</td>
		<td><input type="text" name="complementoEmp" size="45" maxlength="40" value="<%=Pagina.trocaNull(empresa.getComplementoEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Bairro</td>
		<td><input type="text" name="bairroEmp" size="45" maxlength="40" value="<%=Pagina.trocaNull(empresa.getBairroEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Cidade</td>
		<td><input type="text" name="cidadeEmp" size="45" maxlength="40" value="<%=Pagina.trocaNull(empresa.getCidadeEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">CEP</td>
		<td><input type="text" name="cepEmp" size="13" maxlength="8" value="<%=Pagina.trocaNull(empresa.getCepEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">UF</td>
		<td>
		<%if ( empresa.getUf() != null ) {%>
			<combo:ComboTag 
					nome="uf"
					objeto="ecar.pojo.Uf"
					label="codUf"
					value="codUf"
					order="codUf"
					selected="<%=empresa.getUf().getCodUf()%>"
					scripts="<%=_disabled%>"
			/>		
		<%} else {%>
			<combo:ComboTag 
					nome="uf"
					objeto="ecar.pojo.Uf"
					label="codUf"
					value="codUf"
					order="codUf"
					scripts="<%=_disabled%>"
			/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label">Telefone 1</td>
		<td>
			<input type="text" name="ddd1Emp" size="5" maxlength="3" value="<%=Pagina.trocaNull(empresa.getDdd1Emp())%>" <%=_disabled%>>
			<input type="text" name="telefone1Emp" size="15" maxlength="10" value="<%=Pagina.trocaNull(empresa.getTelefone1Emp())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">Telefone 2</td>
		<td>
			<input type="text" name="ddd2Emp" size="5" maxlength="3" value="<%=Pagina.trocaNull(empresa.getDdd2Emp())%>" <%=_disabled%>>
			<input type="text" name="telefone2Emp" size="15" maxlength="10" value="<%=Pagina.trocaNull(empresa.getTelefone2Emp())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">Fax</td>
		<td>
			<input type="text" name="dddFaxEmp" size="5" maxlength="3" value="<%=Pagina.trocaNull(empresa.getDddFaxEmp())%>" <%=_disabled%>>
			<input type="text" name="faxEmp" size="15" maxlength="10" value="<%=Pagina.trocaNull(empresa.getFaxEmp())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> E-mail Contato</td>
		<td><input type="text" name="emailContatoEmp" size="55" maxlength="50" value="<%=Pagina.trocaNull(empresa.getEmailContatoEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> E-mail Erros</td>
		<td><input type="text" name="emailErrosEmp" size="55" maxlength="50" value="<%=Pagina.trocaNull(empresa.getEmailErrosEmp())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">URL Página da Empresa</td>
		<td><input type="text" name="homePageEmp" size="45" maxlength="40" value="<%=Pagina.trocaNull(empresa.getHomePageEmp())%>" <%=_disabled%>></td>
	</tr>
	
	<util:uploadArquivoTag 
			nomeCampo="logotipoEmp" 
			labelCampo="Logotipo"
			contextPath="<%=request.getContextPath()%>"
			pathRaiz="<%=pathRaiz%>"
			arquivo="<%=empresa.getLogotipoEmp()%>"
			desabilitado="<%=_disabled%>"
			obrigatorio="<%=_obrigatorio%>"
		/>
	
	
	<util:uploadArquivoTag 
			nomeCampo="logotipoEmailEmp" 
			labelCampo="Logotipo E-mail"
			contextPath="<%=request.getContextPath()%>"
			pathRaiz="<%=pathRaiz%>"
			arquivo="<%=empresa.getLogotipoEmailEmp()%>"
			desabilitado="<%=_disabled%>"
			obrigatorio="<%=_obrigatorio%>"
		/>
		
	
	<util:uploadArquivoTag 
			nomeCampo="logotipoRelatorioEmp" 
			labelCampo="Logotipo Relatório"
			contextPath="<%=request.getContextPath()%>"
			pathRaiz="<%=pathRaiz%>"
			arquivo="<%=empresa.getLogotipoRelatorioEmp()%>"
			desabilitado="<%=_disabled%>"
			obrigatorio="<%=_obrigatorio%>"
		/>
	
	
	
	
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>
	