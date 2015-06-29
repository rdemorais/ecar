
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type="hidden" name="hidAcao" value="">

<tr>
			<td colspan=2>
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
	</tr>
	<tr>
		<td class="label">* Nome</td>
		<td>		
		<%
	// Jogar valores para uma variável para evitar nullPointerException
	String nomeBenef = "";
	String codBenef = "";
	if(ieBenef.getBeneficiarioBnf()!=null){
		nomeBenef = ieBenef.getBeneficiarioBnf().getNomeBnf();
		codBenef = ieBenef.getBeneficiarioBnf().getCodBnf().toString();
	}
	
%>
	<input type="text" name="nomeBnf" size="55" value="<%=Pagina.trocaNull(nomeBenef)%>" disabled>
	<input type="hidden" name="codBnf" value="<%=codBenef%>">			
		<input type="button" name="pesqBnf" value="Pesquisar" class="botao" <%=_disabled%> onclick="popup_pesquisa('ecar.popup.PopUpBeneficiario');">
	</td>
</tr>		
<tr>
	<td class="label">* Quantidade Prevista</td>
	<td>
		<input type="text" name="qtdePrevistaIEttB" size="13" <%=_disabled%> maxlength="13" value="<%=Pagina.trocaNullNumeroSemDecimal(ieBenef.getQtdPrevistaIettb())%>" onkeyup="reformatNumber(this);">
		
	</td>
</tr>			
<tr>
	<td class="label">Coment&aacute;rio</td>
	<td>
		<textarea name="comentarioIEttB" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(ieBenef.getComentarioIettb())%></textarea>
	</td>
</tr>			

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
