<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label"><%=_obrigatorio%> Descrição</td>
		<td><input type="text" name="descricaoPfl" size="35" maxlength="30" value="<%=Pagina.trocaNull(perfil.getDescricaoPfl())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Nível</td>
		<td>
			<select name="nivelPfl" <%=_disabled%>>
				<option></option>
<%
				int cont = 1;
				String selected = new String();
				selected = "";
				
				while (cont <= 5){
					if (perfil.getNivelPfl() != null){
						if (perfil.getNivelPfl().intValue() == cont)
							selected = "selected";
					}
%>
					<option <%=selected%> value="<%=cont%>"><%=cont%></option>
<%
					cont++;
					selected = "";
				}
%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Área Reservada</td>
		<td>
			<input type="radio" class="form_check_radio" name="indAreaReservadaPfl" value="S" <%=Pagina.isChecked(perfil.getIndAreaReservadaPfl(),"S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indAreaReservadaPfl" value="N" <%=Pagina.isChecked(perfil.getIndAreaReservadaPfl(),"N")%> <%=_disabled%>> Não
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>