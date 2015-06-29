<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<jsp:useBean id="objConsef" class="ecar.pojo.ConfigSisExecFinanCsef" scope="request"/>

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disbled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 String pathRaiz = configuracao.getRaizUpload(); 
 %>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" 
					name="nome" size="22" 
					maxlength="20" value="<%=Pagina.trocaNull(objConsef.getNomeCsef())%>" 
					<%=_disabled%> ></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" 
					name="sigla" size="8" 
					maxlength="6" value="<%=Pagina.trocaNull(objConsef.getSiglaCsef())%>" 
					<%=_disabled%> ></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Permite Informar Valor Manual</td>
			<%
				String simSelecionado = "";
				String naoSelecionado = "";
				
				if (objConsef.getIndPermiteValormanualorcCsef() != null && objConsef.getIndPermiteValormanualorcCsef().equals("N")) {
					naoSelecionado = "selected";
				}else if (objConsef.getIndPermiteValormanualorcCsef() != null && objConsef.getIndPermiteValormanualorcCsef().equals("S")) {
					simSelecionado = "selected";
				}else {
					if (Pagina.getParamStr(request, "pesquisar").equals("")) {
						simSelecionado = "selected";
					}
				}

			%>
		<td>

			<select name="valorManual" <%=_disabled%>>
				<option></option>
               <option value="N" <%=naoSelecionado%>>N&atilde;o</option>
               <option value="S" <%=simSelecionado%>>Sim</option>
			</select>
		</td>
	</tr>
	<tr>
	<%
		simSelecionado = "";
		naoSelecionado = "";	
		
		if (objConsef.getIndAtivoCsef() != null && objConsef.getIndAtivoCsef().equals("N")) {
			naoSelecionado = "selected";
		}else if (objConsef.getIndAtivoCsef() != null && objConsef.getIndAtivoCsef().equals("S")) {
			simSelecionado = "selected";
		}else {
			if (Pagina.getParamStr(request, "pesquisar").equals("")) {
				simSelecionado = "selected";
			}
		}

	%>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>

			<select name="ativo" <%=_disabled%>>
				<option></option>
               <option value="N" <%=naoSelecionado%>>N&atilde;o</option>
               <option value="S" <%=simSelecionado%>>Sim</option>
			</select>
		</td>
	</tr>	

	<tr><td class="label">&nbsp;</td></tr>


	<%@ include file="../../include/estadoMenu.jsp"%>
	