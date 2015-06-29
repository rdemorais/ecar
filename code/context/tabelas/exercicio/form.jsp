<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

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
		<td><input type="text" name="descricaoExe" size="25" maxlength="20" value="<%=Pagina.trocaNull(exercicio.getDescricaoExe())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Data de Início</td>
		<td>
			<input type="text" name="dataInicialExe" size="15" maxlength="10" value="<%=Pagina.trocaNullData(exercicio.getDataInicialExe())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicialExe, '')">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Data de Término</td>
		<td>
			<input type="text" name="dataFinalExe" size="15" maxlength="10" value="<%=Pagina.trocaNullData(exercicio.getDataFinalExe())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFinalExe, '')">
			</c:if>
			
		</td>
	</tr>
	<tr>
		<td class="label">Per&iacute;odo</td>
		<td>
<%
		if (exercicio.getPeriodoExercicioPerExe() != null){
%>
			<combo:ComboTag 
						nome="periodoExercicioPerExe"
						objeto="ecar.pojo.PeriodoExercicioPerExe"
						label="nomePerExe"
						value="codPerExe"
						order="nomePerExe"
						selected="<%=exercicio.getPeriodoExercicioPerExe().getCodPerExe().toString()%>"
						scripts="<%=_disabled%>"
			/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="periodoExercicioPerExe"
					objeto="ecar.pojo.PeriodoExercicioPerExe"
					label="nomePerExe"
					value="codPerExe"
					order="nomePerExe"
					scripts="<%=_disabled%>"
			/>
<%
			}		
%>
		</td>
	</tr>
	
	<tr><td class="label">&nbsp;</td></tr>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
	