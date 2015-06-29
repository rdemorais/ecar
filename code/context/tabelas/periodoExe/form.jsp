
<jsp:directive.page import="ecar.dao.ExercicioDao"/>
<jsp:directive.page import="ecar.pojo.PeriodoExercicioPerExe"/><%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Descrição</td>
		<td><input type="text" name="nomePerExe" size="25" maxlength="100" value="<%=Pagina.trocaNull(perExe.getNomePerExe())%>" <%=_disabled%>></td>
	</tr>
	
	<tr><td class="label">&nbsp;</td></tr>
	
<%@ include file="../../include/estadoMenu.jsp"%>
	