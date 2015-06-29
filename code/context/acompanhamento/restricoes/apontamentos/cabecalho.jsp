<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>

<%

	configuracao = new ConfiguracaoDao(request).getConfiguracao();
	SisGrupoAtributoSga grupoAssunto = configuracao.getSisGrupoAtributoSgaTipoPontoCritico();
	
%>


<div id="linkstop">
<a href="javascript:mudaTela(<%=pontoCritico.getCodPtc()%>)"><%=estruturaFuncao.getLabelEttf()%></a>
<c:if test="<%=estruturaFuncaoApontamento != null && estruturaFuncaoApontamento.getLabelEttf() != null%>">
	&nbsp;|&nbsp;<b><%=estruturaFuncaoApontamento.getLabelEttf()%></b>				
</c:if>
</div>

<table class="form">
		<tr> 
			<td width="15%"><b><%=estruturaFuncao.getLabelEttf()%>:<b></td>
			<%
			if (grupoAssunto != null && pontoCritico.getSisAtributoTipo() != null){
			%>
				<td width="15%"><%=pontoCritico.getSisAtributoTipo().getDescricaoSatb()%></td>
			<%
			}
			%>
			<td><%=pontoCritico.getDescricaoPtc()%></td>
		</tr>			
</table>
