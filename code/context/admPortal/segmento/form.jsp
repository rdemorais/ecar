<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>

<%@page import="ecar.pojo.ConfiguracaoCfg"%><util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%>

<tr>
	<td class="label"><%=_obrigatorio%> Área</td>
	<td>
		<%
		String strSelectedArea = "";
		if(segmento.getSegmentoAreaSgta() != null){
			strSelectedArea = segmento.getSegmentoAreaSgta().getCodSgta().toString();
		}
		%>
		<combo:ComboTag 
				nome="segmentoArea"
				objeto="ecar.pojo.SegmentoAreaSgta" 
				label="descricaoSgta" 
				value="codSgta" 
				order="descricaoSgta"
				scripts="<%=_disabled%>"
				selected="<%=strSelectedArea%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Título</td>
	<td><input type="text" name="tituloSgt" size="25" maxlength="20" value="<%=Pagina.trocaNull(segmento.getTituloSgt())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>
	<td>
		<textarea name="descricaoSgt" <%=_readOnly%> rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(segmento.getDescricaoSgt())%></textarea>		
	</td>
</tr> 
<tr>
	<td class="label">
	<%
	if(segmento.getImagemSgt() != null){	
	%>
		Alterar Imagem para
	<%
	} else {
	%>
		Imagem
	<%}%>
	</td>
	<td>
			<input type="file" name="imagemSgt" <%=_disabled%>>
	</td>
</tr>
<%
//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
String pathRaiz = configuracao.getRaizUpload();
String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;  
if(segmento.getImagemSgt() != null){
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, segmento.getImagemSgt());
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, segmento.getImagemSgt());
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
	<td><input type="text" name="legendaImagemSgt" size="25" maxlength="20" value="<%=Pagina.trocaNull(segmento.getLegendaImagemSgt())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Leiaute do Segmento</td>
	<td>
		<%
		String strSelectedLeiaute = "";
		if(segmento.getSegmentoLeiauteSgtl() != null){
			strSelectedLeiaute = segmento.getSegmentoLeiauteSgtl().getCodSgtl().toString();
		}
		%>	
		<combo:ComboTag 
				nome="segmentoLeiauteSgtl"
				objeto="ecar.pojo.SegmentoLeiauteSgtl" 
				label="descricaoSgtl" 
				value="codSgtl" 
				order="descricaoSgtl"
				scripts="<%=_disabled%>"
				selected="<%=strSelectedLeiaute%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label">Grupo de Atributos para Editoriais</td>
	<td>
		<%
		String strSelectedGrupoAtributoEditoriais = "";
		if(segmento.getSisGrupoAtributoSga() != null){
			strSelectedGrupoAtributoEditoriais = segmento.getSisGrupoAtributoSga().getCodSga().toString();
		}
		%>	
		<combo:ComboTag 
			nome="sisGrupoAtributoEditorias"
			objeto="ecar.pojo.SisGrupoAtributoSga"
			label="descricaoSga"
			value="codSga"
			order="descricaoSga"
			filters="indAtivoSga=S"
			scripts="<%=_disabled%>"
			selected="<%=strSelectedGrupoAtributoEditoriais%>"			
		/>
	</td>
</tr> 
<tr>
	<td class="label">URL Link Pesquisa</td>
	<td><input type="text" name="linkPesquisaSgt" size="70" maxlength="100" value="<%=Pagina.trocaNull(segmento.getLinkPesquisaSgt())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Aparece no Menu</td>
	<td>
		<util:comboSimNaoTag nome="indMenuSgt" valorSelecionado="<%=segmento.getIndMenuSgt()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoSgt" valorSelecionado="<%=segmento.getIndAtivoSgt()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Utiliza Tipo de Acesso</td>
	<td>
		<util:comboSimNaoTag nome="indUtilizTpAcessoSgt" valorSelecionado="<%=segmento.getIndUtilizTpAcessoSgt()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
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
				selected="<%=segmentoDao.getTipoAcessoSegmentoById(segmento)%>"				
		/>	
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
