<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 


<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Categoria</td>
	<td>
<%
	if(segItem.getSegmentoSgt() != null){
		if ( segItem.getSegmentoCategoriaSgtc() != null ) {
%>
			<combo:ComboTag 
				nome="segmentoCategoriaSgtc"
				objeto="ecar.pojo.SegmentoCategoriaSgtc" 
				label="tituloSgtc" 
				value="codSgtc" 
				order="tituloSgtc"
				selected="<%=segItem.getSegmentoCategoriaSgtc().getCodSgtc().toString()%>"
				colecao="<%=segItem.getSegmentoSgt().getSegmentoCategoriaSgtcs()%>"
				scripts="<%=_disabled%>"
			/>
<%
		} else {
%>
			<combo:ComboTag 
				nome="segmentoCategoriaSgtc"
				objeto="ecar.pojo.SegmentoCategoriaSgtc" 
				label="tituloSgtc" 
				value="codSgtc" 
				order="tituloSgtc"
				colecao="<%=segItem.getSegmentoSgt().getSegmentoCategoriaSgtcs()%>"
				scripts="<%=_disabled%>"
			/>
<%
		}
	}else{
		out.print("Problemas na geração da combo, segmento não cadastrado");
	}
%>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Editoria</td>
	<td>
<%
		List editorias = segItemDao.getEditoriasBySegItem(segItem);
		
		if(editorias.size() <= 0)
			out.print("Não cadastrado");
		
		List editSelecionados = segItemDao.getIdsEditSelecionados(segItem);
%>
		<input type="hidden" name="hidTamListaEditorias" value="<%=editorias.size()%>">
		
		<combo:CheckListTag 
				nome="editoriasSisAtributoSatb"
				objeto="ecar.pojo.SisAtributoSatb" 
				label="descricaoSatb" 
				value="codSatb" 
				order="descricaoSatb"
				filters="indAtivoSatb=S"
				scripts="<%=_disabled%>"
				colecao="<%=editorias%>"
				selected="<%=editSelecionados%>"
		/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Pergunta</td>
	<td>
		<textarea name="tituloSgti" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
				<%=_readOnly%>><%=Pagina.trocaNull(segItem.getTituloSgti())%></textarea>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Resposta</td>
	
<%
	if (hidRichText != (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue()){
%>
	
		<td>
			<script language="JavaScript" type="text/javascript">
			//Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
			initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
			</script>
			
			<script language="JavaScript" type="text/javascript">
			//Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
			writeRichText('rte', "<%=Pagina.trocaNull(segItem.getIntegraSgti()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true, "<%=_readOnly%>");
			
			</script>	
			
			<input type="hidden" name="richText" size="90"  >
			<input type="hidden" name="integraSgti" size="90"  >	
		</td>			
<%
	}else{
%>		
		<td>	
			<textarea name="integraSgti" rows="4" cols="60" 
				onkeyup="return validaTamanhoLimite(this, 4000);" 
				onkeydown="return validaTamanhoLimite(this, 4000);"
				onblur="return validaTamanhoLimite(this, 4000);"
				<%=_readOnly%>><%=Pagina.trocaNull(segItem.getIntegraSgti())%></textarea>
		</td>	
<%
	}
%>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Fonte</td>
	<td>
<%
		if ( segItem.getSegmentoItemFonteSgtif() != null ) {
%>
			<combo:ComboTag 
				nome="segmentoItemFonteSgtif"
				objeto="ecar.pojo.SegmentoItemFonteSgtif" 
				label="descricaoSgtif" 
				value="codSgtif" 
				order="descricaoSgtif"
				selected="<%=segItem.getSegmentoItemFonteSgtif().getCodSgtif().toString()%>"
				scripts="<%=_disabled%>"
			/>
<%
		} else {
%>
			<combo:ComboTag 
				nome="segmentoItemFonteSgtif"
				objeto="ecar.pojo.SegmentoItemFonteSgtif" 
				label="descricaoSgtif" 
				value="codSgtif" 
				order="descricaoSgtif"
				scripts="<%=_disabled%>"
			/>
<%
		}
%>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Data</td>
	<td>
		<input type="text" name="dataItemSgti" size="14" maxlength="10" <%=_disabled%> value="<%=Pagina.trocaNullData(segItem.getDataItemSgti())%>" onkeyup="mascaraData(event, this);">
		<c:if test="<%=_disabled.equals("")%>">
			<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataItemSgti, '')">
		</c:if>
	</td>
</tr>
<tr>
	<td class="label">Palavras</td>
	<td>
		<textarea name="palavrasChavesSgti" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(segItem.getPalavrasChavesSgti())%></textarea>
	</td>
</tr>

<tr>
	<td class="label">
<%
		if(segItem.getAnexoEnderecoSgti() != null){	
%>
			Alterar Anexo para
<%
		} else {
%>
			Anexo
<%
		}
%>
	</td>
	<td>
		<input type="file" name="anexoEnderecoSgti" value="<%=segItem.getAnexoEnderecoSgti()%>" <%=_disabled%>>
	</td>
</tr>
<%

String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;
//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
String pathRaiz = configuracao.getRaizUpload();

if(segItem.getAnexoEnderecoSgti() != null){	
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, segItem.getAnexoEnderecoSgti());
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, segItem.getAnexoEnderecoSgti());
	
	String nomeArquivo = segItem.getAnexoEnderecoSgti();
		
	if(nomeArquivo.lastIndexOf("/") != -1){
    	nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
    }
%>
	<tr>
		<td class="label">Anexo Atual:</td>
		<td valign="middle"><a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>">
		<%=nomeArquivo%></a></td>
	</tr>
	<tr>
		<td></td>
		<td>
		<input type="checkbox" class="form_check_radio" name="anexo" <%=_disabled%>>excluir anexo
		</td>
	</tr>	
<%
}
%>	
<tr>
	<td class="label">Legenda Anexo</td>
	<td>
		<input type="text" name="anexoLegendaSgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getAnexoLegendaSgti())%>">		
	</td>
</tr>
<tr>
	<td class="label">URL Link</td>
	<td>
		<input type="text" name="urlLinkSgti" size="60" maxlength="100" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getUrlLinkSgti())%>">
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoSgti" valorSelecionado="<%=segItem.getIndAtivoSgti()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Utiliza Tipo de Acesso</td>
	<td>
		<util:comboSimNaoTag nome="indUtilizTpAcessoSgti" valorSelecionado="<%=segItem.getIndUtilizTpAcessoSgti()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label" valign="top">Tipo Acesso</td>
	<td>
<%
		List atributos = new SisAtributoDao(request).getAtributosTipoAcesso();
		Iterator it = atributos.iterator();
		
		if (atributos.size() <= 0)
			out.print("Não cadastrado");
		
		List atbSelecionados = segItemDao.getIdsTpAcessoSelecionados(segItem);
%>
		<input type="hidden" name="hidTamListaAtributos" value="<%=atributos.size()%>">

		<combo:CheckListTag 
				nome="tpAcessoSisAtributoSatb"
				objeto="ecar.pojo.SisAtributoSatb" 
				label="descricaoSatb" 
				value="codSatb" 
				order="descricaoSatb"
				filters="indAtivoSatb=S"
				scripts="<%=_disabled%>"
				colecao="<%=atributos%>"
				selected="<%=atbSelecionados%>"
		/>
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>	