<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 


<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<%@page import="comum.util.Util"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="javax.swing.ImageIcon"%>
<%@page import="java.awt.Image"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
<%
	//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
    String pathRaiz = configuracao.getRaizUpload();
	if(segItem.getSegmentoSgt() != null){
		List listSgtc = new ArrayList();
		if(segItem.getSegmentoSgt().getSegmentoCategoriaSgtcs() != null) {
			listSgtc.addAll(segItem.getSegmentoSgt().getSegmentoCategoriaSgtcs());
			
			Collections.sort(listSgtc,
				new Comparator() {
	        		public int compare(Object o1, Object o2) {
	        		    return ( (SegmentoCategoriaSgtc)o1 ).getTituloSgtc().compareToIgnoreCase( ( (SegmentoCategoriaSgtc)o2 ).getTituloSgtc() );
	        		}
	    		} );	
    	}
		if ( segItem.getSegmentoCategoriaSgtc() != null ) {
			if(listSgtc != null && listSgtc.size() == 1){
				SegmentoCategoriaSgtc segUnico = (SegmentoCategoriaSgtc) listSgtc.get(0);
%>
				<input type="hidden" name="segmentoCategoriaSgtc" value="<%=segUnico.getCodSgtc()%>">
<%			}
			else {
%>
			<td class="label"><%=_obrigatorio%> Categoria</td>
			<td>
			<combo:ComboTag 
				nome="segmentoCategoriaSgtc"
				objeto="ecar.pojo.SegmentoCategoriaSgtc" 
				label="tituloSgtc" 
				value="codSgtc" 
				order="tituloSgtc"
				selected="<%=segItem.getSegmentoCategoriaSgtc().getCodSgtc().toString()%>"
				colecao="<%=listSgtc%>"
				scripts="<%=_disabled%>"
			/>
<%
			}
		} else {
			if(listSgtc != null && listSgtc.size() == 1){
				SegmentoCategoriaSgtc segUnico = (SegmentoCategoriaSgtc) listSgtc.get(0);
%>
				<input type="hidden" name="segmentoCategoriaSgtc" value="<%=segUnico.getCodSgtc()%>">
<%			}
			else{
%>
			<td class="label"><%=_obrigatorio%> Categoria</td>
			<td>
			<combo:ComboTag 
				nome="segmentoCategoriaSgtc"
				objeto="ecar.pojo.SegmentoCategoriaSgtc" 
				label="tituloSgtc" 
				value="codSgtc" 
				order="tituloSgtc"
				colecao="<%=listSgtc%>"
				scripts="<%=_disabled%>"
			/>
<%
			}
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
<%
		if(editorias != null && editorias.size() == 1){
			SisAtributoSatb editoriaUnica = (SisAtributoSatb) editorias.get(0);
%>
			<input type="checkbox" class="form_check_radio" name="editoriasSisAtributoSatb" value="<%=editoriaUnica.getCodSatb()%>" checked> <%=editoriaUnica.getDescricaoSatb()%>
<%		
		}
		else{
%>		
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
<%
		}
%>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Título</td>
	<td>
		<textarea name="tituloSgti" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(segItem.getTituloSgti())%></textarea>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Linha de Apoio</td>
	<td>
		<textarea name="linhaApoioSgti" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(segItem.getLinhaApoioSgti())%></textarea>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Íntegra</td>

<%
	if (hidRichText != (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue()){
%>
	
		<td>
			<script language="JavaScript" type="text/javascript">
			//Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
			initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
			</script>
			
			<script language="JavaScript" type="text/javascript">
			//Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
			//writeRichText('rte', 'enter body text here', 400, 200, true, false);				
			writeRichText('rte', "<%=Pagina.trocaNull(segItem.getIntegraSgti()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true, "<%=_readOnly%>");
			
			</script>	
			
			<input type="hidden" name="richText" size="90"  >
			<input type="hidden" name="integraSgti" size="90"  >	
		</td>			
<%
	}else{
%>		
		<td>	
			<textarea name="integraSgti" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 4000);" <%=_readOnly%>><%=Pagina.trocaNull(segItem.getIntegraSgti())%></textarea>
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
	<td class="label"><%=_obrigatorio%> Data Início Validade</td>
	<td>
		<input type="text" name="dataIniValidadeSgti" size="14" maxlength="10" <%=_disabled%> value="<%=Pagina.trocaNullData(segItem.getDataIniValidadeSgti())%>" onkeyup="mascaraData(event, this);">
		<c:if test="<%=_disabled.equals("")%>">
			<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIniValidadeSgti, '')">
		</c:if>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Data Fim Validade</td>
	<td>
		<input type="text" name="dataFimValidadeSgti" size="14" maxlength="10" <%=_disabled%> value="<%=Pagina.trocaNullData(segItem.getDataFimValidadeSgti())%>" onkeyup="mascaraData(event, this);">
		<c:if test="<%=_disabled.equals("")%>">
			<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFimValidadeSgti, '')">
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
	<td class="label"><%=_obrigatorio%> Leiaute</td>
	<td>
<%
		//if(this.segmentoItemLeiauteSgtil.getCodSgtil().compareTo(Long.valueOf(11)) > 0)
		List LeiautesNovos = segItemDao.getLeiautesNovos();		
		
		if ( segItem.getSegmentoItemLeiauteSgtil() != null ) {
%>
			<combo:ComboTag 
				nome="segmentoItemLeiauteSgtil"
				objeto="ecar.pojo.SegmentoItemLeiauteSgtil" 
				label="descricaoSgtil" 
				value="codSgtil" 
				order="descricaoSgtil"		
				selected="<%=segItem.getSegmentoItemLeiauteSgtil().getCodSgtil().toString()%>"						
				colecao="<%=LeiautesNovos%>"				
				scripts="<%=_disabled%>"
			/>
<%
		} else {
%>
			<combo:ComboTag 
				nome="segmentoItemLeiauteSgtil"
				objeto="ecar.pojo.SegmentoItemLeiauteSgtil" 
				label="descricaoSgtil" 
				value="codSgtil" 
				order="descricaoSgtil"
				colecao="<%=LeiautesNovos%>"								
				scripts="<%=_disabled%>"
			/>
<%
		}
%>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Destaque Capa</td>
	<td>
		<util:comboSimNaoTag nome="indSuperDestaqueSgti" valorSelecionado="<%=segItem.getIndSuperDestaqueSgti()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Destaque</td>
	<td>
		<util:comboSimNaoTag nome="indDestaqueSgti" valorSelecionado="<%=segItem.getIndDestaqueSgti()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label">
<%
		if(segItem.getImagemCapaSgti() != null && !segItem.getImagemCapaSgti().equals("")){	
%>
			Alterar Imagem Capa para
<%
		} else {
%>
			Imagem Capa
<%
		}
%>
	</td>
	<td>
		<input type="file" name="imagemCapaSgti" <%=_disabled%>>
	</td>
</tr>
<%
String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;  
if(segItem.getImagemCapaSgti() != null && segItem.getImagemCapaSgti().trim().length()>0){
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, segItem.getImagemCapaSgti());
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, segItem.getImagemCapaSgti());
	
%>
	<tr>
		<td class="label" valign="top">Imagem Capa Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
		<!--  img src="<%=request.getContextPath()%>/DownloadFile.jsp"-->
		<!-- a href="<%=request.getContextPath()%>/admPortal/segmentoCategoria/frm_inc.jsp">Teste</a-->
		<input type="checkbox" class="form_check_radio" name="imagemCapa" <%=_disabled%>>excluir imagem
		</td>
	</tr>	
	<%
}
%>	
<tr>
	<td class="label">Legenda Imagem Capa</td>
	<td>
		<input type="text" name="legendaImagCapaSgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getLegendaImagCapaSgti())%>">
	</td>
</tr>
<tr>
	<td class="label">
<%
		if(segItem.getImagem1Stgi() != null && !segItem.getImagem1Stgi().equals("")){	
%>
			Alterar Imagem 1 para
<%
		} else {
%>
			Imagem 1
<%
		}
%>
	</td>
	<td>
		<input type="file" name="imagem1Sgti" <%=_disabled%>>
	</td>
</tr>
<%
String hashNomeArquivo1 = null;
UsuarioUsu usuarioImagem1 = null;  
if(segItem.getImagem1Stgi() != null && segItem.getImagem1Stgi().trim().length()>0){
	
	hashNomeArquivo1 = Util.calcularHashNomeArquivo(pathRaiz, segItem.getImagem1Stgi());
	usuarioImagem1 = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem1, hashNomeArquivo1, pathRaiz, segItem.getImagem1Stgi());
%>
	<tr>
		<td class="label" valign="top">Imagem 1 Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo1%>">
		<input type="checkbox" class="form_check_radio" name="imagem1" <%=_disabled%>>excluir imagem
		</td>
	</tr>	
	<%
}
%>	
<tr>
	<td class="label">Legenda Imagem 1</td>
	<td>
		<input type="text" name="legendaImag1Sgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getLegendaImag1Sgti())%>">
	</td>
</tr>
<tr>
	<td class="label">
<%
		if(segItem.getImagem2Sgti() != null && !segItem.getImagem2Sgti().equals("")){	
%>
			Alterar Imagem 2 para
<%
		} else {
%>
			Imagem 2 
<%
		}
%>
	</td>
	<td>
		<input type="file" name="imagem2Sgti" <%=_disabled%>>
	</td>
</tr>
<%
String hashNomeArquivo2 = null;
UsuarioUsu usuarioImagem2 = null;  
if(segItem.getImagem2Sgti() != null && segItem.getImagem2Sgti().trim().length()>0){
	
	hashNomeArquivo2 = Util.calcularHashNomeArquivo(pathRaiz, segItem.getImagem2Sgti());
	usuarioImagem2 = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem2, hashNomeArquivo2, pathRaiz, segItem.getImagem2Sgti());
	
%>
	<tr>
		<td class="label" valign="top">Imagem 2 Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo2%>">
		<input type="checkbox" class="form_check_radio" name="imagem2" <%=_disabled%>>excluir imagem
		</td>
	</tr>	
	<%
}
%>	
<tr>
	<td class="label">Legenda Imagem 2</td>
	<td>
		<input type="text" name="legendaImag2Sgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getLegendaImag2Sgti())%>">
	</td>
</tr>
<tr>
	<td class="label">
<%
		if(segItem.getImagem3Sgti() != null && !segItem.getImagem3Sgti().equals("")){	
%>
			Alterar Imagem 3 para
<%
		} else {
%>
			Imagem 3
<%
		}
%>
	</td>
	<td>
		<input type="file" name="imagem3Sgti" <%=_disabled%>>
	</td>
</tr>
<%
String hashNomeArquivo3 = null;
UsuarioUsu usuarioImagem3 = null;  
if(segItem.getImagem3Sgti() != null && segItem.getImagem3Sgti().trim().length()> 0){
	hashNomeArquivo3 = Util.calcularHashNomeArquivo(pathRaiz, segItem.getImagem3Sgti());
	usuarioImagem3 = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem3, hashNomeArquivo3, pathRaiz, segItem.getImagem3Sgti());
%>
	<tr>
		<td class="label" valign="top">Imagem 3 Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo3%>">
		<input type="checkbox" class="form_check_radio" name="imagem3" <%=_disabled%>>excluir imagem
		</td>
	</tr>	
	<%
}
%>	
<tr>
	<td class="label">Legenda Imagem 3</td>
	<td>
		<input type="text" name="legendaImag3Sgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getLegendaImag3Sgti())%>">
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
<tr>
	<td class="label">Anexo</td>
	<td>
		<input type="file" name="anexoEnderecoSgti" <%=_disabled%>>
	</td>
</tr>
<%
	String hashNomeArquivo4 = null;
	UsuarioUsu usuarioImagem4 = null;  
	if(segItem.getAnexoEnderecoSgti() != null && segItem.getAnexoEnderecoSgti().trim().length()>0){
		
		hashNomeArquivo4 = Util.calcularHashNomeArquivo(pathRaiz, segItem.getAnexoEnderecoSgti());
		usuarioImagem4 = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
		Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem4, hashNomeArquivo4, pathRaiz, segItem.getAnexoEnderecoSgti());
		
   		String nomeAnexo = "";
		if (segItem.getAnexoEnderecoSgti().lastIndexOf("/") != -1){     
			nomeAnexo = segItem.getAnexoEnderecoSgti().substring(segItem.getAnexoEnderecoSgti().lastIndexOf("/") + 1);
		}
		
       	ImageIcon imagem = new ImageIcon(pathRaiz + segItem.getAnexoEnderecoSgti());
       	if (!((imagem.getIconHeight() < 0) && (imagem.getIconWidth() < 0))){
%>

	<tr>
		<td class="label">Anexo Atual</td>
		<td>
			<img src="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo4%>">
			
			<input type="checkbox" class="form_check_radio" name="anexo" <%=_disabled%>> excluir anexo
			<br>
			<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo4%>"><%=FileUpload.getNomeArquivoOriginal(nomeAnexo)%></a>
		</td>
	</tr>
<%
       	} else {

       	%>
       	
   <tr>
		<td class="label">Anexo Atual</td>
		<td>
			<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo4%>"><%=FileUpload.getNomeArquivoOriginal(nomeAnexo)%></a>
			<input type="checkbox" class="form_check_radio" name="anexo" <%=_disabled%>> excluir anexo
		</td>
	</tr>

       	<%}
	}
%>
<tr>
	<td class="label">Legenda do Anexo</td>
	<td>
		<input type="text" name="anexoLegendaSgti" size="25" maxlength="20" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getAnexoLegendaSgti())%>">
	</td>
</tr>
<tr>
	<td class="label">Link do anexo</td>
	<td>
		<input type="text" name="urlLinkSgti" size="25" maxlength="100" <%=_disabled%> value="<%=Pagina.trocaNull(segItem.getUrlLinkSgti())%>">
	</td>
</tr>

<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>	