
<%@page import="ecar.dao.VisaoGrpAcessoDao"%>
<%@page import="ecar.dao.SisAtributoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ecar.dao.SituacaoDao"%>
<%@page import="ecar.pojo.SituacaoSit"%>
<%@page import="ecar.pojo.SitDemandaSitd"%><jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/>
<jsp:directive.page import="ecar.dao.VisaoDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="comum.util.Pagina"/>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function limparRadioButtons(radioObj) {
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}
	
	
}

function copiarValorHidden(checkBox, campo){
	if (checkBox.checked){
		document.getElementById(campo).value = 'S';
	} else {
		document.getElementById(campo).value = 'N';
	}
}

function atualizarSituacao(checkBoxF, checkBoxA, campo){
	document.getElementById(checkBoxA).checked = checkBoxF.checked;
	document.getElementById(checkBoxA).disabled = checkBoxF.checked;
	copiarValorHidden(checkBoxF, campo);
}

function validaEvento(event){
	if(event.keyCode == 13){
		return false;
	} else{
		return true;
	}
}

</script>

<table class="form">


<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td>
		<input type="text" name="descricaoVisao" size="100" maxlength="500" value="<%=Pagina.trocaNull(visaoDemandas.getDescricaoVisao())%>" <%=_disabled%> onkeydown="return validaEvento(event);">	
	</td>
</tr>
<tr>
	<td class="label">
		Situações
	</td>
	<td>
		<% 
					SituacaoDao situacaoDao = new SituacaoDao(request);				
					
					List todasSituacoes = situacaoDao.listar(SitDemandaSitd.class, new String[]{"descricaoSitd", "asc"});
					
					List situacoesVisao = null;
					if (visaoDemandas.getVisaoSituacaoDemandas()!=null)
						 situacoesVisao = new ArrayList(visaoDemandas.getVisaoSituacaoDemandas());
					
					
					request.setAttribute("todasSituacoes", todasSituacoes);
					request.setAttribute("situacoesVisao", situacoesVisao);
				%>
				<table class="form">
				<tr>
				<td align="center"><b></b></td>
				<td align="center"><b>Filtrar Demandas</b></td>
				<td align="center"><b>Manter Demandas</b></td>
				</tr>
				<c:set var="iguais" value="false" scope="request"/>
				<c:forEach var="todasSituacoes" items="${todasSituacoes}">
				<tr>
				   <c:if test="${situacoesVisao !=null}">
						<c:forEach var="situacoesVisao" items="${situacoesVisao}">
							<c:if test="${situacoesVisao.sitDemandaSitd.codSitd == todasSituacoes.codSitd}">
							   <td>
							   <c:out value ="${situacoesVisao.sitDemandaSitd.descricaoSitd}"/>
							   </td>
							   <td align="center">
							   <c:if test="${situacoesVisao.indConsultar}">
							   <input type="checkbox" class="form_check_radio"  name="f${situacoesVisao.sitDemandaSitd.codSitd}" value="<%=Pagina.SIM%>" checked="true" <%=_disabled%> onchange="return atualizarSituacao(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao', 'a${situacoesVisao.sitDemandaSitd.codSitd}');" onclick="return atualizarSituacao(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao', 'a${situacoesVisao.sitDemandaSitd.codSitd}');">
							   </c:if>
							   <c:if test="${!situacoesVisao.indConsultar}">
							   <input type="checkbox" class="form_check_radio"  name="f${situacoesVisao.sitDemandaSitd.codSitd}" value="<%=Pagina.SIM%>"  <%=_disabled%> onchange="return atualizarSituacao(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao', 'a${situacoesVisao.sitDemandaSitd.codSitd}');" onclick="return atualizarSituacao(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao', 'a${situacoesVisao.sitDemandaSitd.codSitd}');">
							   </c:if>
							   </td>
							   <td align="center">
							   <c:if test="${(situacoesVisao.indAlterar) && (situacoesVisao.indConsultar)}">
							   <input type="checkbox" class="form_check_radio" id="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" name="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" value="<%=Pagina.SIM%>"  checked="true" disabled onchange="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');" onclick="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');">
							   <input type="hidden" id="a${situacoesVisao.sitDemandaSitd.codSitd}" name="a${situacoesVisao.sitDemandaSitd.codSitd}" value="S"/>
							   </c:if>
							   <c:if test="${(situacoesVisao.indAlterar) && (!situacoesVisao.indConsultar)}">
							   <input type="checkbox" class="form_check_radio" id="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" name="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" value="<%=Pagina.SIM%>" <%=_disabled%> checked="true" onchange="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');" onclick="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');">
							   <input type="hidden" id="a${situacoesVisao.sitDemandaSitd.codSitd}" name="a${situacoesVisao.sitDemandaSitd.codSitd}" value="S"/>
							   </c:if>
							   <c:if test="${!situacoesVisao.indAlterar}">
							   <input type="checkbox" class="form_check_radio" id="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" name="a${situacoesVisao.sitDemandaSitd.codSitd}_exibicao" value="<%=Pagina.SIM%>" <%=_disabled%> onchange="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');" onclick="return copiarValorHidden(this, 'a${situacoesVisao.sitDemandaSitd.codSitd}');">
							   <input type="hidden" id="a${situacoesVisao.sitDemandaSitd.codSitd}"  name="a${situacoesVisao.sitDemandaSitd.codSitd}" value="N"/>
							   </c:if>
							   </td>
							   <c:set var="iguais" value="true" scope="request"/>
							</c:if> 		
					 	</c:forEach>
					 	<c:if test="${iguais == false}">
					 	    <td>
					 	    <c:out value ="${todasSituacoes.descricaoSitd}"/>
					 	    </td>
					 	    <td align="center">
							<input type="checkbox" class="form_check_radio"  name="f${todasSituacoes.codSitd}" value="<%=Pagina.SIM%>" <%=_disabled%> onchange="return atualizarSituacao(this, 'a${todasSituacoes.codSitd}_exibicao', 'a${todasSituacoes.codSitd}');" onclick="return atualizarSituacao(this, 'a${todasSituacoes.codSitd}_exibicao', 'a${todasSituacoes.codSitd}');">
							</td>
							<td align="center">
							<input type="checkbox" class="form_check_radio" id="a${todasSituacoes.codSitd}_exibicao" name="a${todasSituacoes.codSitd}_exibicao" value="<%=Pagina.SIM%>" <%=_disabled%> onchange="return copiarValorHidden(this, 'a${todasSituacoes.codSitd}');" onclick="return copiarValorHidden(this, 'a${todasSituacoes.codSitd}');">
							<input type="hidden" id="a${todasSituacoes.codSitd}" name="a${todasSituacoes.codSitd}" value="N"/>
							</td>
						</c:if> 
						<c:set var="iguais" value="false" scope="request"/>
				   </c:if> 		
				   <c:if test="${situacoesVisao == null}">
				    	   <td>
				    	   <c:out value ="${todasSituacoes.descricaoSitd}"/>
				    	   </td>
				    	   <td align="center">
						   <input type="checkbox" class="form_check_radio"  name="f${todasSituacoes.codSitd}" value="<%=Pagina.SIM%>" <%=_disabled%> onchange="return atualizarSituacao(this, 'a${todasSituacoes.codSitd}_exibicao', 'a${todasSituacoes.codSitd}');" onclick="return atualizarSituacao(this, 'a${todasSituacoes.codSitd}_exibicao', 'a${todasSituacoes.codSitd}');">
						   </td>
						   <td align="center">
						   <input type="checkbox" class="form_check_radio" id="a${todasSituacoes.codSitd}_exibicao" name="a${todasSituacoes.codSitd}_exibicao" value="<%=Pagina.SIM%>" <%=_disabled%> onchange="return copiarValorHidden(this, 'a${todasSituacoes.codSitd}');" onclick="return copiarValorHidden(this, 'a${todasSituacoes.codSitd}');">
						   <input type="hidden" id="a${todasSituacoes.codSitd}" name="a${todasSituacoes.codSitd}" value="N"/>
						   </td>
				  	</c:if>
				<tr> 		
				</c:forEach>
				</table>
<%	
	request.removeAttribute("todasSituacoes");
	request.removeAttribute("situacoesVisao");				
%>			
	</td>
</tr>
<tr>
	<td class="label">Visualizar Demandas Incluídas pelo Usuário</td>
	<td>
	 <input type="checkbox" name="visualizarDemandasIncluidasUsuario" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getVisualizarDemandasIncluidasUsuario(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
	</td>
</tr>
<tr>
	<td class="label">Utilizar Regra de Entidade Solucionadora</td>
	<td>
	 <input type="checkbox" name="utilizarRegraEntidadeSolucionadora" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getUtilizarRegraEntidadeSolucionadora(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
	</td>
</tr>
<!-- *************************** Lista do Cadastro de Itens ************************** -->
<tr>
	<td class="label">
		Permissões
	</td>
	<td>
		<% 
					VisaoDao visaoDao = new VisaoDao(request);
					SisAtributoDao sisAtributoDao = new SisAtributoDao();				
					
					List gruposAcesso = sisAtributoDao.getListaAcesso();
					
					List visoesGrupoAcesso = null;
					if (visaoDemandas.getVisoesGrpAcesso()!=null)
						 visoesGrupoAcesso = new ArrayList(visaoDemandas.getVisoesGrpAcesso());
					
					
					request.setAttribute("todosGruposAcesso", gruposAcesso);
					request.setAttribute("visoesGrupoAcesso", visoesGrupoAcesso);
					
					String enabledParaConsulta = "disabled";
					
					if (ehPesquisa) {
						enabledParaConsulta = "";
					}
					
				%>
				<c:set var="iguais" value="false" scope="request"/>
				<c:forEach var="grpAcessoTodos" items="${todosGruposAcesso}">
				   <c:if test="${visoesGrupoAcesso !=null}">
						<c:forEach var="visaoGrupoAcesso" items="${visoesGrupoAcesso}">
							<c:if test="${visaoGrupoAcesso.visaoDemandasGrpAcessoPk.sisAtributo.codSatb == grpAcessoTodos.codSatb}">
							   <input type="checkbox" class="form_check_radio"  name="visoes" value="${visaoGrupoAcesso.visaoDemandasGrpAcessoPk.sisAtributo.codSatb}" checked="true" <%=enabledParaConsulta%> onkeydown="return validaEvento(event);">
							   <c:out value ="${visaoGrupoAcesso.visaoDemandasGrpAcessoPk.sisAtributo.descricaoSatb}"/>
							   <c:set var="iguais" value="true" scope="request"/>
							   <br>
							</c:if> 		
					 	</c:forEach>
					 	<c:if test="${iguais == false}">
							<input type="checkbox" class="form_check_radio"  name="visoes" value="${grpAcessoTodos.codSatb}" <%=enabledParaConsulta%> onkeydown="return validaEvento(event);">
							<c:out value ="${grpAcessoTodos.descricaoSatb}"/>
							<br>
						</c:if> 
						<c:set var="iguais" value="false" scope="request"/>
				   </c:if> 		
				    <c:if test="${visoesGrupoAcesso == null}">
						   <input type="checkbox" class="form_check_radio"  name="visoes" value="${grpAcessoTodos.codSatb}" <%=enabledParaConsulta%> onkeydown="return validaEvento(event);">
						   <c:out value ="${grpAcessoTodos.descricaoSatb}"/>
						   <br>
				 	</c:if> 		
				</c:forEach>
<%	
	request.removeAttribute("todosGruposAcesso");
	request.removeAttribute("visoesGrupoAcesso");				

%>			
	</td>
</tr>
<tr>
	<td class="label" colspan="3" >
		<table class="form">
			<tr>
				<td>
					&nbsp;
				</td>
				<td class="label">
					Demanda
				</td>
				<td> 
				  <table class="form">	
						<tr>
							<td class="label">Adicionar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoIncluirDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoIncluirDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Alterar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoAlterarDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoAlterarDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Excluir?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoExcluirDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoExcluirDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
				   </table>
				</td>	
			</tr>
		</table>
		
	</td>
</tr>		
<tr>
	<td class="label" colspan="3" >
		<table class="form">
			<tr>
				<td>
					&nbsp;
				</td>
				<td class="label">
					Encaminhamento
				</td>
				<td> 
				  <table class="form">	
						<tr>
							<td class="label">Adicionar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoIncluirApontamento" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoIncluirApontamento(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Alterar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoAlterarApontamento" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoAlterarApontamento(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Excluir?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoExcluirApontamento" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoExcluirApontamento(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
				   </table>
				</td>	
			</tr>
		</table>
		
	</td>
</tr>		
<tr>
	<td class="label" colspan="3" >
		<table class="form">
			<tr>
				<td>
					&nbsp;
				</td>
				<td class="label">
					Anexo Encaminhamento
				</td>
				<td> 
				  <table class="form">	
						<tr>
							<td class="label">Adicionar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoIncluirAnexoApontamento" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoIncluirAnexoApontamento(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<!-- Botão alterar removido. Replicação de alteração feita na branch da versão 8.4.2 solicitada por Fred  -->
						<tr>
							<td class="label">Excluir?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoExcluirAnexoApontamento" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoExcluirAnexoApontamento(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
				   </table>
				</td>	
			</tr>
		</table>
		
	</td>
</tr>		
<tr>
	<td class="label" colspan="3" >
		<table class="form">
			<tr>
				<td>
					&nbsp;
				</td>
				<td class="label">
					Anexo Demanda
				</td>
				<td> 
				  <table class="form">	
						<tr>
							<td class="label">Adicionar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoIncluirAnexoDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoIncluirAnexoDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Alterar?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoAlterarAnexoDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoAlterarAnexoDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);">
							</td>
						</tr>
						<tr>
							<td class="label">Excluir?</td>
							<td>
				 				<input type="checkbox" name="ehPermitidoExcluirAnexoDemanda" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoDemandas.getEhPermitidoExcluirAnexoDemanda(), Pagina.SIM)%> <%=_disabled%> onkeydown="return validaEvento(event);"> 
							</td>
						</tr>
				   </table>
				</td>	
			</tr>
		</table>
		
	</td>
</tr>

<%@ include file="/include/estadoMenu.jsp"%>
</table>