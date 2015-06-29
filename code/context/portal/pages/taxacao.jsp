<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ page language="java" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ImageInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/datas.js"></script>
<script language="JavaScript">

function detalhe(codSgti){
	document.form.codSgti.value=codSgti;
	document.form.hidAcao.value = "DETALHE";
	document.form.action = "ctrl_taxacao.jsp";	
	document.form.submit();
}

function taxacoes(){
	document.form.hidAcao.value = "TAXACAO_TODOS";
	document.form.action = "ctrl_taxacao.jsp";	
	document.form.submit();
}

function validar() {

	if (document.form.palavra.value == "") {
		alert('<%=_msg.getMensagem("portal.taxacoes.palavra.obrigatorio")%>');
		document.form.palavra.focus();
		return false;
	}	
	
	var check=false;
	
	if (document.form.hidTamListaEditorias.value > 1) {	
		for(i=0 ; document.form.editoria[i] != null ; i++){
			if (document.form.editoria[i].checked){
				check=true;
				break;
			}
		}	
	} else if (document.form.editoria.checked)	check=true;
	
	if (!check) {
		alert('<%=_msg.getMensagem("portal.taxacoes.editoria.obrigatorio")%>');		
		return false;
	}
	
	if (document.form.hidTamListaEditorias.value > 1) {				
		for(i=0 ; document.form.editoria[i] != null ; i++){
			if (document.form.editoria[i].checked){
				if (document.form.editorias.value != "")	document.form.editorias.value += ",";
				document.form.editorias.value += document.form.editoria[i].value;			
			}
		}	
	} else document.form.editorias.value = document.form.editoria.value;
	
	document.form.hidAcao.value = "PESQUISAR";

	return true;
}

</script>

</head>
<body>
<%@ include file="/cabecalho.jsp" %>

<%
try{
		
	SegmentoItemSgti segItem = new SegmentoItemSgti();
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	SegmentoDao segmentoDao = new SegmentoDao(request);
	
%>	
<form name="form" method="post" action="ctrl_taxacao.jsp" onsubmit="javascript:return validar();">

<input type="hidden" name="hidAcao" value="">	
<input type="hidden" name="codSgti" value="">
<input type="hidden" name="editorias" value="">	
<input type="hidden" name="labelsEditorias" value="">	

	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> &gt; Na M&iacute;dia </td>
			<td align="right">&lt;&lt;<a href="index.jsp"> Voltar</a></td>
		</tr>
	</table>	
	<%
	if (request.getSession().getAttribute("strDia")!=null) {%>
		<h1 class="interno">M&Iacute;DIA DO DIA <%=request.getSession().getAttribute("strDia")%></h1>   	<%
    } else {	%>
    	<h1 class="interno">IMAGENS DA M&Iacute;DIA SELECIONADAS</h1>    <%
    }%>
    
    <div id="list_moldura">
	<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td><%=request.getSession().getAttribute("strQtdSegmentoItem")!=null?request.getSession().getAttribute("strQtdSegmentoItem"):""%></td>
			<td align="right"><a href="#" onclick="javascript:taxacoes();">Ver todas as imagens na m&iacute;dia </a> &gt;&gt;</td>
		</tr>
	</table>
<table cellspacing="0" cellpadding="0" width="100%">
<tr>
	<td valign="top">
<!-- -->	
		<table cellspacing="0" class="list_taxacao">
		<%
		int limite = 220;
						
		String imagemCaminho = "";
		 		
    	int larguraImagem = 0;
    			
    	int alturaImagem = 0;
		
		List lista = (List)request.getSession().getAttribute("listaSegmentoItemTaxacao");
		if((lista != null) && (lista.size() > 0)) {	
			int cont = 0;
			Iterator iterator = lista.iterator();
			while (iterator.hasNext())	{		
				cont++;
				SegmentoItemSgti seg = (SegmentoItemSgti)iterator.next();		
				java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
				String data = sf.format(seg.getDataItemSgti());

				//ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();
				String pathRaiz = configuracaoCfg.getRaizUpload();
				
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;  
				%>
				<tr>
					<td>							
						<h1><%=seg.getTituloSgti()%></h1><%				
						
				if ((seg.getImagemCapaSgti() != null && !seg.getImagemCapaSgti().equals(""))&&(cont<=3)){	
				
				
					imagemCaminho = pathRaiz + seg.getImagemCapaSgti();
								    			
    				File arquivo = new File(imagemCaminho);    			
    			
    				ImageInfo imagem = new ImageInfo();    			
    			
    				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, seg.getImagemCapaSgti());
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, seg.getImagemCapaSgti());
    				
    				if (arquivo.exists()){
    			    	//out.print("Achei o arquivo!");			
    					FileInputStream file = new FileInputStream(imagemCaminho);
    					imagem.setInput(file);
    					    			 	    				
    				    if (!imagem.check()) {   				    	    				    	
				%>						
						<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							alt="<%=seg.getLegendaImagCapaSgti()%>" border="0"> 
					<%
						}
						else{
							imagem.redimensionaImagem(limite);
    						larguraImagem = imagem.getWidth();		
							alturaImagem = imagem.getHeight();
					%>
						<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							width="<%=larguraImagem%>" height="<%=alturaImagem%>" alt="<%=seg.getLegendaImagCapaSgti()%>" border="0">	
					<%
						}
					}
					else{
					%>
						<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							alt="<%=seg.getLegendaImagCapaSgti()%>" border="0"> 	
					<%
					}
				}	%>
						<p><%=seg.getLegendaImagCapaSgti()%></p>				
						<p>(<%=data%>)<br><%=seg.getSegmentoItemFonteSgtif().getDescricaoSgtif()%></p>
						<a href="javascript:detalhe(<%=seg.getCodSgti()%>);"><%=seg.getLinhaApoioSgti()%></a>
					</td>
				</tr> 
				<%					
			}			
		} else {
			%>
			<tr>
				<td align="center">
				<%
				if (request.getSession().getAttribute("strDia")!=null) {	%>
					N&atilde;o existem imagens da m&iacute;dia para esta data.	<%
			    } else {	%>
			    	N&atilde;o existem imagens da m&iacute;dia para estes cr&eacute;rios.  <%
			    }%>					
				</td>					
			</tr>
			<%
		}
		%>		
		</table>
	</td>
	<td width="233" valign="top">		
		<table cellspacing="0">	
		<tr> 
			<td>
<!-- Criterios -->	
<% 
	if (request.getSession().getAttribute("palavra")!=null) {	
		String data = "todas";
		String fonte = "todas";

		if ((request.getSession().getAttribute("dataInicial")!=null)&&(!"".equals(request.getSession().getAttribute("dataInicial"))))
			data = "Apartir de " + (String)request.getSession().getAttribute("dataInicial");
		if ((request.getSession().getAttribute("dataFinal")!=null)&&(!"".equals(request.getSession().getAttribute("dataFinal"))))
			data += " Até " + (String)request.getSession().getAttribute("dataFinal");
		
		String strEditorias = "";
		List listEdit = segItemDao.getEditoriasByIds((String)request.getSession().getAttribute("editorias"));
		if((listEdit != null) && (listEdit.size() > 0)) {	
			Iterator itEditorias = listEdit.iterator();
			while (itEditorias.hasNext())	{				
				ecar.pojo.SisAtributoSatb at = (ecar.pojo.SisAtributoSatb)itEditorias.next();	
				if (!"".equals(strEditorias))	strEditorias += ", ";
				strEditorias += at.getDescricaoSatb();
			}
		}	%>
	
				<table cellspacing="0" id="taxacao_procura">
					<tr>
						<td colspan="2">
							<h1>Crit&eacute;rios de sele&ccedil;&atilde;o </h1>
							<table cellspacing="0">
									<tr>
									<td class="form_label">Palavras</td>
									<td class="form_label">
									<%		
		String[] palavras = ((String)request.getSession().getAttribute("palavra")).split("\\s");		
		for (int i=0; i < palavras.length; i++) {
			String palavra = palavras[i];
			%><%=palavra%><%
			if ((i+1) < palavras.length)	{
				%><B> OU </B><%
			}
		}	%>					
									</td>
								</tr>
								<tr>
									<td class="form_label">Editorias</td><td class="form_label"><%=strEditorias%></td>
								</tr>
								<tr>
									<td class="form_label">Fontes</td>
									<td class="form_label"><%		
		String[] fontes = ((String)request.getSession().getAttribute("fonte")).split("\\s");	
		if ((fontes.length > 0)&&!("".equals(fontes[0]))) {	
			for (int i=0; i < fontes.length; i++) {
				fonte = fontes[i];
				%><%=fonte%><%
				if ((i+1) < fontes.length)	{
					%><B> OU </B><%
				}
			}	
		} else {
			%><%=fonte%><%
		}%>			
									</td>
								</tr>
								<tr>
									<td class="form_label">Datas</td><td class="form_label"><%=data%></td>
								</tr>
						
							</table>						
						</td>
					</tr>					
				</table>			
			</td>
		</tr>	
		<tr>
			<td>&nbsp;</td>
		</tr> 
		<%
		request.getSession().removeAttribute("palavra");
		request.getSession().removeAttribute("dataInicial");
		request.getSession().removeAttribute("dataFinal");
		request.getSession().removeAttribute("fonte");
		request.getSession().removeAttribute("editorias");
	}	%>
<!-- Procura -->
<%


segItem.setSegmentoSgt( (SegmentoSgt) segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))));

List editorias = segItemDao.getEditoriasBySegItem(segItem);
		
if(editorias.size() <= 0)
	out.print("Não cadastrado");

%>
		<tr>
			<td width="233" valign="top">	
				<table cellspacing="0" id="taxacao_procura">
					<tr>
						<td colspan="2">
						<h1>Procura</h1>
							<table cellspacing="0">
								<tr>
									<td class="form_label">*Palavra</td><td class="form_campo"><input name="palavra" type="text" size="20"></td>
								</tr>
								<tr>
									<td class="form_label">*Editoria</td>
									<td class="form_campo">
										<input type="hidden" name="hidTamListaEditorias" value="<%=editorias.size()%>">
		
										<combo:CheckListTag 
												nome="editoria"
												objeto="ecar.pojo.SisAtributoSatb" 
												label="descricaoSatb" 
												value="codSatb" 
												order="descricaoSatb"
												filters="indAtivoSatb=S"
												scripts="<%=_disabled%>"
												colecao="<%=editorias%>"
										/>	

									</td>
								</tr>
								<tr>
									<td class="form_label">Fonte</td>
									<td class="form_campo"><input type="text" name="fonte" size="20"></td>
								</tr>
								<tr>
									<td class="form_label">Data Inicial </td>
									<td class="form_campo"><input type="text" name="dataInicial" size="14" maxlength="10" onkeyup="mascaraData(event, this);"></td>
								</tr>
								<tr>
									<td class="form_label">Data Final </td>
									<td class="form_campo"><input type="text" name="dataFinal" size="14" maxlength="10" onkeyup="mascaraData(event, this);"></td>
								</tr>
							</table>						
						</td>
					</tr>
					<tr class="form_barra_botao">
					  <td colspan="2" align="center" class="form_grupo"><input class="form_botao" type="submit" value="Procurar"></td>
					</tr>
				</table>			
		</td>
	</tr>
	</table>
	</td>
</tr>
</table>

	<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td><%=request.getSession().getAttribute("strQtdSegmentoItem")!=null?request.getSession().getAttribute("strQtdSegmentoItem"):""%></td>
			<td align="right"><a href="#" onclick="javascript:taxacoes();">Ver todas as imagens da m&iacute;dia </a> &gt;&gt;</td>
		</tr>
	</table>
</div>
<%
	
	request.getSession().removeAttribute("strDia");

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</form>

</body>

</html>
