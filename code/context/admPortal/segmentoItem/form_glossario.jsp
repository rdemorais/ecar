<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<%@page import="comum.util.Util"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Letra</td>
	<td>
<%
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
%>
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
		} else {
%>
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
	<td class="label"><%=_obrigatorio%> Termo</td>
	<td>
		<textarea name="tituloSgti" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(segItem.getTituloSgti())%></textarea>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>

<%
	if (hidRichText != (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue()){
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
			<textarea name="integraSgti" rows="4" cols="60" 
				onkeyup="return validaTamanhoLimite(this, 4000);" 
				onkeydown="return validaTamanhoLimite(this, 4000);"
				onblur="return validaTamanhoLimite(this, 4000);" <%=_readOnly%>><%=Pagina.trocaNull(segItem.getIntegraSgti())%></textarea>
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
		if(segItem.getImagem1Stgi() != null){	
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

String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;
//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
String pathRaiz = configuracao.getRaizUpload();

if(segItem.getImagem1Stgi() != null){
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, segItem.getImagem1Stgi());
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, segItem.getImagem1Stgi());
	
%>
	<tr>
		<td class="label" valign="top">Imagem 1 Atual</td>
		<td>
		<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
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