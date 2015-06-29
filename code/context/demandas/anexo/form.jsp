

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Pagina"%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<%@page import="comum.util.Util"%><jsp:directive.page import="ecar.pojo.RegApontamentoRegda"/>
<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
<input type="hidden" name="aptRefazPesquisa" value="">
    <tr class="label" align="left">
    	
    	<%
    	String codRegdan = Pagina.getParam(request, "codRegdan");	
		String hashNomeArquivo = null;
		UsuarioUsu usuarioImagem = null;  
    	
    	if(codRegdan != null && !codRegdan.equals("") && !codRegdan.equals("null")) {    	
    		
    		String pathRaiz = configura.getRaizUpload();
    		String pathAnexo = configura.getUploadAnexos();
    		
    		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
    		
    		// código de tratamento de permissão de acesso a arquivo
			hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz + pathAnexo, regAnexo.getSrcAnexo());
			//
			Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz + pathAnexo, regAnexo.getSrcAnexo());	
    		
    	%>
    		<td class="label">*Arquivo Anexo:</td>
    		<td align="left">
    			<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>"><%=regAnexo.getSrcAnexo()%></a> 		
    		</td>
    	<%
    	} else {
    	%>
    		<td class="label">*Upload de Arquivos:</td>
    		<td align="left"><input type="file" name="arquivoAnexo" id="arquivoAnexo"></td>
    	<%
    	}
    	%>
    </tr>
	<tr>
		<td class="label">*Descrição</td>
		
		<td><textarea name="descricaoAnexoDemanda" cols="60" rows="4"
			<%if (acao.equals("incluir")){
				if (visaoSessao.getEhPermitidoIncluirAnexoDemanda() == null || visaoSessao.getEhPermitidoIncluirAnexoDemanda().equals(Pagina.NAO)) {
				%>
					readonly="readonly"	
				<%}
			} else if (acao.equals("alterar")){
				if (visaoSessao.getEhPermitidoAlterarAnexoDemanda() == null || visaoSessao.getEhPermitidoAlterarAnexoDemanda().equals(Pagina.NAO)) {
				%>
					readonly="readonly"	
				<%}
			}%> 
			onkeyup="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);" 
			onkeydown="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);" 
			onblur="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);"
			><%if(codRegdan != null && !codRegdan.equals("") && !codRegdan.equals("null")) { %><%=Pagina.trocaNull(regAnexo.getDescricao()).replaceAll("\r\n","<br>")%><%}%></textarea></td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
