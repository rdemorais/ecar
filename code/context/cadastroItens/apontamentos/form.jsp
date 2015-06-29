<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">

<input type=hidden name="codPtc" value="<%=Pagina.getParamStr(request, "codPtc")%>">
<input type=hidden name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="hidAcao" value="">
<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">


	<tr> 
			<td colspan=2>
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
	</tr>
	<tr>
		<td class="label">Data:</td>
		<td>
		<%
		if(apontamento.getDataInclusaoApt() != null)
			out.println(Pagina.trocaNullData(apontamento.getDataInclusaoApt()));
		else
			out.println(Pagina.trocaNullData(Data.getDataAtual()));
		%>
		</td>
	</tr>
	<tr>
		<td class="label">Usu&aacute;rio</td>
		<td>
			<%
			UsuarioUsu usuario = new UsuarioUsu();
			%>
			
		<%
		if(apontamento.getUsuarioUsu() != null ){%>
				<input type="hidden" name="codUsu" value="<%=apontamento.getUsuarioUsu().getCodUsu()%>">
				<%usuario = apontamento.getUsuarioUsu();%>
		<%} else {
				//TODO: Recuperar Usuário da Session%>
				<%usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();%>
				<input type="hidden" name="codUsu" value="<%=usuario.getCodUsu()%>">				
		<%}%>

		<%=usuario.getNomeUsuSent()%>
		</td>
	</tr>
	<tr>
		<td class="label">* Texto</td>
		<td><textarea name="textoApt" rows="5" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(apontamento.getTextoApt())%></textarea>
		</td>
	</tr>	
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
