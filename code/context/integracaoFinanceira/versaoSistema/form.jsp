<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<jsp:useBean id="objConsefv" class="ecar.pojo.ConfigSisExecFinanCsefv" scope="request"/>

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disbled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 ConfiguracaoCfg conf = (ConfiguracaoCfg)session.getAttribute("configuracao");
 String pathRaiz = conf.getRaizUpload(); 
 %>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Sistema</td>
		<td>
			<%
				String valorSelecionado = (objConsefv.getConfigSisExecFinanCsef() != null? objConsefv.getConfigSisExecFinanCsef().getCodCsef().toString(): "");
			%>
			<combo:ComboTag 
				nome="configSisExecFinanCsef"
				objeto="ecar.pojo.ConfigSisExecFinanCsef" 
				label="nomeCsef" 
				value="codCsef" 
				order="nomeCsef"
				scripts="<%=_disabled%>"
				selected="<%=valorSelecionado%>"
			/>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Vers&atilde;o</td>
		<td><input type="text" 
					name="versaoCsefv" size="9" 
					maxlength="4" value="<%=Pagina.trocaNull(objConsefv.getVersaoCsefv())%>" 
					onkeypress="javascript:return(digitaNumero(this, event));" <%=_disabled%> ></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> M&ecirc;s/Ano</td>
		<td><input type="text" name="mesVersaoCsefv" 
				size="9" maxlength="7" 
				value="<%=Pagina.getParamStr(request, "mesAno")%>" <%=_disabled%> 
				onkeypress="javascript:mascaraMesAno(event, this)"></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<%
				String simSelecionado = "";
				String naoSelecionado = "";
				
				if (objConsefv.getIndAtivoCsefv() != null && objConsefv.getIndAtivoCsefv().equals("N")) {
					naoSelecionado = "selected";
				}else if (objConsefv.getIndAtivoCsefv() != null && objConsefv.getIndAtivoCsefv().equals("S")) {
					simSelecionado = "selected";
				}else {
					if (Pagina.getParamStr(request, "pesquisar").equals("")) {
						simSelecionado = "selected";
					}
				}
			
			//Regra para habilitar apenas o campo "Ativo" caso a alteração possua dependencias.
			String alteracaoParcial = (String)request.getAttribute("alteracaoParcial");
			if (alteracaoParcial != null && alteracaoParcial.equals("true")) {
				_disabled = "";
			}
			%>
			<select name="ativo" <%=_disabled%>>
				<option></option>
               <option value="N" <%=naoSelecionado%> >N&atilde;o</option>
               <option value="S" <%=simSelecionado%> >Sim</option>
			</select>
		</td>
	</tr>

	<tr><td class="label">&nbsp;</td></tr>


	<%@ include file="../../include/estadoMenu.jsp"%>
	