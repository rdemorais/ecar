<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page language="java" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.pojo.ImageInfo" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>

<script language="javascript">
function artigos(){
	document.form.hidAcao.value = "ARTIGOS_TODOS";
	document.form.action = "ctrl_artigos.jsp";	
	document.form.submit();
}

function subCapaArtigos(){
	document.form.hidAcao.value = "ARTIGOS_DESTAQUE";
	document.form.action = "ctrl_artigos.jsp";	
	document.form.submit();
}

function detalhe(link, codSgti){
	document.form.codSgti.value=codSgti;
	document.form.hidAcao.value = "DETALHE";
	document.form.action = link;	
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
	//ecar.pojo.ConfiguracaoCfg configuracao = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	

try{
%>
	
	<form name="form" method="post" action="ctrl_artigos.jsp">
		<input type="hidden" name="hidAcao" value="">	
		<input type="hidden" name="codSgti" value="">
	</form>
	
	
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > Artigos</td>
			<td align="right">&lt;&lt;<a href="index.jsp"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">ARTIGOS EM DESTAQUE</h1>
<div id="list_moldura">
	<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td><%=request.getSession().getAttribute("strQtdSegmentoItem")%></td>
			<td align="right"><a href="#" onclick="javascript:artigos();">Ver todos os artigos</a> &gt;&gt;</td>
		</tr>
	</table>
	
	<table cellspacing="0" class="list_artigos">
	<%
		int limite = 190;
						
		String imagemCaminho = "";
		 		
    	int larguraImagem = 0;
    			
    	int alturaImagem = 0;  
		
		List lista = (List)request.getSession().getAttribute("listaSegmentoItem");
		if((lista != null) && (lista.size() > 0)) {	
			Iterator iterator = lista.iterator();
			while (iterator.hasNext())	{
				
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;  
				
				SegmentoItemSgti seg = (SegmentoItemSgti)iterator.next();		
				java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
				String data = sf.format(seg.getDataItemSgti());
				%>
				<tr>
					<td>								
						<h1><%=seg.getTituloSgti()%></h1><%				
				if (seg.getImagemCapaSgti() != null && !seg.getImagemCapaSgti().equals("")) {	
				
					imagemCaminho = pathRaiz + seg.getImagemCapaSgti();
					ImageInfo imagem = new ImageInfo();
					File arquivo = new File(imagemCaminho);
					
					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, seg.getImagemCapaSgti());
			   		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
			   		Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, seg.getImagemCapaSgti());
					
					   	if (arquivo.exists()){
					   						   		
    				    	FileInputStream file = new FileInputStream(imagemCaminho);
    				    	imagem.setInput(file);
    				    
					   		if (!imagem.check()) {
							
				%>						
						<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							title="<%=seg.getLegendaImagCapaSgti()%>" id="imagem_noticia_home_sem_tamanho" border="0"> 
				<%  		
							} 
							else{
							
								imagem.redimensionaImagem(limite);
    							larguraImagem = imagem.getWidth();		
								alturaImagem = imagem.getHeight();
    			%>
						<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							title="<%=seg.getLegendaImagCapaSgti()%>" id="imagem_noticia_home_sem_tamanho" border="0" width="<%=larguraImagem%>" height="<%=alturaImagem%>"> 
				<%			}
						}
						else {
				%>
				<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
							title="<%=seg.getLegendaImagCapaSgti()%>" id="imagem_noticia_home_sem_tamanho" border="0">
				<%
						}
					}	
				%>				
						<p>(<%=data%>)<br><%=seg.getSegmentoItemFonteSgtif().getDescricaoSgtif()%></p>
						<% 
						if( seg.getSegmentoItemLeiauteSgtil() != null ) { 
							String linkLeiaute1E = seg.getSegmentoItemLeiauteSgtil().getLinkSgtil().toString();
						%>
						
						<a href="javascript:detalhe('<%=linkLeiaute1E%>', <%=seg.getCodSgti().longValue()%>);"><%=seg.getLinhaApoioSgti()%></a>
						<%						
						}
						%>
					</td>
				</tr> 
				<%					
			}			
		}
		
	%>	
	</table>
	
	<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td><%=request.getSession().getAttribute("strQtdSegmentoItem")%></td>
			<td align="right"><a href="#" onclick="javascript:artigos();">Ver todos os artigos</a> &gt;&gt;</td>
		</tr>
	</table>
</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>
