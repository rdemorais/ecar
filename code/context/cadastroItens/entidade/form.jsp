<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%
	
	String codEnt = new String();
	String nomeEnt = new String();
	
	if (itemEstrutEntidade.getEntidadeEnt() != null){
		codEnt = itemEstrutEntidade.getEntidadeEnt().getCodEnt().toString();
		nomeEnt = itemEstrutEntidade.getEntidadeEnt().getNomeEnt();
	}else{
		codEnt = "";
		nomeEnt = "";
	}
%>
	
	<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="hidAcao" value="">
	

	<tr>
		<td colspan=2>
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
		<td class="label">* Nome</td>
		<td>
			<input type="hidden" name="codEnt" value="<%=codEnt%>">
			<textarea name="nomeEnt" rows="3" cols="60" readonly><%=nomeEnt%></textarea>
			<input type="button" class="botao" name="pesquisarEnt" value="Pesquisar" onclick="popup_pesquisa('ecar.popup.PopUpEntidade')" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">* Atua&ccedil;&atilde;o</td>
		<td>
<%
			String scripts = new String();
			if ( itemEstrutEntidade.getTipoParticipacaoTpp() != null){
				scripts = _disabled + " onchange='atualizaCodigo(form);'";
%>		
			<combo:ComboTag 
					nome="tipoParticipacaoTpp"
					objeto="ecar.pojo.TipoParticipacaoTpp"
					label="descricaoTpp"
					value="codTpp"
					filters="indAtivoTpp=S"
					order="descricaoTpp"
					selected="<%=itemEstrutEntidade.getTipoParticipacaoTpp().getCodTpp().toString()%>"
					scripts="<%=scripts%>"
			/>
			<input type=hidden name="codTpp" value="<%=itemEstrutEntidade.getTipoParticipacaoTpp().getCodTpp().toString()%>">
<%
			} else {
				scripts = "onchange='atualizaCodigo(form);'";
%>
			<combo:ComboTag 
					nome="tipoParticipacaoTpp"
					objeto="ecar.pojo.TipoParticipacaoTpp"
					label="descricaoTpp"
					value="codTpp"
					filters="indAtivoTpp=S"
					order="descricaoTpp"
					scripts="<%=scripts%>"
			/>
			<input type=hidden name="codTpp" value="">
<%
			}
%>
		</td>
	</tr>
	<tr>
		<td class="label">Participa&ccedil;&atilde;o</td>
		<td><textarea name="descricaoIette" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(itemEstrutEntidade.getDescricaoIette())%></textarea></td>
	</tr>
	<tr>
		<td class="label">Data In&iacute;cio</td>
		<td>
			<input type="text" name="dataInicioIette" size="13" maxlength="10" value="<%=Pagina.trocaNullData(itemEstrutEntidade.getDataInicioIette())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicioIette, '')">
			</c:if>
		</td>		
	</tr>
	<tr>
		<td class="label">Data Fim</td>
		<td>
		<input type="text" name="dataFimIette" size="13" maxlength="10" value="<%=Pagina.trocaNullData(itemEstrutEntidade.getDataFimIette())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
		<c:if test="<%=_disabled.equals("")%>">
			<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFimIette, '')">
		</c:if>
		</td>		
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>
