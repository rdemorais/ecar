<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SegmentoItemSgti" %> 
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.ImageInfo" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<script language="javascript">

function subCapaArtigos(){
	document.form.hidAcao.value = "ARTIGOS_DESTAQUE";
	document.form.action = "ctrl_artigos.jsp";	
	document.form.submit();
}
</script>

<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
<%
try{
%>
	<form name="form" method="post" action="ctrl_artigos.jsp">
		<input type="hidden" name="hidAcao" value="">	
		<input type="hidden" name="codSgti" value="">
	</form>
	
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > <a href="#" onclick="javascript:subCapaArtigos();">Artigos</a> > Detalhe </td>
			<td align="right">&lt;&lt;<a href="javascript:history.back()"> Voltar</a></td>
		</tr>
	</table>

<% 
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();
		
	String cod = Pagina.getParamStr(request, "codSgti");
	
	SegmentoItemSgti item = new SegmentoItemSgti();
	
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	
	item = segItemDao.getSegmentoItemCodSgti(Long.parseLong(cod));	
	
	
	java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
	
	String data = sf.format(item.getDataItemSgti());
	
	
	String imagem1Caminho = "";	
	String imagem2Caminho = "";
		
	int limite = 220;    			
    int larguraImagem1 = 0;
    int alturaImagem1  = 0;
    int larguraImagem2 = 0;
    int alturaImagem2  = 0;        
      
		
%>

<h1 class="interno">ARTIGO</h1>
<table cellspacing="0" cellpadding="0">
	<tr>
		<td class="conteudo_interno">
			<h1><%=item.getTituloSgti().toUpperCase()%></h1>
			<div class="imagemartigo_dois_esquerda_sem_tamanho">
				<% if (item.getImagem1Stgi() != null && !"".equals(item.getImagem1Stgi().trim())){ 
				
						String hashNomeArquivo = null;
						UsuarioUsu usuarioImagem = null; 
					
						imagem1Caminho = pathRaiz + item.getImagem1Stgi();
					   	ImageInfo imagem1 = new ImageInfo();
					   	File arquivo1 = new File(imagem1Caminho);
					   	
					   	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, item.getImagem1Stgi());
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, item.getImagem1Stgi());
					   	
					   	if (arquivo1.exists()){
					   	
    				    	FileInputStream file1 = new FileInputStream(imagem1Caminho);
    				    	imagem1.setInput(file1);
    				    
					   		if (!imagem1.check()) {
				
				%>
				<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
				<%  		} 
							else{
							
								imagem1.redimensionaImagem(limite);
    							larguraImagem1 = imagem1.getWidth();		
								alturaImagem1 = imagem1.getHeight();
    			%>
    			<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" width="<%=larguraImagem1%>" height="<%=alturaImagem1%>">
    			<%			}
						}
						else {
				%>
				<img src="<%=request.getContextPath()%>/images/ImagemIndisponivel.gif" title="Imagem Indisponível" >
				<%
						}
						if (item.getLegendaImag1Sgti() != null){
				%>
				<p class="legenda"><%=item.getLegendaImag1Sgti()%></p>
				<%		}	
				 	} 
				 
				 	if (item.getImagem2Sgti() != null && !"".equals(item.getImagem2Sgti().trim())){ 
				
						imagem2Caminho = pathRaiz + item.getImagem2Sgti();
					   	ImageInfo imagem2 = new ImageInfo();
					   	File arquivo2 = new File(imagem2Caminho);
					   	
					   	String hashNomeArquivo = null;
						UsuarioUsu usuarioImagem = null;
					   	
					   	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, item.getImagem2Sgti());
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, item.getImagem2Sgti());
					   	
					   	if (arquivo2.exists()){
					   	
    				    	FileInputStream file2 = new FileInputStream(imagem2Caminho);
    				    	imagem2.setInput(file2);
    				    
					   		if (!imagem2.check()) {
				
				%>
				<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
				<%  		} 
							
							else{
							
							imagem2.redimensionaImagem(limite);
    						larguraImagem2 = imagem2.getWidth();		
							alturaImagem2 = imagem2.getHeight();
    			%>
    			<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" width="<%=larguraImagem2%>" height="<%=alturaImagem2%>">
				<%			}
						}
						
						else {
				%>
				<img src="<%=request.getContextPath()%>/images/ImagemIndisponivel.gif" title="Imagem Indisponível" >
				<%
						}
						if (item.getLegendaImag2Sgti() != null){
				%>
				<p class="legenda"><%=item.getLegendaImag2Sgti()%></p>
				<%
						}	
				 	} 
				%>
		 	</div>
			
			<p>(<%=data%>)<br>
			<%=item.getSegmentoItemFonteSgtif().getDescricaoSgtif().toUpperCase()%></p>
			<p><%=item.getLinhaApoioSgti()%>
			</p>
			<p><%=item.getIntegraSgti()%>
			</p>
						
			<%
			if(item.getAnexoEnderecoSgti() != null && !"".equals(item.getAnexoEnderecoSgti())){
				
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;
				
				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, item.getAnexoEnderecoSgti());
				usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, item.getAnexoEnderecoSgti());
			%>			
				<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>"><%=item.getAnexoLegendaSgti()%></a><br>
			<%
			}
			
			if(item.getUrlLinkSgti() != null && !"".equals(item.getUrlLinkSgti())){
			%>
				<a href="<%=item.getUrlLinkSgti()%>" target="_blank"><%=item.getUrlLinkSgti()%></a>
			<%
			}
			%>
		</td>
	</tr>
</table><hr>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>
