<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="nomeCef" size="25" maxlength="20" value="<%=Pagina.trocaNull(configExecFinan.getNomeCef())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Label para utilizar nas telas</td>
		<td><input type="text" name="labelCef" size="25" maxlength="20" value="<%=Pagina.trocaNull(configExecFinan.getLabelCef())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> N&uacute;mero de Posi&ccedil;&otilde;es</td>
		<td><input type="text" name="numCaracteresCef" size="10" maxlength="4" value="<%=Pagina.trocaNull(configExecFinan.getNumCaracteresCef())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Tipo de Dado</td>
		<td>
		<%if ( configExecFinan.getConfigTipoDadoCtd() != null ) {%>
				<combo:ComboTag 
						nome="configTipoDadoCtd"
						objeto="ecar.pojo.ConfigTipoDadoCtd" 
						label="nomeCtd" 
						value="codCtd" 
						order="nomeCtd"
						selected="<%=configExecFinan.getConfigTipoDadoCtd().getCodCtd().toString()%>"
						scripts="<%=_disabled%>"
				/>		
		<%} else {%>
				<combo:ComboTag 
						nome="configTipoDadoCtd"
						objeto="ecar.pojo.ConfigTipoDadoCtd" 
						label="nomeCtd" 
						value="codCtd" 
						order="nomeCtd"
						scripts="<%=_disabled%>"
				/>		
		<%}%>
		</td>
	</tr>
	<tr>
	
		<%
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(request);
		List versoes = versaoDao.getCsefvOrderBySistema();
		if(versoes == null)
			versoes = new ArrayList();
			
		%>
	
		<td class="label"><%=_obrigatorio%> Sistema/Versão</td>
		<td>
		<%if (configExecFinan.getConfigSisExecFinanCsefv() != null){%>
			<combo:ComboTag 
					nome="configSisExecFinanCsefv"
					colecao="<%=versoes%>"
					objeto="ecar.pojo.ConfigSisExecFinanCsefv" 
					label="sistemaVersaoCsefv" 
					value="codCsefv" 
					order="codCsefv"
					selected="<%=configExecFinan.getConfigSisExecFinanCsefv().getCodCsefv().toString()%>"
					scripts="<%=_disabled%>"
			/>		
		<%}	else {%>
			<combo:ComboTag 
					nome="configSisExecFinanCsefv"
					colecao="<%=versoes%>"
					objeto="ecar.pojo.ConfigSisExecFinanCsefv" 
					label="sistemaVersaoCsefv" 
					value="codCsefv" 
					order="codCsefv"
					scripts="<%=_disabled%>"
			/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Seq&uuml;&ecirc;ncia de Apresenta&ccedil;&atilde;o</td>
		<td><input type="text" name="seqApresentacaoCef" size="10" maxlength="4" value="<%=Pagina.trocaNull(configExecFinan.getSeqApresentacaoCef())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">ID_XML</td>
		<td><input type="text" name="idXmlCef" size="25" maxlength="20" value="<%=Pagina.trocaNull(configExecFinan.getIdXmlCef())%>" <%=_disabled%>></td>
	</tr>
	
	<% /*  controla o estado do menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp" %>