
<jsp:directive.page import="java.util.HashSet"/>
<jsp:directive.page import="java.util.Set"/><%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}
	
	
}
</script>

<table class="form">
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Estrutura</td>
	<td>
	<%
	String scripts = _disabled + " onchange=reload(document.forms[0]);";
	%>
	<combo:ComboTag 
						nome="estruturaEtt"
						objeto="ecar.pojo.EstruturaEtt" 
						label="nomeEtt,siglaEtt" 
						value="codEtt" 
						order="nomeEtt"
						scripts="<%=scripts%>"
						selected="<%=selectedEstrutura%>"
						colecao="<%=listaEstrutura%>"
	/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Fun&ccedil;&atilde;o de Acompanhamento</td>
	<td>
	<%
	scripts = _disabled + " onchange=reload(document.forms[0]);";
	//lFuncoesAcompanhamento =estruturaDao.getFuncoesAcompanhamentoById(estrutura);
	
	%>
	
	<combo:ComboTag 
						nome="tipoFuncAcompTpfa"
						objeto="ecar.pojo.TipoFuncAcompTpfa" 
						label="labelTpfa" 
						value="codTpfa" 
						order="labelTpfa"
						scripts="<%=scripts%>"
						selected="<%=selectedTipoFuncAcomp%>"
						colecao="<%=listaTipoFuncAcomp%>"
	/>
	</td>
</tr>

<!-- *************************** Permissão de Acesso na Estrutura ************************** -->
<tr>
	<td class="label">
		Permissão de Acesso na Estrutura
	</td>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td class="label">
		&nbsp;
	</td>
	<td>
		<table class="form">
			<tr>
				<td style="width:136px">
					Manter no próximo nível
				</td>
				<td>
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indManterProximoNivelEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indManterProximoNivelEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indManterProximoNivelEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indManterProximoNivelEtttfa" id="indManterProximoNivelEtttfa" value="<%=Pagina.SIM %>"
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIndManterProximoNivelEtttfa(), Pagina.SIM)%>
						<%= _disabled%>
						>
						
						<input type="hidden" name="indManterProximoNivelEtttfaAntigo" value='<%=estruturaTipoFuncAcomp.getIndManterProximoNivelEtttfa()%>'>
					<% 
					}
					%>			
			</tr>
		</table>
	</td>
</tr>

<!-- *************************** Tela de Cadastramento ************************** -->
<tr>
	<td class="label">Tela Cadastramento</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<table class="form">
			<tr>
				<td width="136px"> Pode ser bloqueado?</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="idPodeBloquearEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="idPodeBloquearEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(idPodeBloquearEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="idPodeBloquearEtttfa" id="idPodeBloquearEtttfa" value="<%=Pagina.SIM%>" 
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIdPodeBloquearEtttfa(), Pagina.SIM)%>
						<%= _disabled%>
						onchange="exibeEscondeListFuncaoAcomp('idPodeBloquearEtttfa','lstFuncAcomp');">
						
					<% 
					}
					%>
				
					 
				</td>
			</tr>
			<%  
				String displayFuncAcomp = "none";
				Set setTpfa = new HashSet();
				
				if (estruturaTipoFuncAcomp != null && Pagina.trocaNull(estruturaTipoFuncAcomp.getIdPodeBloquearEtttfa()).equals(Pagina.SIM)){
					displayFuncAcomp = "inline";
					setTpfa = estruturaTipoFuncAcomp.getLibTipoFuncAcompTpfas();
				} 
					
				 %>
			<tr>
				<td style="vertical-align: text-top;"> <span id="tdLiberado" style="display: <%=displayFuncAcomp %>; vertical-align:top;"> Est&aacute; liberado para</span> </td>
				<td > <table id="lstFuncAcomp" style="display: <%=displayFuncAcomp %> ;"><tr><td>
					<combo:CheckListTag 
						nome="limbTipoFuncAcompTpfa"
						objeto="ecar.pojo.TipoFuncAcompTpfa" 
						label="labelTpfa" 
						value="codTpfa" 
						order="labelTpfa"
						selected="<%=setTpfa %>"
						scripts = "<%= _disabled%>"
					/>
				</td></tr></table>
				</td>
			</tr>
			
			
			<tr>
				<td width="136px"><%=_obrigatorio%>Seqüência</td>
				<td>
					<input type="text" name="seqApresentTelaCampoEtttfa" size="6" maxlength="4" value="<%=Pagina.trocaNull(estruturaTipoFuncAcomp.getSeqApresentTelaCampoEtttfa())%>" <%=_disabled%>></td>
				</td>
			</tr>
		</table>
	</td>
</tr>

<!-- *************************** Lista do Cadastro de Itens ************************** -->
<tr>
	<td class="label">Lista do Cadastro de Itens</td>
	<td></td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<table class="form">
			<tr>
				<td width="136px"> Exibir na Lista</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indListagemTelaEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indListagemTelaEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemTelaEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indListagemTelaEtttfa" value="<%=Pagina.SIM%>" 
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIndListagemTelaEtttfa(), Pagina.SIM)%> 
						<%=_disabled%>>
					<% 
					}
					%>
				
					
				</td>
			</tr>

			<tr>
				<td width="136px"> É Filtro?</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="idFiltroEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="idFiltroEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(idFiltroEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="idFiltroEtttfa" value="<%=Pagina.SIM%>" 
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIdFiltroEtttfa(), Pagina.SIM)%> 
						<%=_disabled%>>
					<% 
					}
					%>
				
					
				</td>
			</tr>

			<tr>
				<td width="136px">Seqüência</td>
				<td>
					<input type="text" name="seqApresListagemTelaEtttfa" size="6" maxlength="4" value="<%=Pagina.trocaNull(estruturaTipoFuncAcomp.getSeqApresListagemTelaEtttfa())%>" <%=_disabled%>></td>
				</td>
			</tr>
			<tr>
				<td width="136px">Largura</td>
				<td>
					<input type="text" name="larguraListagemTelaEtttfa" size="6" maxlength="4" value="<%=Pagina.trocaNull(estruturaTipoFuncAcomp.getLarguraListagemTelaEtttfa())%>" <%=_disabled%>></td>
				</td>
			</tr>
		</table>
	</td>
</tr>

<!-- *************************** Impressão do Cadastro de Itens ************************** -->
<tr>
	<td class="label">Impressão do Cadastro de Itens</td>
	<td></td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<table class="form">
			<tr>
				<td width="136px">Completa</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indListagemImprCompEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indListagemImprCompEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImprCompEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indListagemImprCompEtttfa" value="<%=Pagina.SIM%>" 
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIndListagemImprCompEtttfa(), Pagina.SIM)%> 
						<%=_disabled%>> 
					<% 
					}
					%>
				
									
				</td>
			</tr>
			<tr>
				<td width="136px">Resumo</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indListagemImprResEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indListagemImprResEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImprResEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indListagemImprResEtttfa" value="<%=Pagina.SIM%>" 
						<%=Pagina.isChecked(estruturaTipoFuncAcomp.getIndListagemImprResEtttfa(), Pagina.SIM)%> 
						<%=_disabled%>>
					<% 
					}
					%>
				
					 
				</td>
			</tr>
			<tr>
				<td width="136px">Revisão</td>
				<td>
				<%
					String checkedRevisaoSim = Pagina.isChecked(estruturaTipoFuncAcomp.getIndRevisao(), Pagina.SIM);
				%>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indRevisao" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indRevisao" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indRevisao)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indRevisao" value="<%=Pagina.SIM%>" 
						<%=checkedRevisaoSim%> 
						<%=_disabled%> 
						onclick="javascript:form.indPermiteRevisaoEtttfa.checked = this.checked;">
					<% 
					}
					%>
				
					 
				</td>
			</tr>
		</table>
	</td>
</tr>
<!-- *************************** Aba Revisão ************************** -->
<tr>
	<td class="label">Aba Revis&atilde;o</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<table class="form">
			<tr>
				<td width="136px">Permite Revis&atilde;o</td>
				<td>
				
					<%
					if (ehPesquisa) {
					%>
									
						<input type="radio" class="form_check_radio" name="indPermiteRevisaoEtttfa" value="<%=Pagina.SIM%>">				
								Sim &nbsp;
						<input type="radio" class="form_check_radio" name="indPermiteRevisaoEtttfa" value="<%=Pagina.NAO%>">					
								Não &nbsp;
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPermiteRevisaoEtttfa)">
						<br>
						
					<%
					} else {
					%>
						<input type="checkbox" class="form_check_radio" name="indPermiteRevisaoEtttfa" value="<%=Pagina.SIM%>" <%=checkedRevisaoSim%> disabled>
					<% 
					}
					%>				
					 
				</td>
			</tr>
		</table>
	</td>
</tr>

<tr>
	<td class="label">&nbsp;</td>
</tr>
</table>
<%@ include file="/include/estadoMenu.jsp"%>