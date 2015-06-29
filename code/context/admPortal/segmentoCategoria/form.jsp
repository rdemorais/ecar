<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%>


<%
if("".equals(codSegmento)){
	List segmentos = new SegmentoDao(request).getSegmentosLivres(application);
	String strSelected = "";
	if(segmentoCategoria.getSegmentoSgt() != null)
		strSelected = segmentoCategoria.getSegmentoSgt().getCodSgt().toString();
	%>
		<tr>
			<td class="label"><%=_obrigatorio%> Segmento</td>
			<td>
				<combo:ComboTag 	
					nome="segmentoSgt"
					objeto="ecar.pojo.SegmentoSgt" 
					label="tituloSgt" 
					value="codSgt" 
					order="tituloSgt"
					scripts="<%=_disabled%>"
					selected="<%=strSelected%>"
					colecao="<%=segmentos%>"
				/>						
			</td>
		</tr> 	
	<%
} else {
	%>
	<input type="hidden" name="segmentoSgt" value="<%=codSegmento%>">
	<%
}
%>

<tr>
	<td class="label"><%=_obrigatorio%> Título</td>
	<td><input type="text" name="tituloSgtc" size="25" maxlength="200" value="<%=Pagina.trocaNull(segmentoCategoria.getTituloSgtc())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>
	<td>
		<textarea name="descricaoSgtc" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(segmentoCategoria.getDescricaoSgtc())%></textarea>		
	</td>
</tr> 
<tr>
	<td class="label">
	<%
	if(segmentoCategoria.getImagemSgtc() != null && !segmentoCategoria.getImagemSgtc().equals("")){	
	%>
		Alterar Imagem para
	<%
	} else {
	%>
		Imagem
	<%}%>
	</td>
	<td>
			<input type="file" name="imagemSgtc" <%=_disabled%>>
	</td>
</tr>
<%

String pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;  

if(segmentoCategoria.getImagemSgtc() != null && segmentoCategoria.getImagemSgtc().trim().length()>0){
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, segmentoCategoria.getImagemSgtc());
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, segmentoCategoria.getImagemSgtc());
	
%>
	<tr>
		<td class="label" valign="top">Imagem Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
		<input type="checkbox" class="form_check_radio" name="imagem" <%=_disabled%>>excluir imagem
		</td>
	</tr>	
	<%
}
%>	
<tr>
	<td class="label">Legenda Imagem</td>
	<td><input type="text" name="legImagemSgtc" size="25" maxlength="20" value="<%=Pagina.trocaNull(segmentoCategoria.getLegImagemSgtc())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoSgtc" valorSelecionado="<%=segmentoCategoria.getIndAtivoSgtc()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Utiliza Tipo de Acesso</td>
	<td>
		<util:comboSimNaoTag nome="indUtilizTpAcessoSgtc" valorSelecionado="<%=segmentoCategoria.getIndUtilizTpAcessoSgtc()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label" valign="top">Tipo de Acesso</td>
	<td>
		<%
		List atributos = new SisAtributoDao(request).getAtributosTipoAcesso();
		%>
		<combo:CheckListTag 
				nome="sisAtributoSatb"
				objeto="ecar.pojo.SisAtributoSatb" 
				label="descricaoSatb" 
				value="codSatb" 
				order="descricaoSatb"
				filters="indAtivoSatb=S"
				scripts="<%=_disabled%>"
				colecao="<%=atributos%>"
				selected="<%=segmentoCategoriaDao.getTipoAcessoSegmentoCategoriaById(segmentoCategoria)%>"				
		/>	
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
