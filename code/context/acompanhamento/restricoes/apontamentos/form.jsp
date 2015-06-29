<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="codPtc" value="<%=Pagina.getParamStr(request, "codPtc")%>">
<input type="hidden" name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder e o link voltar -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">

<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">
<%if (!Pagina.getParamStr(request, "codIett").trim().equals("")) {%>
	<input type="hidden" name="codIett"       value="<%=Pagina.getParamStr(request, "codIett")%>">
<%} else { %>
	<input type="hidden" name="codIett"       value="<%=ari.getItemEstruturaIett().getCodIett()%>">
<%} %>
<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
<input type="hidden" name="codAba"        value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type="hidden" name="labelEttf"     value="<%=estruturaFuncao.getLabelEttf()%>">
<input type="hidden" name="Usuario"       value="<%=usuario.getCodUsu().toString()%>">
<input type="hidden" name="codApt"        value="<%=apontamento.getCodApt() == null ? "":apontamento.getCodApt().toString()%>">

<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">


<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">


	
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
			UsuarioUsu usuarioApontamento = new UsuarioUsu();
			%>
			
		<%
		if(apontamento.getUsuarioUsu() != null ){%>
				<input type="hidden" name="codUsu" value="<%=apontamento.getUsuarioUsu().getCodUsu()%>">
				<%usuarioApontamento = apontamento.getUsuarioUsu();%>
		<%} else {
				//TODO: Recuperar Usuário da Session%>
				<%usuarioApontamento = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();%>
				<input type="hidden" name="codUsu" value="<%=usuarioApontamento.getCodUsu()%>">				
		<%}%>

		<%=usuarioApontamento.getNomeUsuSent()%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Comentários</td>
		<td><textarea name="textoApt" rows="5" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(apontamento.getTextoApt())%></textarea>
		</td>
	</tr>	
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../../include/estadoMenu.jsp"%>
