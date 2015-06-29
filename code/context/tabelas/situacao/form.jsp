<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%
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

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td width="20%" class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><input type="text" name="descricaoSit" <%=_disabled%> size="50" maxlength="50" value="<%=Pagina.trocaNull(situacao.getDescricaoSit())%>"></td>
	</tr>
	<tr>
		<td width="20%" class="label">Coment&aacute;rios</td>
		<td><textarea name="comentarioSit" <%=_readOnly%> cols="60" rows="4" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);">
			<%=Pagina.trocaNull(situacao.getComentarioSit())%></textarea></td>
	</tr>
	<tr>
		<td width="20%" valign="top" class="label">Dispon&iacute;vel para <br>Fun&ccedil;&atilde;o de Acompanhamento</td>		
		<%
		List lFuncoes = new ArrayList();
			
		lFuncoes = situacaoDao.getFuncoesAcompanhamentoById(situacao);
		%>
		<td>
				<combo:CheckListTag 
						nome="tipoFuncAcompTpfa"
						objeto="ecar.pojo.TipoFuncAcompTpfa" 
						label="labelTpfa" 
						value="codTpfa" 
						selected="<%=lFuncoes%>"
						order="labelTpfa"
						scripts="<%=_disabled%>"
				/>		
		</td>
	</tr>
	<tr>
		<td width="20%" valign="top" class="label"><%=_obrigatorio%> Representa Conclus&atilde;o?</td>
		<td>

				<input type="radio" class="form_check_radio" name="indConcluidoSit" value="S" 
					<%=Pagina.isChecked(situacao.getIndConcluidoSit(), "S")%> 
					<%=_disabled%>>Sim 
				<input type="radio" class="form_check_radio" name="indConcluidoSit" value="N" 
					<%=Pagina.isChecked(situacao.getIndConcluidoSit(), "N")%> 
					<%=_disabled%>>Não
					
				<%
				 	if (ehPesquisa){
				%>
				 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indConcluidoSit)">
					<br>
				<%
				 	}
				%>
			</td>
		</tr>
	<tr>
		<td width="20%" class="label"><%=_obrigatorio%> Exige Coment&aacute;rios em <br>Acompanhamento F&iacute;sico?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indComentarioSit" value="S" 
				<%=Pagina.isChecked(situacao.getIndComentarioSit(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indComentarioSit" value="N" 
 				 <%=Pagina.isChecked(situacao.getIndComentarioSit(), "N")%>
			 <%=_disabled%>>Não
			 
			<%
				if (ehPesquisa){
			%>
		 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indComentarioSit)">
			<br>
			<%
		 		}
			%>
		</td>
	</tr>
	<tr>
		<td width="20%" valign="top" class="label"><%=_obrigatorio%> Disponível em Metas Físicas?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indMetaFisicaSit" value="S" 
				<%=Pagina.isChecked(situacao.getIndMetaFisicaSit(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indMetaFisicaSit" value="N" 
				<%=Pagina.isChecked(situacao.getIndMetaFisicaSit(), "N")%> 
				<%=_disabled%>>Não
				
			<%
				if (ehPesquisa){
			%>
		 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indMetaFisicaSit)">
			<br>
			<%
		 		}
			%>
		</td>
	</tr>	 
	<tr>
		<td width="20%" valign="top" class="label"><%=_obrigatorio%> Sem Acompanhamento?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indSemAcompanhamentoSit" value="S" 
				<%=Pagina.isChecked(situacao.getIndSemAcompanhamentoSit(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indSemAcompanhamentoSit" value="N" 
				<%=Pagina.isChecked(situacao.getIndSemAcompanhamentoSit(), "N")%> 
				<%=_disabled%>>Não
				
			<%
				if (ehPesquisa){
			%>
		 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indSemAcompanhamentoSit)">
			<br>
			<%
		 		}
			%>
			
		</td>
	</tr>	 
	<tr>
		<td width="20%" valign="top" class="label">Dispon&iacute;vel para <br>o n&iacute;vel de estrutura</td>		
		<td>
		<%		
		List lEstrutura = new ArrayList();
		lEstrutura = situacaoDao.getEstruturasById(situacao);
		%>		
				<combo:CheckListTag 
						nome="estruturaEtt"
						objeto="ecar.pojo.EstruturaEtt" 
						label="nomeEtt,siglaEtt" 
						value="codEtt" 
						selected="<%=lEstrutura%>"
						order="nomeEtt"
						scripts="<%=_disabled%>"
						filters="virtual=false"	
				/>		
		</td>
	</tr>	
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp" %>