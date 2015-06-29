<%@page import="ecar.util.ConfiguracaoResponseBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<style>
<!--
label, td, th, input, select {
	font-family: sans-serif;
	font-size: 11px;
}
-->
</style>

<script>
function alterarConfiguracao() {
	document.forms[0].hdnInAtualizacao.value = 'S';
	selecionarTodosItensSelect(document.forms[0].lstHeaders);
	//alert(document.forms[0].hdnInAtualizacao.value);
	document.forms[0].submit();
}

function getIndiceSelecionado(pSelect) {
	return pSelect.selectedIndex;
}

/**
 * Função utilitária para habilitar um campo do formulário
 */
function adicionarItemSelect(pSelect, pValor, pTexto) {
	pSelect.options[pSelect.options.length] = new Option(pTexto, pValor);
}

/**
 * Função utilitária para habilitar um campo do formulário
 */
function removerItemSelecionadoSelect(pSelect) {
	indiceSelecionado = getIndiceSelecionado(pSelect);

	if (indiceSelecionado != -1) {
		pSelect[indiceSelecionado] = null;
	}
}

/**
 * Função utilitária para remover todas as opções de um select
 */
function removerTodosItensSelect(pSelect) {
	pSelect.length = 0;
}

function selecionarTodosItensSelect(pSelect) {
	for(i = 0; i < pSelect.options.length; i ++) {
		pSelect.options[i].selected = true;
	}
}

function existeItemSelect(pSelect, pValor) {
	for(i = 0; i < pSelect.options.length; i ++) {
		if (pSelect.options[i].value == pValor) {
			return true;
		}
	}
}

</script>

<%
	ConfiguracaoResponseBean configuracaoResponseBean = (ConfiguracaoResponseBean) session.getAttribute(ConfiguracaoResponseBean.class.getName());
	
	// verifica se o hashmap de configuracao do response está na sessão
	if (configuracaoResponseBean == null) {
		configuracaoResponseBean = new ConfiguracaoResponseBean();
		application.setAttribute(ConfiguracaoResponseBean.class.getName(), configuracaoResponseBean);
	}
	
	// recupera os parametros para atualização do bean
	String pInAtualizacao = request.getParameter("pInAtualizacao");
	
	//System.out.println("pInAtualizacao - " + pInAtualizacao);

	String pInUtilizarConfiguracaoResponse = request.getParameter("pInUtilizarConfiguracaoResponse");
	String pInSetarContentLength = request.getParameter("pInSetarContentLength");
	String pContentType = request.getParameter("pContentType");
	String[] pHeaders = request.getParameterValues("pHeaders");
	
	// verifica se está sendo executada uma atualização do bean
	if (pInAtualizacao != null && pInAtualizacao.equals("S")) {
		configuracaoResponseBean = new ConfiguracaoResponseBean();
		configuracaoResponseBean.setInUtilizarConfiguracaoResponse(pInUtilizarConfiguracaoResponse != null && pInUtilizarConfiguracaoResponse.equals("S"));
		configuracaoResponseBean.setInSetarContentLength(pInSetarContentLength != null && pInSetarContentLength.equals("S"));
		
		if (pContentType != null && !pContentType.equals("")) {
	configuracaoResponseBean.setContentType(pContentType);
		}
		
		//System.out.println(pHeaders);
		
		// codigo prodreira para preencher os headers
		if (pHeaders != null && pHeaders.length > 0) {
	for (int i = 0; i < pHeaders.length; i ++) {
		String chave = pHeaders[i].split("<@>")[0];
		String valor = pHeaders[i].split("<@>")[1];
		configuracaoResponseBean.getHeaders().put(chave, valor);
	}
		}
		
		application.setAttribute(ConfiguracaoResponseBean.class.getName(), configuracaoResponseBean);
	}

	//System.out.println(configuracaoResponseBean);
%>


<form action="configuracao.jsp" method="post">

<input type="hidden" name="pInAtualizacao" id="hdnInAtualizacao">

<div>
<h2>Configuração Response</h2>
<label>Utilizar Configuração Response</label> <input type="checkbox" name="pInUtilizarConfiguracaoResponse" value="S" <%=(configuracaoResponseBean.getInUtilizarConfiguracaoResponse() ? "checked" : "")%>> <br>
<label>Setar content length</label> <input type="checkbox" name="pInSetarContentLength" value="S" <%=(configuracaoResponseBean.getInSetarContentLength() ? "checked" : "")%>> <br>
<label>Content Type</label> <input type="text" name="pContentType" value="<%=configuracaoResponseBean.getContentType()%>"> <br>
</div>


<div>
<table>
	<tr>
		<th colspan="2">Headers</th>
	</tr>
	<tr>
		<td colspan="2">
			<select multiple="multiple" name="pHeaders" id="lstHeaders">
			<%
				HashMap hmHeaders = configuracaoResponseBean.getHeaders();
				Iterator iterator = hmHeaders.keySet().iterator();
				
				while (iterator.hasNext()) {
					String chave = (String) iterator.next();
					String valor = (String) hmHeaders.get(chave);
					
			%>
				<option value="<%=chave%><@><%=valor%>"><%=chave%> - <%=valor%></option>
			<%
				}
			%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" value="Adicionar" onclick="adicionarItemSelect(document.forms[0].lstHeaders, document.forms[0].idTxtChave.value + '<@>' + document.forms[0].idTxtValor.value, document.forms[0].idTxtChave.value + ' - ' + document.forms[0].idTxtValor.value)">
			<input type="button" value="Remover" onclick="removerItemSelecionadoSelect(document.forms[0].lstHeaders)">
			<input type="button" value="Remover Todos" onclick="removerTodosItensSelect(document.forms[0].lstHeaders)">
		</td>
	</tr>
	<tr>
		<td>Header:</td>
		<td><input type="text" id="idTxtChave"></td>
	</tr>
	<tr>
		<td>Valor:</td>
		<td><input type="text" id="idTxtValor"></td>
	</tr>
</table>

</div>

<div>
	<input type="button" value="Alterar Configuração" onclick="alterarConfiguracao()">
</div>
</form>