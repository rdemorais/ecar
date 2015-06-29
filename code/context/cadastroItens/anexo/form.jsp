<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">

<%
/* faz o tratamento do request dependendo se é um form de upload ou não */
if(!isFormUpload){

%>
<input type=hidden name="codIettuc" value="<%=Pagina.getParamStr(request, "codIettuc")%>">
<input type=hidden name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
<%} else {

%>
<input type="hidden" name="codAba" value=<%=FileUpload.verificaValorCampo(campos, "codAba")%>>
<input type="hidden" name="cod" value=<%=FileUpload.verificaValorCampo(campos, "cod")%>>
<input type="hidden" name="codIettuc" value=<%=FileUpload.verificaValorCampo(campos, "codIettuc")%>>
<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
<%}%>

<%UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();%>
<input type="hidden" name="usuarioUsu" value="<%=usuario.getCodUsu()%>">				


<input type=hidden name="hidAcao" value="">
	
	<!-- ############### Tag de Anexos ################### -->
	<util:anexosUploadTag 
		anexo="<%=anexo%>" 
		disabled="<%=_disabled%>"
		readOnly="<%=_readOnly%>"
		nomeComboTag="uploadTipoArquivoUta"
		objetoComboTag="ecar.pojo.UploadTipoArquivoUta"
		labelComboTag="descricaoUta"
		valueComboTag="codUta"
		orderComboTag="descricaoUta"
		scriptsComboTag="<%=_disabled%>"
		colecao="<%=categoriaAnexo.getUploadTipoCategoriaUtc().getUploadTipoArquivoUtas()%>"
	/>
	<!-- ############### Tag de Anexos  ################### -->			
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
